class Controller_state{
    Motion motion;
	public void change_character(Chara c){
        c.setIsmoving(false);
    }
}
class C_Neutral extends Controller_state{
    public void change_character(Chara c){
        c.setIsmoving(false);
    }
}
class C_Right extends Controller_state{
    public void change_character(Chara c){
        if(c.getAngle() != 0){
            c.getV().x = 1; //振り向き
            c.setAngle(0);
        }else{
            c.getV().x = c.getSpeed();//移動
        }
        if(!c.getIsinair()){
            c.getM_r().reset(c.getAngle());
            c.setMotion(c.getM_r());
        }else{
            c.getM_n_a().reset(c.getAngle());
            c.setMotion(c.getM_n_a());
        }
        c.setIsmoving(true);
    }
}
class C_Left extends Controller_state{
    public void change_character(Chara c){
        if(c.getAngle() != Math.PI){
            c.getV().x = -1;
            c.setAngle(Math.PI);
        }else{
            c.getV().x = c.getSpeed()*-1;
        }
        if(!c.getIsinair()){//地上
            c.getM_r().reset(c.getAngle());
            c.setMotion(c.getM_r());
        }else{//空中
            c.getM_n_a().reset(c.getAngle());
            c.setMotion(c.getM_n_a());
        }
        c.setIsmoving(true);
    }
}
class C_Up extends Controller_state{
    public void change_character(Chara c){
        if(c.getIsinair()){return;}
        /*角度調整 */
        double angle = c.getAngle();
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
        if(angle > Math.PI*0.5 && angle <Math.PI*1.5){
            angle = Math.PI;
        }else{
            angle = 0;
        }
        c.setAngle(angle);
        c.getM_j().reset(c.getAngle());
        c.setMotion(c.getM_j());
        c.getV().y = 50;
        c.setIsmoving(true);
    }
}
class C_Down extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
    }
}
class C_Attack_Neutral extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
        double angle = c.getAngle();
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
        if(angle > Math.PI*0.5 && angle <Math.PI*1.5){
            angle = Math.PI;
        }else{
            angle = 0;
        }
        c.setAngle(angle);

        if(c.isinair){
            c.getM_ana().reset(c.getAngle());
            c.setMotion(c.getM_ana());
        }else{
            //ground
            c.getM_ang().reset(c.getAngle());
            c.setMotion(c.getM_ang());
    
        }
    }
}
class C_Attack_Right extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
        //right
        if(c.getAngle() != 0){
            c.setAngle(0);
        }

        if(c.isinair){
            c.getM_asa().reset(c.getAngle());
            c.setMotion(c.getM_asa());
        }else{
            //ground
            c.getM_asg().reset(c.getAngle());
            c.setMotion(c.getM_asg());    
        }

    }
}
class C_Attack_Left extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
        //left
        if(c.getAngle() != Math.PI){
            c.setAngle(Math.PI);
        }

        if(c.isinair){
            c.getM_asa().reset(c.getAngle());
            c.setMotion(c.getM_asa());
        }else{
            //ground
            c.getM_asg().reset(c.getAngle());
            c.setMotion(c.getM_asg());    
        }

    }
}
class C_Attack_Up extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
        double angle = c.getAngle();
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
        if(angle > Math.PI*0.5 && angle <Math.PI*1.5){
            angle = Math.PI;
        }else{
            angle = 0;
        }

        c.setAngle(angle);

        if(c.isinair){
            c.getM_aua().reset(c.getAngle());
            c.setMotion(c.getM_aua());
        }else{
            //ground
            c.getM_aug().reset(c.getAngle());
            c.setMotion(c.getM_aug());    
        }

    }
}
class C_Attack_Down extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
        double angle = c.getAngle();
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
        if(angle > Math.PI*0.5 && angle <Math.PI*1.5){
            angle = Math.PI;
        }else{
            angle = 0;
        }
        if(!c.isinair){
            c.setAngle(angle);
            c.getM_ad().reset(c.getAngle());
            c.setMotion(c.getM_ad());
        }
    }
}
class C_Guard extends Controller_state{
    public void change_character(Chara c){
        super.change_character(c);
        double angle = c.getAngle();
        while(angle < 0){angle += Math.PI*2;}
        while(angle >= Math.PI*2){angle -= Math.PI*2;}
        if(angle > Math.PI*0.5 && angle <Math.PI*1.5){
            angle = Math.PI;
        }else{
            angle = 0;
        }
        c.setAngle(angle);
        c.getM_g().reset(c.getAngle());
        c.setMotion(c.getM_g());        
    }
}
