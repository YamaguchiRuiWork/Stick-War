import java.util.*;

public class Motion {
    protected int time;//現在のフレーム数（配列のインデックス）
    protected Pose[] poses;//ポーズ（標準）
    protected Vector2[] head; //t-1からtの間の頭の移動量head[t]（標準）
    protected Pose[] current_poses;//ポーズ（現在）
    protected Vector2[] current_head;//頭の移動量（現在）
    protected double hitV;//吹っ飛ばし角度（標準）
    protected double current_hitV;//吹っ飛ばし角度（現在）
    protected double swordangle[];//剣の角度（標準）
    protected double current_swordangle[];//剣の角度（現在）

    protected boolean[] cancel;//キャンセルして別モーションを受け付けるか
    protected double angle;//現在向いている方向
    protected Circle[][] hitzone;//当たり判定
    static int Maxhitzone = 5;//当たり判定の円の個数
    protected int damage;//攻撃ダメージ量
    protected Map<Player,Boolean> hitplayer;//他のプレイヤーに当てたかどうか

    private Motion(int time, double angle){
        //System.out.println("motion gen");
        poses = new Pose[time];
        head = new Vector2[time];
        current_poses = new Pose[time];
        current_head = new Vector2[time];
        hitV = 0;

        //init
        cancel = new boolean[time];
        for (int i=0; i<time; i++) {
            poses[i] = new Pose();
            head[i] = new Vector2();
            cancel[i] = false;
        }

        //init
        hitzone = new Circle[time][Maxhitzone];
        for(int i=0; i<hitzone.length; i++){
            for(int j=0; j<hitzone[i].length; j++){
                hitzone[i][j] = new Circle();
            }
        }

        this.time = 0;
        this.angle = angle;
        this.damage = 0;
    }
    public Motion(int time, double angle, Map<Player,Boolean> others){
        this(time,angle);
        //init
        hitplayer  = new HashMap<>(others);
    }

    //標準の値を現在の値にコピー
    protected void copyDefCur(){
        //deep copy def to current
        for(int i=0; i<head.length; i++){
            current_head[i] = new Vector2(head[i]);
            current_poses[i] = new Pose(poses[i]);
        }
        current_hitV = hitV;
        while(current_hitV < 0){current_hitV += Math.PI*2;}
        while(current_hitV >= Math.PI*2){current_hitV -= Math.PI*2;}
    }

    //getter
    public double getHitV(){return current_hitV;}
    public Pose getPose(int t){return current_poses[t];}
    public Pose getPose(){return current_poses[time];}
    public Vector2 getMoveVector(){return current_head[time];}
    public int getTime(){return time;}
    public boolean getCancel(){return cancel[time];}
    public Circle[] getHitzone(){return hitzone[time];}
    public int getDamage(){return damage;}
    public boolean getHitP(Player p){return hitplayer.get(p);}
    public double getAngle(){return angle;}
    public double getSwordangle(){return -1;}//Motion_Sでoverride

    //setter
    public void replaceHitP(Player p, boolean b){
        hitplayer.replace(p,b);
    }
    public void setTime(int t){
        time = t;
    }
    protected void setHitzone(){
        //defult nothing
    }

    //回転・反転
    protected void rotate(double a){
        copyDefCur();
        while(a < 0){a += Math.PI*2;}
        while(a >= Math.PI*2){a -= Math.PI*2;}
        for(Pose po : current_poses) {
            po.rotate(a);
        }
        for (Vector2 v : current_head) {
            v.rotate(a);
        }
        if(a >= Math.PI *0.5 && a<= Math.PI*1.5){
            current_hitV = a - current_hitV;
        }else{
            current_hitV += a;
        }
        while(current_hitV < 0){current_hitV+= Math.PI*2;}
        while(current_hitV >= Math.PI*2){current_hitV -= Math.PI*2;}
    }

    //コマ送り
    public boolean update(){
        time++;
        if(time>=poses.length){return false;}//motionの時間が終わった
        return true;
    }

    //角度aで初期化
    public void reset(double a){
        rotate(a);
        time = 0;
        for (Player p: hitplayer.keySet()) {
            hitplayer.replace(p, false);
        }
        setHitzone();
    }

    //Linear interpolation
    //posesを線形補間する
    protected void lerp_pose(int s, int e){
        if(s>e || e<0 || s>poses.length){}
        Pose ps=poses[s],pe=poses[e];
        double a;
        for(int i=s+1; i<e; i++){
            a = (double)(i-s)/(double)(e-s);
            poses[i] = new Pose(
                new Vector2(ps.getArmR0().cal_idp(pe.getArmR0(), a)),
                new Vector2(ps.getArmR1().cal_idp(pe.getArmR1(), a)),
                new Vector2(ps.getArmL0().cal_idp(pe.getArmL0(), a)),
                new Vector2(ps.getArmL1().cal_idp(pe.getArmL1(), a)),
                new Vector2(ps.getLegR0().cal_idp(pe.getLegR0(), a)),
                new Vector2(ps.getLegR1().cal_idp(pe.getLegR1(), a)),
                new Vector2(ps.getLegL0().cal_idp(pe.getLegL0(), a)),
                new Vector2(ps.getLegL1().cal_idp(pe.getLegL1(), a)),
                new Vector2(ps.getNeck().cal_idp(pe.getNeck(), a)),
                new Vector2(ps.getHip().cal_idp(pe.getHip(), a))
            );
        }
    }
    
}
