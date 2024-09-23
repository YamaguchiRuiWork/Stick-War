import java.util.*;

class M_Attack_Neutral_A extends Motion{
    M_Attack_Neutral_A(double angle,Map<Player,Boolean> others){
        super(1, angle, others);
    }
    M_Attack_Neutral_A(int time, double angle,Map<Player,Boolean> others){
        super(time, angle, others);
    }
}
class M_Attack_Side_A extends Motion{
    M_Attack_Side_A(double angle,Map<Player,Boolean> others){
        super(1, angle, others);
    }
    M_Attack_Side_A(int time, double angle,Map<Player,Boolean> others){
        super(time, angle, others);
    }
}
class M_Attack_Side_G extends Motion{
    M_Attack_Side_G(double angle,Map<Player,Boolean> others){
        super(1, angle, others);
    }
    M_Attack_Side_G(int time, double angle,Map<Player,Boolean> others){
        super(time, angle, others);
    }
}
class M_Attack_Up_A extends Motion{
    M_Attack_Up_A(double angle,Map<Player,Boolean> others){
        super(1, angle, others);
    }
    M_Attack_Up_A(int time, double angle,Map<Player,Boolean> others){
        super(time, angle, others);
    }
}
class M_Attack_Up_G extends Motion{
    M_Attack_Up_G(double angle,Map<Player,Boolean> others){
        super(1, angle, others);
    }
    M_Attack_Up_G(int time, double angle,Map<Player,Boolean> others){
        super(time, angle, others);
    }
}
class M_Guard extends Motion{
    public M_Guard(double angle, Map<Player, Boolean> others) {
        super(1, angle, others);
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
        cancel[0]=true;
    }
}



class M_hit extends Motion {
    M_hit(double angle,Map<Player,Boolean> others){
        super(11,angle,others);
        poses[0] = new Pose(
            new Vector2(10,-40),
            new Vector2(-30,-30),
            new Vector2(-5,-40),
            new Vector2(-25,-30),
            new Vector2(-0,-50),
            new Vector2(-10,-70),
            new Vector2(10,-70),
            new Vector2(20,-80),
            new Vector2(0,-20),
            new Vector2(20,-60)
        );
        poses[1]=new Pose(poses[0]);
        poses[2]=new Pose(poses[0]);
        poses[5] = new Pose(
            new Vector2(-30,10),
            new Vector2(-50,10),
            new Vector2(-30,-30),
            new Vector2(-50,-10),
            new Vector2(-80,10),
            new Vector2(-100,0),
            new Vector2(-80,0),
            new Vector2(-100,-20),
            new Vector2(-20,0),
            new Vector2(-60,0)
        );
        poses[10] = new Pose(poses[5]);
        lerp_pose(2, 5);
        lerp_pose(5, 10);
        head[0].x = 15;
        head[1].x = -30;
        head[2].x = 15;
        reset(angle);
        cancel[10] = true;
        cancel[9] = true;
        cancel[8] = true;
        cancel[7] = true;
        cancel[6] = true;
    }
}

class M_Attack_Neutral_G extends Motion{
    M_Attack_Neutral_G(int time,double angle,Map<Player,Boolean> others){
        super(time, angle, others);
    }
    M_Attack_Neutral_G(double angle,Map<Player,Boolean> others){
        super(10, angle, others);
    }

}

class M_Attack_Down  extends Motion{
    M_Attack_Down (double angle,Map<Player,Boolean> others){
        super(10, angle, others);
        poses[0] = new Pose(
            new Vector2(-20,-30),
            new Vector2(-10,-50),
            new Vector2(20,-25),
            new Vector2(40,-30),
            new Vector2(10,-80),
            new Vector2(10,-100),
            new Vector2(20,-40),
            new Vector2(50,-50),
            new Vector2(0,-20),
            new Vector2(10,-60)
        );
        poses[3] = new Pose(
            new Vector2(-20,-40),
            new Vector2(0,-50),
            new Vector2(20,-40),
            new Vector2(40,-30),
            new Vector2(10,-80),
            new Vector2(0,-100),
            new Vector2(40,-60),
            new Vector2(70,-60),
            new Vector2(0,-20),
            new Vector2(20,-60)
        );
        poses[4] = new Pose(poses[3]);
        poses[9] = new Pose(
            new Vector2(-20,-30),
            new Vector2(-10,-50),
            new Vector2(10,-40),
            new Vector2(30,-40),
            new Vector2(-10,-80),
            new Vector2(-20,-100),
            new Vector2(20,-80),
            new Vector2(20,-100),
            new Vector2(0,-20),
            new Vector2(0,-60)
        );

        lerp_pose(0,3);
        lerp_pose(4,9);
        reset(angle);
        
        damage = 5;
    }
    protected void setHitzone(){
        for (int i=0; i<=5; i++) {
            hitzone[i][0].c = new Vector2(current_poses[i].getLegL0());
            hitzone[i][0].r = 20;
        }        
    }
}

