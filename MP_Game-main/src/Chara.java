import java.awt.*;
import java.util.*;

public class Chara {
    protected Vector2 head;//頭の円の中心座標
    protected Motion motion;//現在のモーション
    protected Color color;//キャラの色
    protected double angle;//向いている方向
    protected Vector2 velocity;//移動速度
    private static int gravity = 5;//重力の大きさ
    private static int speed = 10;//左右移動の速さ
    protected boolean ismoving;//移動中かどうか

    protected boolean wait;// 入力待機状態にあるか
    protected boolean isinair;//空中判定
    protected Circle[] hits;//被当たり判定
    protected int percent;//被ダメ
    protected int damage;//与ダメ
    protected Map<Player,Boolean> otherpalyers = new HashMap<>();//他プレイヤーのマップ。モーション生成用
    protected boolean alive;//生死判定
    
    //motion def
    //生成したモーションを格納する変数
    protected Motion m_n;//M_neutral
    protected Motion m_n_a;//M_neutral_A in air
    protected Motion m_r;//M_run
    protected Motion m_ad;//M_Attack_Down
    protected Motion m_j;//M_jump
    protected Motion m_h;//M_jump
    protected Motion m_ang;//M_Attack_Neutral_G
    protected Motion m_ana;//M_Attack_Neutral_A
    protected Motion m_asa;//M_Attack_Side_A
    protected Motion m_asg;//M_Attack_Side_G
    protected Motion m_aua;//M_Attack_Up_A
    protected Motion m_aug;//M_Attack_Up_G
    protected Motion m_g;//M_Guard
    
    public Chara(Vector2 v, Color c){
        head = new Vector2();
        head.x = v.x;
        head.y = v.y;
        color = c;
        angle = 0;
        percent = 0;
        damage = 0;
        speed = 13;
        velocity = new Vector2(0,0);
        isinair = true;
        ismoving = false;
        alive = true;
    }

    //motion getter
    public Motion getM_n(){return m_n;}
    public Motion getM_n_a(){return m_n_a;}
    public Motion getM_r(){return m_r;} //M_run
    public Motion getM_ad(){return m_ad;}
    public Motion getM_j(){return m_j;}  //M_jump
    public Motion getM_h(){return m_h;}  //M_hit
    public Motion getM_ang(){return m_ang;}
    public Motion getM_ana(){return m_ana;}
    public Motion getM_asa(){return m_asa;}
    public Motion getM_asg(){return m_asg;}
    public Motion getM_aua(){return m_aua;}
    public Motion getM_aug(){return m_aug;}
    public Motion getM_g(){return m_g;}

    //getter
    public boolean getAlive(){return alive;}
    public boolean getIsinair(){return isinair;}
    public Vector2 getHead(){return head;}
    public Motion getMotion(){return motion;}
    public Color getColor(){return color;}
    public int getSpeed(){return speed;}
    public double getAngle(){return angle;}
    public int getPercent(){return percent;}
    public int getDamage(){return motion.getDamage();}
    public Circle[] getHitzone(){return motion.getHitzone();}
    public boolean isWait(){return  motion.getCancel();}
    public Pose getPose(){return motion.getPose();}
    public int getYoDamage(){return damage;}
    public Vector2 getV(){return velocity;}

    //被当たり判定の円を返す
    public Circle[] getHits(){
        hits[0].c = motion.getPose().getNeck();
        hits[1].c = motion.getPose().getHip();
        return hits;
    }

    //他プレイヤーのマップ。モーション生成用
    public Map<Player,Boolean> getOtherplayers(){
        return otherpalyers;
    }

    //setter
    public void setAlive(boolean b){alive = b;}
    public void setIsmoving(boolean b){ismoving = b;}
    public void setIsinair(boolean b){isinair = b;}
    public void setMotion(Motion m){motion = m;}
    public void setAngle(double a){
        angle = a;
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
    }
    public void addDamage(int d){percent += d;}
    public void addYoDamage(int d){damage += d;}
    public void putOtherplayers(Player p){
        otherpalyers.put(p,false);
    }

    //初期化
    public void initChara(){
        initCharaMotion();
        //被当たり判定初期化
        hits = new Circle[2];
        hits[0] = new Circle(motion.getPose().getNeck(),40);
        hits[1] = new Circle(motion.getPose().getHip(),40);
    }
    //モーション生成
    protected void initCharaMotion(){
        m_n = new M_neutral(angle,otherpalyers);
        m_n_a = new M_neutral_A(angle, otherpalyers);
        m_r = new M_run(angle, otherpalyers);
        m_ad = new M_Attack_Down(angle, otherpalyers);
        m_j = new M_jump(angle, otherpalyers);
        m_h = new M_hit(angle, otherpalyers);
        m_ang = new M_Attack_Neutral_G(angle, otherpalyers);
        m_ana = new M_Attack_Neutral_A(angle, otherpalyers);
        m_asa = new M_Attack_Side_A(angle, otherpalyers);
        m_asg = new M_Attack_Side_G(angle, otherpalyers);
        m_aua = new M_Attack_Up_A(angle, otherpalyers);
        m_aug = new M_Attack_Up_G(angle, otherpalyers);
        m_g = new M_Guard(angle, otherpalyers);    
        motion = m_n;
    }

    //毎フレームの更新処理
    //update in timer
    public void update(){
        //モーションのコマ送りと反映
        if(!motion.update()){
            while(angle < 0){angle += Math.PI*2;}
            while(angle >= Math.PI*2){angle -= Math.PI*2;}
            if(angle > Math.PI*0.5 && angle <Math.PI*1.5){
                angle = Math.PI;
            }else{
                angle = 0;
            }
            if(isinair){
                m_n_a.reset(angle);
                setMotion(m_n_a);
            }else{
                m_n.reset(angle);
                setMotion(m_n);
            }
        }

        //重力
        if(isinair){velocity.y -= gravity;}
        //慣性
        if(motion == m_h){velocity.x /= 1.1;}
        else if(!ismoving){velocity.x /= 2;}
        head.add(velocity);
    }
    public void movebyMotion(){
        head.add(motion.getMoveVector());
    }


    // for swordsman
    //剣の角度を返す。剣がない場合は-1が返る。
    public double getSwordangle(){
        return -1;
    }

}
