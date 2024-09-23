public class Pose{
    //関節の座標
    protected Vector2 armR0,armR1,armL0,armL1,legR0,legR1,legL0,legL1,neck,hip;

    public Pose(){
        armR0 = new Vector2(-20, -40);
        armR1 = new Vector2(-20, -60);
        armL0 = new Vector2(10, -40);
        armL1 = new Vector2(20, -60);
        legR0 = new Vector2(-20, -80);
        legR1 = new Vector2(-20, -100);
        legL0 = new Vector2(10, -80);
        legL1 = new Vector2(20, -100);
        neck = new Vector2(0, -20);
        hip = new Vector2(0, -60);
    }
    public Pose(Vector2 armR0, Vector2 armR1, Vector2 armL0, Vector2 armL1, Vector2 legR0, Vector2 legR1, Vector2 legL0, Vector2 legL1, Vector2 neck, Vector2 hip){
        //deep copy
        this.armR0 = new Vector2(armR0);
        this.armR1 = new Vector2(armR1);
        this.armL0 = new Vector2(armL0);
        this.armL1 = new Vector2(armL1);
        this.legR0 = new Vector2(legR0);
        this.legR1 = new Vector2(legR1);
        this.legL0 = new Vector2(legL0);
        this.legL1 = new Vector2(legL1);
        this.neck = new Vector2(neck);
        this.hip = new Vector2(hip);
    }   
    public Pose(Pose p){
        //deep copy
        this.armR0 = new Vector2(p.getArmR0());
        this.armR1 = new Vector2(p.getArmR1());
        this.armL0 = new Vector2(p.getArmL0());
        this.armL1 = new Vector2(p.getArmL1());
        this.legR0 = new Vector2(p.getLegR0());
        this.legR1 = new Vector2(p.getLegR1());
        this.legL0 = new Vector2(p.getLegL0());
        this.legL1 = new Vector2(p.getLegL1());
        this.neck = new Vector2(p.getNeck());
        this.hip = new Vector2(p.getHip());
    }
    public Vector2 getArmR0(){
        return armR0;
    }
    public Vector2 getArmR1(){
        return armR1;
    }
    public Vector2 getArmL0(){
        return armL0;
    }
    public Vector2 getArmL1(){
        return armL1;
    }
    public Vector2 getLegR0(){
        return legR0;
    }
    public Vector2 getLegR1(){
        return legR1;
    }
    public Vector2 getLegL0(){
        return legL0;
    }
    public Vector2 getLegL1(){
        return legL1;
    }
    public Vector2 getNeck(){
        return neck;
    }
    public Vector2 getHip(){
        return hip;
    }

    //回転と反転
    public void rotate(double angle){
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
        if(angle>Math.PI*0.5 && angle<Math.PI*1.5){
            angle = Math.PI-angle;
            armR0.rotate(angle);
            armR1.rotate(angle);
            armL0.rotate(angle);
            armL1.rotate(angle);
            legR0.rotate(angle);
            legR1.rotate(angle);
            legL0.rotate(angle);
            legL1.rotate(angle);
            neck.rotate(angle);
            hip.rotate(angle);
            armR0.x*=-1;
            armR1.x*=-1;
            armL0.x*=-1;
            armL1.x*=-1;
            legR0.x*=-1;
            legR1.x*=-1;
            legL0.x*=-1;
            legL1.x*=-1;
            neck.x*=-1;
            hip.x*=-1;
        }else{
            armR0.rotate(angle);
            armR1.rotate(angle);
            armL0.rotate(angle);
            armL1.rotate(angle);
            legR0.rotate(angle);
            legR1.rotate(angle);
            legL0.rotate(angle);
            legL1.rotate(angle);
            neck.rotate(angle);
            hip.rotate(angle);
        }
    }

}