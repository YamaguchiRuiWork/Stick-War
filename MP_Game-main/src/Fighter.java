import java.awt.*;
import java.util.*;

class Fighter extends Chara{
    public Fighter(Vector2 v, Color c) {
        super(v, c);
    }

    @Override
    protected void initCharaMotion(){
        super.initCharaMotion();
        //original init
        m_n = new M_neutral_F(angle, otherpalyers);
        m_ang =new M_Attack_Neutral_G_F(angle, otherpalyers);
        m_aug = new M_Attack_Up_G_F(angle, otherpalyers);
        m_asg = new M_Attack_Side_G_F(angle, otherpalyers);
        m_ana = new M_Attack_Neutral_A_F(angle, otherpalyers);
        m_asa = new M_Attack_Side_A_F(angle, otherpalyers);
        m_aua = new M_Attack_Up_A_F(angle, otherpalyers);
        m_g = new M_Guard_F(angle, otherpalyers);
        m_n.reset(angle);
        motion = m_n;
    }
}

class M_neutral_F extends M_neutral{
    public M_neutral_F(double angle, Map<Player, Boolean> others) {
        super(angle, others);
        poses[0] = new Pose(
            new Vector2(-20,-40),
            new Vector2(-10,-50),
            new Vector2(20,-40),
            new Vector2(30,-20),
            new Vector2(-10,-80),
            new Vector2(-20,-100),
            new Vector2(20,-70),
            new Vector2(20,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );
    }
}

class M_Attack_Neutral_G_F extends M_Attack_Neutral_G{
    M_Attack_Neutral_G_F (double angle,Map<Player,Boolean> others){
        super(8, angle, others);
        poses[0] = new Pose(
            new Vector2(-20,-30),
            new Vector2(-10,-50),
            new Vector2(-10,-30),
            new Vector2(10,-40),
            new Vector2(-10,-70),
            new Vector2(-30,-90),
            new Vector2(20,-60),
            new Vector2(20,-90),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );
        poses[7] = new Pose(
            new Vector2(20,-25),
            new Vector2(40,-30),
            new Vector2(-30,-35),
            new Vector2(-20,-50),
            new Vector2(10,-75),
            new Vector2(10,-90),
            new Vector2(-20,-80),
            new Vector2(-40,-90),
            new Vector2(0,-20),
            new Vector2(-10,-50)
        );
        lerp_pose(0, 7);
        reset(angle);
        damage = 8;
        hitV = Math.PI/8;
    }
    protected void setHitzone(){
        for (int i=1; i<=7; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getArmR1());
            hitzone[i][0].r = 20;
        }        
    }

}

class M_Attack_Up_G_F extends M_Attack_Up_G{
    M_Attack_Up_G_F (double angle,Map<Player,Boolean> others){
        super(13, angle, others);
        poses[0] = new Pose(
            new Vector2(-30,-30),
            new Vector2(-30,-40),
            new Vector2(10,-10),
            new Vector2(30,0),
            new Vector2(-10,-60),
            new Vector2(-40,-60),
            new Vector2(20,-40),
            new Vector2(20,-60),
            new Vector2(0,-20),
            new Vector2(-10,-40)
        );
        
        poses[5] = new Pose(
            new Vector2(20,-30),
            new Vector2(30,-10),
            new Vector2(-20,-30),
            new Vector2(-30,-30),
            new Vector2(-25,-50),
            new Vector2(-40,-60),
            new Vector2(20,-50),
            new Vector2(20,-60),
            new Vector2(0,-20),
            new Vector2(-10,-40)
        );

        poses[6] = new Pose(
            new Vector2(10,0),
            new Vector2(10,30),
            new Vector2(0,-30),
            new Vector2(0,-50),
            new Vector2(-10,-40),
            new Vector2(-10,-70),
            new Vector2(5,-65),
            new Vector2(0,-80),
            new Vector2(0,-20),
            new Vector2(10,-50)
        );
        poses[8] = new Pose(
            new Vector2(10,0),
            new Vector2(10,30),
            new Vector2(0,-30),
            new Vector2(0,-50),
            new Vector2(-10,-40),
            new Vector2(-10,-70),
            new Vector2(5,-65),
            new Vector2(0,-80),
            new Vector2(0,-20),
            new Vector2(10,-50)
        );

        poses[12] = new Pose(
            new Vector2(10,0),
            new Vector2(10,30),
            new Vector2(0,-30),
            new Vector2(0,-50),
            new Vector2(-10,-40),
            new Vector2(-10,-70),
            new Vector2(5,-65),
            new Vector2(0,-80),
            new Vector2(0,-20),
            new Vector2(10,-50)
        );

        lerp_pose(0, 5);
        lerp_pose(6, 8);
        lerp_pose(8, 12);
        reset(angle);
        damage = 15;
        hitV = Math.PI/2;
    }
    protected void setHitzone(){
        for (int i=6; i<=8; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getArmR1());
            hitzone[i][0].r = 30;
        }        
    }

}

