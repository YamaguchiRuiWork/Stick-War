import java.util.*;

class AI_Controller extends GameController{
    private Player me;
    private Player enemy;
    private static int clearance =70;
    AI_Controller(GameModel model, GameView view){
        super(model, view,'\0','\0', '\0', '\0', '\0', '\0');
    }
    @Override
    public Controller_state getState(){
        if(!isActive(enemy))
            setEnemy();
        Vector2 enV =  new Vector2(enemy.getChara().getHead());
        Vector2 myV = new Vector2(me.getChara().getHead());
        int gap = enV.x - myV.x ;
        if(Math.abs(gap)>clearance+30){setMostNearEnemy();}
        if(gap > clearance){
            // go to right
            return n4;
        }else if(gap < -1*clearance){
            //go to left
            return n2;
        }
        double r = Math.random();
        if(enemy.getChara().getMotion() == enemy.getChara().getM_ana()){return n5;}
        if(r<0.3){return n1;}
        else if(r<0.4){return n6;}
        else if(r<0.6){
            if(gap<0){return n7;}
            else{return n8;}
        }
        else if(r<0.8){return n9;}
        else {return n10;}
    }
    public void setPlayer(Player p){
        me = p;
    }
    private void setEnemy(){
        ArrayList<Player> players = model.getPlayers();
        int rand = (int)Math.floor((Math.random()*players.size()));
        int i=0;
        while((!isActive(enemy) || enemy==me)&&i<players.size()){
            rand = (rand+i)%players.size();
            enemy = players.get(rand);
            i++;
        }
        System.out.println(me.getName() + "-->" + enemy.getName());
    }
    private boolean isActive(Player p){
        if(p == null){return false;}
        else if(p.getChara().getAlive() && p.getAlive()){return true;}
        else{return false;}
    }
    private void setMostNearEnemy(){
        ArrayList<Player> players = model.getPlayers();
        for(Player p: players){
            if(p!=me && p.getChara().getAlive() && p.getAlive()){
                if(enemy.getChara().getHead().getD2(me.getChara().getHead()) >= p.getChara().getHead().getD2(me.getChara().getHead())){
                    enemy = p;
                }
            }
        }
    }
}