class M_jump extends Motion{
    M_jump(double angle , Map<Player,Boolean> others){
        super(5, angle, others);
        poses[0] = new Pose(
            new Vector2(-20,-30),
            new Vector2(-20,-60),
            new Vector2(15,-30),
            new Vector2(30,-40),
            new Vector2(0,-50),
            new Vector2(-30,-70),
            new Vector2(20,-40),
            new Vector2(10,-70),
            new Vector2(0,-20),
            new Vector2(-10,-40)
        );
        poses[4] = new Pose(
            new Vector2(-30,-30),
            new Vector2(-30,-50),
            new Vector2(15,-35),
            new Vector2(30,-50),
            new Vector2(-10,-80),
            new Vector2(-30,-110),
            new Vector2(10,-70),
            new Vector2(-10,-90),
            new Vector2(0,-20),
            new Vector2(-10,-60)
        );
        lerp_pose(0, 4);
        reset(angle);
    }
}

class M_neutral_A extends Motion{
    M_neutral_A(double angle , Map<Player,Boolean> others){
        super(1,angle, others);
        poses[0] = new Pose(
            new Vector2(-30,-30),
            new Vector2(-30,-50),
            new Vector2(15,-35),
            new Vector2(30,-50),
            new Vector2(-10,-80),
            new Vector2(-30,-110),
            new Vector2(10,-70),
            new Vector2(-10,-90),
            new Vector2(0,-20),
            new Vector2(-10,-60)
        );
        head[0] = new Vector2(0,0);
        for (int i=0; i<cancel.length; i++) {
            cancel[i] = true;
        }
        rotate(angle);
    }
}

class M_neutral extends Motion{
    M_neutral(double angle , Map<Player,Boolean> others){
        super(1,angle, others);
        poses[0] = new Pose();
        head[0] = new Vector2(0,0);
        for (int i=0; i<cancel.length; i++) {
            cancel[i] = true;
        }
        rotate(angle);
    }
    @Override
    public boolean update(){
        return true;
    }
}

class M_run extends Motion{
    M_run(double angle, Map<Player,Boolean> others){
        super(3,angle, others);

        //pose
        Pose p1,p2,p3;
        Vector2 armR0,armR1,armL0,armL1,legR0,legR1,legL0,legL1,neck,hip;
        armR0 = new Vector2(-30, -30);
        armR1 = new Vector2(-20, -50);
        armL0 = new Vector2(10, -40);
        armL1 = new Vector2(30, -30);
        legR0 = new Vector2(-30, -90);
        legR1 = new Vector2(-50, -80);
        legL0 = new Vector2(10, -70);
        legL1 = new Vector2(10, -100);
        neck = new Vector2(0, -20);
        hip = new Vector2(-20, -60);
        p1 = new Pose(armR0,armR1,armL0,armL1,legR0,legR1,legL0,legL1,neck,hip);

        legL0.add(-10,0);
        legL1.add(-20,0);
        legR0.add(20,10);
        legR1.add(10,10);
        p2 = new Pose(armR0,armR1,armL0,armL1,legR0,legR1,legL0,legL1,neck,hip);
        p3 = new Pose(
            new Vector2(-30,-30),
            new Vector2(-20,-50),
            new Vector2(10,-40),
            new Vector2(30,-30),
            new Vector2(10,-70),
            new Vector2(0,-100),
            new Vector2(-30,-80),
            new Vector2(-50,-70),
            new Vector2(0,-20),
            new Vector2(-20,-60)
        );
        poses[0] = p1;
        poses[1] = p2;
        poses[2] = p3;
        
        cancel[2] = true;
        rotate(angle);

    }

    protected void setHitzone(){
        //nothing       
    }
}
