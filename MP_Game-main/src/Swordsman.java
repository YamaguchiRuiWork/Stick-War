import java.awt.*;
import java.util.*;

class Swordsman extends Chara{
    private double defswordangle = Math.PI*11/6;
    public Swordsman(Vector2 v, Color c) {
        super(v, c);
        
    }

    @Override
    protected void initCharaMotion(){
        super.initCharaMotion();
        //original init
        m_n = new M_neutral_S(angle, otherpalyers);
        m_ang = new M_Attack_neutral_G_S(angle, otherpalyers);
        m_asg = new M_Attack_Side_G_S(angle, otherpalyers);
        m_aug = new M_Attack_Up_G_S(angle, otherpalyers);
        m_asa = new M_Attack_Side_A_S(angle, otherpalyers);
        m_ana = new M_Attack_neutral_A_S(angle, otherpalyers);
        m_aua = new M_Attack_Up_A_S(angle, otherpalyers);
        m_g = new M_Guard_S(angle, otherpalyers);
        motion = m_n;
    }
    @Override
    public double getSwordangle(){
        if(motion instanceof Motion_S){
            return motion.getSwordangle();
        }else{
            double Curswordangle = defswordangle;
            if(angle >= Math.PI *0.5 && angle<= Math.PI*1.5){
                Curswordangle = angle - Curswordangle;
            }else{
                Curswordangle += angle;
            }
            while(Curswordangle < 0){Curswordangle += Math.PI*2;}
            while(Curswordangle >= Math.PI*2){Curswordangle -= Math.PI*2;}

            return Curswordangle;
        }
    }

}

class Motion_S extends Motion{
    Motion_S(int time, double angle, Map<Player, Boolean> others){
        super(time, angle, others);
        swordangle = new double[time];
        current_swordangle = new double[time];
    }
    @Override
    public double getSwordangle(){
        return current_swordangle[time];
    }
    @Override
    public void copyDefCur(){
        super.copyDefCur();
        for(int i=0; i<swordangle.length; i++){
            current_swordangle[i] = swordangle[i];
        }
    }
    @Override
    public void rotate(double a){
        super.rotate(a);
        for(int i=0; i<current_swordangle.length; i++){
            if(a >= Math.PI *0.5 && a<= Math.PI*1.5){
                current_swordangle[i] = a - current_swordangle[i];
            }else{
                current_swordangle[i] += a;
            }
            while(current_swordangle[i] < 0){current_swordangle[i] += Math.PI*2;}
            while(current_swordangle[i] >= Math.PI*2){current_swordangle[i] -= Math.PI*2;}
        }
    }

    //Linear interpolation
    protected void lerp_swordangle(int s, int e){
        if(s>e || e<0 || s>swordangle.length){}//return;}
        double as=swordangle[s],ae=swordangle[e];
        double a;
        for(int i=s+1; i<e; i++){
            a = (double)(i-s)/(double)(e-s);
            swordangle[i] = as*(1-a) + ae*a;
        }
    }

    protected void defsetHitzone(int s, int e){
        //剣の定位置に当たり判定
        //iでフレーム指定
        Vector2[] v = new Vector2[3];
        v[0] = new Vector2(15,0);
        v[1] = new Vector2(35,0);
        v[2] = new Vector2(55,0);
        for(int i=s; i<=e; i++){
            for(int j=0; j<v.length; j++){
                hitzone[i][j].c = new Vector2(current_poses[i].getArmR1().addv(v[j].rotateV(current_swordangle[i])));
                hitzone[i][j].r = 10;
            }
        }
    }
    
}

