import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

public class GameModel implements ActionListener{
    private ArrayList <Player> players;//playerの一覧
    private GamePanel view;//View
    private javax.swing.Timer timer;//毎フレームの処理をするタイマー
    private int fps = 20;//frames per second
    private int deathline = 100;//死亡基準
    private Deathtimer slowtimer;//死亡スローモーション用のタイマー
    private long countdown = 4000;//開始時のカウント時間
    private long startTime;//開始時刻
    private long nowTime;//現在時刻

    public GameModel(){
        players = new ArrayList<Player>();
        timer = new javax.swing.Timer(1000/fps, this);
        slowtimer = new Deathtimer(this);
    }
    //タイマーを通常設定で開始
    public void TimerStart(){
        setTimer(1000/fps,this);
        timer.start();
    }
    //タイマーの設定
    private void setTimer(int delay,ActionListener al){
        for(ActionListener a : timer.getActionListeners()){
            timer.removeActionListener(a);
        }
        timer.addActionListener(al);
        timer.setDelay(delay);
    }

    public void addPlayer(Chara ch, GameController gc, String na){
        players.add(new Player(ch, gc,na));
    }
    public void setView(GamePanel v){
        view = v;
    }

    public ArrayList <Player> getPlayers(){
        return players;
    }
    public Timer getTimer(){
        return timer;
    }
    public long getCount(){
        if((nowTime - startTime) < countdown){
            return (long)Math.floor((countdown - (nowTime - startTime))/1000);
        }else{
            return -1;
        }
    }
    //全プレイヤーを初期化
    public void initPlayers(){
        for(Player me : players){
            for(Player other : players){
                if(me==other){continue;}
                other.getChara().putOtherplayers(me);
            }
        }
        for(Player p: players){
            p.getChara().initChara();
        }
        System.out.println("init players");
        startTime = -1;
        nowTime = 0;
    }

    //毎フレームの更新処理
    public void actionPerformed(ActionEvent e){
        if(startTime == -1){startTime = e.getWhen();}else{nowTime = e.getWhen();}
        //キャラクター状態更新
        for (Player pl : players) {
            if(!pl.getAlive()){continue;}
            Chara chara = pl.getChara();

            chara.update();

            //input controller
            //開始時のカウントダウン時間は受け付けない
            if(chara.isWait() && chara.getAlive() && countdown<nowTime-startTime ){
                pl.getController().getState().change_character(chara);
            }
            chara.movebyMotion();

            //ステージ内にとどめる
            chara.setIsinair(true);
            boolean onwall = false;
            boolean onfloor = false;
            for(Circle circle : chara.getHits()){
                Vector2 head = chara.getHead();
                while( head.x + circle.c.x -circle.r < 0){head.x++;onwall=true;}
                while( head.x + circle.c.x +circle.r > view.width){head.x--;onwall=true;}
                while( head.y + circle.c.y -circle.r < 0){head.y++;onwall=true;onfloor=true;}
                while( head.y + circle.c.y +circle.r > view.height){head.y--;onwall=true;}
                if(head.y + circle.c.y -circle.r == 0){chara.setIsinair(false);}
            }

            //死亡判定
            if(onwall && chara.alive){
                int x = chara.getV().x;
                int y = chara.getV().y;
                int d2;
                if(onfloor){
                    d2 = y*y;
                }else{
                    d2 = x*x + y*y;
                }
                if(d2 >= deathline*deathline){
                    //death
                    chara.setAlive(false);
                    slowtimer.setStartTime(e.getWhen());
                    setTimer(100,slowtimer);
                    timer.restart();
                }
                if(chara.getMotion() == chara.getM_h()){
                    chara.getV().x=0;
                    chara.getV().y=0;
                }
            }
        }

        //当たり判定
        for (Player pl : players) {
            Chara chara = pl.getChara();
            //hit by hitzone
            for(Circle hitee : chara.getHitzone()){
                for(Player other: players){
                    if(pl == other){continue;}
                    if(chara.getMotion().getHitP(other)){continue;}
                    if(!other.getChara().getAlive()){continue;}
                    Chara you = other.getChara();
                    for(Circle hitter: you.getHits()){
                        Circle c1 = new Circle(chara.getHead().addv(hitee.c), hitee.r);
                        Circle c2 = new Circle(you.getHead().addv(hitter.c), hitter.r);
                        if(c1.hit(c2)){
                            double a = 1.0;//ダメージ、吹っ飛びの係数。相手がガードで半減
                            if(other.getChara().getMotion() == other.getChara().getM_g()){
                                a /= 2;
                            }
                            //damege
                            int damege = (int)(chara.getDamage()*a);
                            you.addDamage(damege);
                            chara.addYoDamage(damege);
                            //hit velocity
                            Vector2 v =  you.getV();
                            v.x = (damege * you.getPercent() /10);
                            v.y = 0;
                            double hitangle = chara.getMotion().getHitV();
                            v.rotate(hitangle);
                            you.getM_h().reset(hitangle);
                            you.setMotion(you.getM_h());
                            chara.getMotion().replaceHitP(other, true);
                            break;
                        }
                    }
                }
            }
        }
        view.update();//再描画
    }

    public void resetDeathPlayer(){
        for(Player pl : players){
            if(!pl.getChara().getAlive()){
                pl.setAlive(false);
            }
        }
    }

    public void gameover(){
        System.out.println("game over");
        view.gameover();
    }
    public int sizeOfAlive(){
        int i=0;
        for(Player p: players){
            if(p.getAlive())i++;
        }
        return i;
    }

}

class Deathtimer implements ActionListener {
    private GameModel model;
    static int maxTime = 1200;//継続時間
    private long startTime;//開始時刻
    
    Deathtimer(GameModel m){
        model = m;
    }
    public void setStartTime(long time){
        startTime = time;
    }
    public void actionPerformed(ActionEvent evt) {
        long nowTime= evt.getWhen();
        if(nowTime - startTime < maxTime){
            model.actionPerformed(evt);
        }else{//end of slow timer
            model.resetDeathPlayer();
            model.getTimer().stop();
            if(model.sizeOfAlive() <= 1){
                //game over
                model.gameover();
            }else{
                //game continue
                model.TimerStart();
            }
        }
    }
}

class Player{
    private Chara c;//キャラクター
    private GameController g;//コントローラー
    private String n;//名前
    private boolean alive;//プレイヤーの生存判定
    Player(Chara ch, GameController ga, String name){
        c = ch; g = ga; n = name;
        alive = true;
    }
    public Chara getChara(){
        return c;
    }
    public GameController getController(){
        return g;
    }
    public String getName(){
        return n;
    }
    public boolean getAlive(){return alive;}
    public void setAlive(boolean b){alive = b;}
}