class M_Attack_Side_G_F extends M_Attack_Side_G{
    M_Attack_Side_G_F (double angle,Map<Player,Boolean> others){
        super(15, angle, others);
        poses[0] = new Pose(
            new Vector2(-20,-30),
            new Vector2(-20,-50),
            new Vector2(15,-30),
            new Vector2(30,-40),
            new Vector2(-10,-70),
            new Vector2(-30,-90),
            new Vector2(30,-70),
            new Vector2(30,-90),
            new Vector2(0,-20),
            new Vector2(-10,-60)
        );
        
        poses[8] = new Pose(
            new Vector2(-30,-10),
            new Vector2(-40,-30),
            new Vector2(20,-30),
            new Vector2(40,-10),
            new Vector2(20,-40),
            new Vector2(50,-50),
            new Vector2(-15,-80),
            new Vector2(-30,-90),
            new Vector2(0,-20),
            new Vector2(0,-50)
        );

        poses[14] = new Pose(
            new Vector2(-20,-20),
            new Vector2(-40,-30),
            new Vector2(30,-20),
            new Vector2(10,-30),
            new Vector2(20,-50),
            new Vector2(60,-40),
            new Vector2(-10,-80),
            new Vector2(-30,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );
        
        lerp_pose(0, 8);
        lerp_pose(8, 14);
        reset(angle);
        damage = 15;
        hitV = 0;
    }
    protected void setHitzone(){
        for (int i=7; i<=14; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getLegR1());
            hitzone[i][0].r = 20;
        }        
    }

}

class M_Attack_Neutral_A_F extends M_Attack_Neutral_A{
    M_Attack_Neutral_A_F (double angle,Map<Player,Boolean> others){
        super(20, angle, others);
        poses[0] = new Pose(
            new Vector2(-40,0),
            new Vector2(-40,-20),
            new Vector2(40,0),
            new Vector2(40,-20),
            new Vector2(-20,-30),
            new Vector2(10,-50),
            new Vector2(20,-30),
            new Vector2(10,-50),
            new Vector2(0,0),
            new Vector2(0,-40)
        );
        
        poses[10] = new Pose(
            new Vector2(-30,20),
            new Vector2(-40,0),
            new Vector2(30,20),
            new Vector2(40,0),
            new Vector2(-20,-20),
            new Vector2(-10,-40),
            new Vector2(20,-20),
            new Vector2(10,-40),
            new Vector2(0,-20),
            new Vector2(0,0)
        );
        poses[11] = new Pose(
            new Vector2(-20,-40),
            new Vector2(0,-50),
            new Vector2(20,-40),
            new Vector2(30,-30),
            new Vector2(5,-70),
            new Vector2(0,-90),
            new Vector2(10,-70),
            new Vector2(10,-90),
            new Vector2(0,-20),
            new Vector2(10,-50)
        );
        poses[19] = new Pose(
            new Vector2(-20,-40),
            new Vector2(0,-50),
            new Vector2(20,-40),
            new Vector2(30,-30),
            new Vector2(5,-70),
            new Vector2(0,-90),
            new Vector2(10,-70),
            new Vector2(10,-90),
            new Vector2(0,-20),
            new Vector2(10,-50)
        );
        
        lerp_pose(0, 10);
        lerp_pose(11, 19);
        reset(angle);
        damage = 15;
        hitV = 3*Math.PI/2;
    }
    protected void setHitzone(){
        for (int i=10; i<=15; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getLegR1());
            hitzone[i][0].r = 25;
        }        
    }

}