class M_neutral_S extends Motion_S{
    public M_neutral_S(double angle , Map<Player,Boolean> others){
        super(1,angle, others);
        swordangle[0] = 1;
        //ポーズは仮
        poses[0] = new Pose(
            new Vector2(5,-40),
            new Vector2(10,-60),
            new Vector2(10,-35),
            new Vector2(20,-50),
            new Vector2(-30,-80),
            new Vector2(-40,-100),
            new Vector2(10,-80),
            new Vector2(20,-100),
            new Vector2(0,-20),
            new Vector2(-20,-60)
        );
        for (int i=0; i<cancel.length; i++) {
            cancel[i] = true;
        }
        reset(angle);
    }
    @Override
    public boolean update(){
        return true;
    }

}
class M_Attack_neutral_G_S extends Motion_S{
    public M_Attack_neutral_G_S(double angle , Map<Player,Boolean> others){
        super(10,angle, others);
        poses[0] = new Pose(
            new Vector2(30,10),
            new Vector2(20,40),
            new Vector2(-30,-30),
            new Vector2(-30,0),
            new Vector2(30,-70),
            new Vector2(40,-100),
            new Vector2(-20,-80),
            new Vector2(-40,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        poses[5] = new Pose(
            new Vector2(20,-25),
            new Vector2(40,-30),
            new Vector2(-30,-30),
            new Vector2(-50,-10),
            new Vector2(30,-70),
            new Vector2(30,-100),
            new Vector2(-20,-80),
            new Vector2(-40,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        poses[9] = new Pose(
            new Vector2(5,-40),
            new Vector2(10,-70),
            new Vector2(10,-10),
            new Vector2(-30,-20),
            new Vector2(40,-70),
            new Vector2(40,-80),
            new Vector2(-15,-70),
            new Vector2(-30,-80),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        //swordangle[]を設定
        swordangle[0] = Math.PI*7/6;
        swordangle[5] = Math.PI/3;
        swordangle[9] = 0;
        lerp_swordangle(0, 5);
        lerp_swordangle(5,9 );
        lerp_pose(0, 5);
        lerp_pose(5, 9);
        reset(angle);
        damage = 8;
        hitV = 0;

    }
    @Override
    protected void setHitzone(){
        defsetHitzone(5, 9);
    }
}

class M_Attack_Side_G_S extends Motion_S{
    public M_Attack_Side_G_S(double angle , Map<Player,Boolean> others){
        super(15,angle, others);
        //posesは格闘家のモーションと同じ
        poses[0] = new Pose(
            new Vector2(0,-40),
            new Vector2(20,-30),
            new Vector2(10,-30),
            new Vector2(-30,-20),
            new Vector2(-10,-70),
            new Vector2(-30,-80),
            new Vector2(-10,-60),
            new Vector2(10,-80),
            new Vector2(0,-20),
            new Vector2(-30,-50)
        );
        poses[8] = new Pose(
            new Vector2(0,-40),
            new Vector2(20,-30),
            new Vector2(10,-30),
            new Vector2(-30,-20),
            new Vector2(-10,-70),
            new Vector2(-30,-80),
            new Vector2(-10,-60),
            new Vector2(10,-80),
            new Vector2(0,-20),
            new Vector2(-30,-50)
        );

        poses[9] = new Pose(
            new Vector2(-10,-10),
            new Vector2(10,0),
            new Vector2(-10,-30),
            new Vector2(-30,-50),
            new Vector2(-50,-70),
            new Vector2(-70,-90),
            new Vector2(0,-60),
            new Vector2(-30,-90),
            new Vector2(0,-20),
            new Vector2(-30,-50)
        );

        poses[14] = new Pose(
            new Vector2(-20,-15),
            new Vector2(-40,-10),
            new Vector2(-10,-35),
            new Vector2(-20,-50),
            new Vector2(-40,-80),
            new Vector2(-70,-90),
            new Vector2(10,-60),
            new Vector2(0,-90),
            new Vector2(0,-20),
            new Vector2(-30,-60)
        );

        //swordangle[]を設定
        swordangle[0] = Math.PI*5/6;
        swordangle[8] = Math.PI*5/6;
        swordangle[9] = 0;
        swordangle[14] = Math.PI*5/6;
        lerp_swordangle(0, 8);
        lerp_swordangle(9,14 );
        lerp_pose(0, 8);
        lerp_pose(9, 14);
        reset(angle);
        damage = 20;
        hitV =0;
    }
    @Override
    protected void setHitzone(){
        defsetHitzone(8, 14);
    }
}

class M_Attack_Up_G_S extends Motion_S{
    public M_Attack_Up_G_S(double angle , Map<Player,Boolean> others){
        super(14,angle, others);
        //posesは格闘家のモーションと同じ
        poses[0] = new Pose(
            new Vector2(-20,-40),
            new Vector2(-10,-70),
            new Vector2(10,10),
            new Vector2(30,30),
            new Vector2(0,-60),
            new Vector2(-30,-80),
            new Vector2(30,-50),
            new Vector2(20,-80),
            new Vector2(0,-20),
            new Vector2(0,-50)
        );
        poses[5] = new Pose(
            new Vector2(-10,10),
            new Vector2(0,30),
            new Vector2(10,-40),
            new Vector2(30,-30),
            new Vector2(0,-80),
            new Vector2(-10,-100),
            new Vector2(20,-70),
            new Vector2(10,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        poses[9] = new Pose(
            new Vector2(-10,0),
            new Vector2(-10,30),
            new Vector2(-10,0),
            new Vector2(-10,30),
            new Vector2(-5,-80),
            new Vector2(-10,-100),
            new Vector2(20,-70),
            new Vector2(10,-90),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );
        poses[13] = new Pose(
            new Vector2(-10,0),
            new Vector2(-10,30),
            new Vector2(-10,0),
            new Vector2(-10,30),
            new Vector2(-5,-80),
            new Vector2(-10,-100),
            new Vector2(20,-70),
            new Vector2(10,-90),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );
        //swordangle[]を設定
        swordangle[0] = 0;
        swordangle[5] = 0;
        swordangle[9] = 0;
        swordangle[13] = 0;
        lerp_swordangle(0, 5);
        lerp_swordangle(5,9 );
        lerp_swordangle(9,13 );
        lerp_pose(0, 5);
        lerp_pose(5, 9);
        lerp_pose(9, 13);
        reset(angle);
        damage = 13;
        hitV=Math.PI/2;
    }
    @Override
    protected void setHitzone(){
        defsetHitzone(3, 9);
    }
}

class M_Attack_Side_A_S extends Motion_S{
    public M_Attack_Side_A_S(double angle , Map<Player,Boolean> others){
        super(23,angle, others);
        //posesは格闘家のモーションと同じ
        poses[0] = new Pose(
            new Vector2(-20,-40),
            new Vector2(-40,-20),
            new Vector2(0,-40),
            new Vector2(20,-40),
            new Vector2(-20,-80),
            new Vector2(-40,-90),
            new Vector2(0,-70),
            new Vector2(-10,-100),
            new Vector2(0,-20),
            new Vector2(-20,-60)
        );
        poses[6] = new Pose(
            new Vector2(-10,20),
            new Vector2(-40,10),
            new Vector2(-20,5),
            new Vector2(-40,10),
            new Vector2(10,-80),
            new Vector2(-30,-90),
            new Vector2(10,-70),
            new Vector2(-20,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        poses[12] = new Pose(
            new Vector2(20,-15),
            new Vector2(50,-10),
            new Vector2(20,-30),
            new Vector2(50,-10),
            new Vector2(-10,-80),
            new Vector2(-50,-70),
            new Vector2(0,-70),
            new Vector2(-10,-100),
            new Vector2(0,-20),
            new Vector2(-20,-60)
        );

        poses[19] = new Pose(
            new Vector2(15,-35),
            new Vector2(30,-50),
            new Vector2(10,-35),
            new Vector2(20,-50),
            new Vector2(-10,-70),
            new Vector2(-40,-70),
            new Vector2(0,-70),
            new Vector2(-20,-100),
            new Vector2(0,-20),
            new Vector2(-10,-60)
        );
        poses[22] = new Pose(
            new Vector2(15,-35),
            new Vector2(30,-50),
            new Vector2(10,-35),
            new Vector2(20,-50),
            new Vector2(-10,-70),
            new Vector2(-40,-70),
            new Vector2(0,-70),
            new Vector2(-20,-100),
            new Vector2(0,-20),
            new Vector2(-10,-60)
        );

        //swordangle[]を設定
        swordangle[0] = Math.PI*5/4;
        swordangle[6] = Math.PI*5/4;
        swordangle[12] = Math.PI*1/2;
        swordangle[19] = 0;
        swordangle[22] = 0;
        lerp_swordangle(0, 6);
        lerp_swordangle(6, 12);
        lerp_swordangle(12,19 );
        lerp_swordangle(19,22 );
        lerp_pose(0,6);
        lerp_pose(6, 12);
        lerp_pose(12, 19);
        lerp_pose(19, 22);
        reset(angle);
        damage = 20;
        hitV=0;
    }
    @Override
    protected void setHitzone(){
        defsetHitzone(6, 19);
    }
}

class M_Attack_neutral_A_S extends Motion_S{
    public M_Attack_neutral_A_S(double angle , Map<Player,Boolean> others){
        super(25,angle, others);
        //posesは格闘家のモーションと同じ
        poses[0] = new Pose(
            new Vector2(-10,0),
            new Vector2(10,30),
            new Vector2(20,0),
            new Vector2(10,30),
            new Vector2(-10,-60),
            new Vector2(-10,-80),
            new Vector2(20,-90),
            new Vector2(10,-100),
            new Vector2(0,-20),
            new Vector2(0,-50)
        );

        poses[5] = new Pose(
            new Vector2(-10,0),
            new Vector2(10,30),
            new Vector2(20,0),
            new Vector2(10,30),
            new Vector2(-10,-60),
            new Vector2(-10,-80),
            new Vector2(20,-90),
            new Vector2(10,-100),
            new Vector2(0,-20),
            new Vector2(0,-50)
        );

        poses[10] = new Pose(
            new Vector2(-20,-30),
            new Vector2(0,-50),
            new Vector2(30,-30),
            new Vector2(0,-50),
            new Vector2(-40,-30),
            new Vector2(-60,-60),
            new Vector2(30,-40),
            new Vector2(30,-60),
            new Vector2(0,0),
            new Vector2(-10,-10)
        );

        poses[20] = new Pose(
            new Vector2(-15,-15),
            new Vector2(-10,-40),
            new Vector2(15,-15),
            new Vector2(-10,-40),
            new Vector2(-40,-20),
            new Vector2(-60,-40),
            new Vector2(40,-20),
            new Vector2(20,-40),
            new Vector2(0,0),
            new Vector2(0,20)
        );
        poses[24] = new Pose(
            new Vector2(-15,-15),
            new Vector2(-10,-40),
            new Vector2(15,-15),
            new Vector2(-10,-40),
            new Vector2(-40,-20),
            new Vector2(-60,-40),
            new Vector2(40,-20),
            new Vector2(20,-40),
            new Vector2(0,0),
            new Vector2(0,20)
        );

        //swordangle[]を設定
        swordangle[0] = Math.PI*2/3;
        swordangle[5] = Math.PI*2/3;
        swordangle[10] = Math.PI/4;
        swordangle[14] = 0;
        swordangle[17] = Math.PI*5/3;
        swordangle[20] = Math.PI*3/2;
        swordangle[24] = Math.PI*3/2;
        lerp_swordangle(0, 5);
        lerp_swordangle(5, 10);
        lerp_swordangle(10, 14);
        lerp_swordangle(14, 17);
        lerp_swordangle(17, 20);
        lerp_swordangle(20, 24);
        lerp_pose(0, 5);
        lerp_pose(5, 10);
        lerp_pose(10, 20);
        lerp_pose(20, 24);
        reset(angle);
        damage = 22;
        hitV=Math.PI*3/2;
    }
    @Override
    protected void setHitzone(){
        defsetHitzone(5, 20);
    }
}

class M_Attack_Up_A_S extends Motion_S{
    public M_Attack_Up_A_S(double angle , Map<Player,Boolean> others){
        super(15,angle, others);
        //posesは格闘家のモーションと同じ
        poses[0] = new Pose(
            new Vector2(-5,-30),
            new Vector2(-10,-40),
            new Vector2(30,0),
            new Vector2(50,-10),
            new Vector2(30,-70),
            new Vector2(10,-90),
            new Vector2(40,-50),
            new Vector2(60,-80),
            new Vector2(0,-20),
            new Vector2(20,-60)
        );

        poses[5] = new Pose(
            new Vector2(10,-30),
            new Vector2(40,-30),
            new Vector2(-20,-30),
            new Vector2(-40,-30),
            new Vector2(20,-80),
            new Vector2(-20,-70),
            new Vector2(50,-60),
            new Vector2(70,-80),
            new Vector2(0,-20),
            new Vector2(20,-60)
        );

        poses[10] = new Pose(
            new Vector2(-30,30),
            new Vector2(-50,30),
            new Vector2(30,-30),
            new Vector2(40,-50),
            new Vector2(-20,-80),
            new Vector2(40,-80),
            new Vector2(10,-80),
            new Vector2(20,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        poses[14] = new Pose(
            new Vector2(-30,-20),
            new Vector2(-40,-40),
            new Vector2(30,-30),
            new Vector2(40,-50),
            new Vector2(35,-70),
            new Vector2(60,-80),
            new Vector2(0,-70),
            new Vector2(30,-80),
            new Vector2(0,-20),
            new Vector2(10,-60)
        );

        //swordangle[]を設定
        swordangle[0] = Math.PI*7/6;
        swordangle[5] = Math.PI*3/2;
        swordangle[7] = 6.282;
        swordangle[8] = 0;
        swordangle[10] = Math.PI/2;
        swordangle[14] = Math.PI*3/2;
        lerp_swordangle(0, 5);
        lerp_swordangle(5, 7);
        lerp_swordangle(7, 8);
        lerp_swordangle(8, 10);
        lerp_swordangle(10, 14);
        lerp_pose(0, 5);
        lerp_pose(5, 10);
        lerp_pose(10, 14);
        reset(angle);
        damage = 15;
        hitV=Math.PI/2;
    }
    @Override
    protected void setHitzone(){
        defsetHitzone(5,14 );
    }
}

class M_Guard_S extends Motion_S{
    public M_Guard_S(double angle , Map<Player,Boolean> others){
        super(1,angle, others);
        swordangle[0] = Math.PI*4/3;
        //ポーズは仮
        poses[0] = new Pose(
            new Vector2(-20,-40),
            new Vector2(40,-40),
            new Vector2(20,-30),
            new Vector2(40,-40),
            new Vector2(-10,-70),
            new Vector2(-20,-100),
            new Vector2(30,-80),
            new Vector2(30,-100),
            new Vector2(0,-20),
            new Vector2(-10,-60)
        );
        
        reset(angle);
    }

}