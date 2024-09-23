import java.awt.event.*;

public class GameController implements KeyListener{
    protected GameModel model;
    protected GameView view;
    private Controller_state state;
    private char w,a,s,d,q,g; //q,g ガード、アタック
    private int keystate;
    static final int KEYSTATE_W=1;
    static final int KEYSTATE_A=2;
    static final int KEYSTATE_S=4;
    static final int KEYSTATE_D=8;
    static final int KEYSTATE_Q=16;
    static final int KEYSTATE_E=32;
    protected Controller_state n1 = new C_Up();
    protected Controller_state n2 = new C_Left();
    protected Controller_state n3 = new C_Neutral();
    protected Controller_state n4 = new C_Right();
    protected Controller_state n5 = new C_Guard();
    protected Controller_state n6 = new C_Attack_Down();
    protected Controller_state n7 = new C_Attack_Left();
    protected Controller_state n8 = new C_Attack_Right();
    protected Controller_state n9 = new C_Attack_Up();
    protected Controller_state n10 = new C_Attack_Neutral();
    protected Controller_state n11 = new C_Down();
    public GameController(GameModel m, GameView v){
        this(m, v,'w','a','s','d','q','e');
    }
    public GameController(GameModel m, GameView v, char w, char a, char s, char d , char q, char e){
        model = m;
        view = v;
        this.w = w;
        this.a = a;
        this.s = s;
        this.d = d;
        this.q = q;
        this.g = e;
        v.gpanel.addKeyListener(this);
        state = new C_Neutral();
    }
    public Controller_state getState(){
        return state;
    }
    public void keyTyped(KeyEvent e){}
    
    public void keyReleased(KeyEvent e){
        char c = e.getKeyChar();

        if(c==w){
            keystate &= ~KEYSTATE_W;
        }else if(c==a){
            keystate &= ~KEYSTATE_A;
        }else if(c==s){
            keystate &= ~KEYSTATE_S;
        }else if(c==d){
            keystate &= ~KEYSTATE_D;
        }else if(c==q){
            keystate &= ~KEYSTATE_Q;
        }else if(c==g){
            keystate &= ~KEYSTATE_E;
        }

        if(keystate == KEYSTATE_W ){
            state = n1;
        }else if(keystate == KEYSTATE_A){
            state = n2;
        }else if(keystate == KEYSTATE_S){
            state = n11;
        }else if(keystate == KEYSTATE_D){
            state = n4;
        }else if(keystate == KEYSTATE_E){
            state = n5;
        }else if(keystate == KEYSTATE_Q){
            state = n10;
        }else if(keystate == KEYSTATE_A + KEYSTATE_Q){
            state = n7;
        }else if(keystate == KEYSTATE_D + KEYSTATE_Q){
            state = n8;
        }else if(keystate == KEYSTATE_W + KEYSTATE_Q){
            state = n9;
        }else if(keystate == KEYSTATE_S + KEYSTATE_Q){
            state = n6;
        }else if(keystate == 0){
            state = n3;
        }
    }
    /*keystateに数字を足していく */
    public void keyPressed(KeyEvent e){
        char c = e.getKeyChar();
        if(c==w){
            keystate |=KEYSTATE_W;
        }else if(c==a){
            keystate |=KEYSTATE_A;
        }else if(c==s){
            keystate |=KEYSTATE_S;
        }else if(c==d){
            keystate |=KEYSTATE_D;
        }else if(c==q){
            keystate |=KEYSTATE_Q;
        }else if(c==g){
            keystate |=KEYSTATE_E;
        }
        
        if(keystate == KEYSTATE_W || keystate == KEYSTATE_A + KEYSTATE_W || keystate == KEYSTATE_D + KEYSTATE_W){
            state = n1;
        }else if(keystate == KEYSTATE_A){
            state = n2;
        }else if(keystate == KEYSTATE_S){
            state = n11;
        }else if(keystate == KEYSTATE_D){
            state = n4;
        }else if(keystate == KEYSTATE_E || keystate == KEYSTATE_E + KEYSTATE_A || keystate == KEYSTATE_E + KEYSTATE_D || keystate ==  KEYSTATE_E + KEYSTATE_S){
            state = n5;
        }else if(keystate == KEYSTATE_Q){
            state = n10;
        }else if(keystate == KEYSTATE_A + KEYSTATE_Q){
            state = n7;
        }else if(keystate == KEYSTATE_D + KEYSTATE_Q){
            state = n8;
        }else if(keystate == KEYSTATE_W + KEYSTATE_Q){
            state = n9;
        }else if(keystate == KEYSTATE_S + KEYSTATE_Q){
            state = n6;
        }
    }
}