class M_Attack_Side_A_F extends M_Attack_Side_A{
    M_Attack_Side_A_F (double angle,Map<Player,Boolean> others){
        super(20, angle, others);
        poses[0] = new Pose(
            new Vector2(-30,-30),
            new Vector2(-40,-10),
            new Vector2(20,-40),
            new Vector2(30,-30),
            new Vector2(-20,-70),
            new Vector2(0,-90),
            new Vector2(10,-50),
            new Vector2(10,-80),
            new Vector2(0,-20),
            new Vector2(-10,-50)
        );
        
        poses[6] = new Pose(
            new Vector2(-20,10),
            new Vector2(10,30),
            new Vector2(-20,-30),
            new Vector2(-10,-40),
            new Vector2(-25,-65),
            new Vector2(-40,-80),
            new Vector2(10,-60),
            new Vector2(0,-80),
            new Vector2(0,-20),
            new Vector2(-10,-50)
        );
        poses[10] = new Pose(
            new Vector2(30,-10),
            new Vector2(50,-20),
            new Vector2(-20,-20),
            new Vector2(-30,-40),
            new Vector2(10,-60),
            new Vector2(-10,-70),
            new Vector2(-25,-65),
            new Vector2(-30,-80),
            new Vector2(0,-20),
            new Vector2(-20,-50)
        );
        poses[14] = new Pose(
            new Vector2(5,-35),
            new Vector2(10,-50),
            new Vector2(-20,0),
            new Vector2(-30,20),
            new Vector2(-20,-70),
            new Vector2(-40,-40),
            new Vector2(-35,-50),
            new Vector2(-50,-50),
            new Vector2(0,-20),
            new Vector2(-20,-50)
        );
        poses[19] = new Pose(
            new Vector2(5,-35),
            new Vector2(10,-50),
            new Vector2(-20,0),
            new Vector2(-30,20),
            new Vector2(-20,-70),
            new Vector2(-40,-40),
            new Vector2(-35,-50),
            new Vector2(-50,-50),
            new Vector2(0,-20),
            new Vector2(-20,-50)
        );
        
        lerp_pose(0, 6);
        lerp_pose(6, 10);
        lerp_pose(10, 14);
        lerp_pose(14, 19);
        reset(angle);
        damage = 20;
        hitV = 0;
    }
    protected void setHitzone(){
        for (int i=6; i<=14; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getArmR1());
            hitzone[i][0].r =35;
        }        
    }

}

class M_Attack_Up_A_F extends M_Attack_Up_A{
    M_Attack_Up_A_F (double angle,Map<Player,Boolean> others){
        super(10, angle, others);
        poses[0] = new Pose(
            new Vector2(40,-10),
            new Vector2(30,10),
            new Vector2(-10,-40),
            new Vector2(10,-40),
            new Vector2(20,-60),
            new Vector2(10,-80),
            new Vector2(50,-30),
            new Vector2(50,-50),
            new Vector2(15,-15),
            new Vector2(20,-40)
        );
        
        poses[3] = new Pose(
            new Vector2(40,5),
            new Vector2(50,10),
            new Vector2(20,-30),
            new Vector2(40,-10),
            new Vector2(60,-10),
            new Vector2(40,-20),
            new Vector2(70,0),
            new Vector2(100,-10),
            new Vector2(20,0),
            new Vector2(60,0)
        );
        poses[6] = new Pose(
            new Vector2(20,-30),
            new Vector2(40,-20),
            new Vector2(10,20),
            new Vector2(0,40),
            new Vector2(60,30),
            new Vector2(60,50),
            new Vector2(80,-10),
            new Vector2(70,-30),
            new Vector2(20,0),
            new Vector2(60,10)
        );
        poses[9] = new Pose(
            new Vector2(30,-20),
            new Vector2(50,-10),
            new Vector2(40,20),
            new Vector2(20,30),
            new Vector2(20,20),
            new Vector2(-10,30),
            new Vector2(60,20),
            new Vector2(70,-10),
            new Vector2(20,0),
            new Vector2(50,10)
        );
        lerp_pose(0, 3);
        lerp_pose(3, 6);
        lerp_pose(6, 9);
        
        reset(angle);
        damage = 15;
        hitV = Math.PI/2;
    }
    protected void setHitzone(){
        for (int i=3; i<=9; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getLegR1());
            hitzone[i][0].r = 35;
        }        
    }

}

class M_Guard_F extends M_Guard{
    public M_Guard_F(double angle, Map<Player, Boolean> others) {
        super(angle, others);
        poses[0] = new Pose(
            new Vector2(-10,-40),
            new Vector2(10,-40),
            new Vector2(20,-40),
            new Vector2(30,-20),
            new Vector2(0,-70),
            new Vector2(-20,-80),
            new Vector2(20,-60),
            new Vector2(30,-80),
            new Vector2(0,-20),
            new Vector2(-10,-50)
        );
    }
}