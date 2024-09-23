import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SettingPanel extends JPanel implements ActionListener, MouseListener{
    protected GameModel model;  //モデル
    protected GameView view;    //ビュー
    private ArrayList <SettingPlayerPanel> playerslist;
    private String[] charadate = {"Fighter", "Swordsman", "AI(Fighter)","AI(Swordsman)"};   //キャラの種類
    private String[] colordate = {"red", "green", "blue", "orange","pink","yellow"};        //キャラの色
    private Color[] colors = {new Color(255,51,51),new Color(51,255,51),new Color(30,144,255),new Color(255,175,0),new Color(255,105,180),new Color(255,240,30)};   //キャラの色のColorクラス
    private PlayersPanel PlayersPanel;
    private JButton button_start;       //ゲームスタートボタン
    private JButton button_add_player;  //プレーヤー数増加ボタン
    private JButton button_del_player;  //プレーヤー数減少ボタン

    public SettingPanel(GameModel m, GameView v){
        model = m;
        view = v;
        this.setLayout(new BorderLayout());
        //プレイヤー情報を表すパネル
        playerslist = new ArrayList<>();
        PlayersPanel = new PlayersPanel();
        this.add(PlayersPanel,BorderLayout.CENTER);
        //プレーヤー数の増加減少パネル
        JPanel adpanel = new JPanel();
        adpanel.setLayout(new GridLayout(2,1));
        button_add_player = new JButton("<html><div style='font-size:50px;text-align:center;'>+</div><br>add player</html>");
        button_add_player.addActionListener(PlayersPanel);
        button_add_player.setActionCommand("add");
        button_add_player.addMouseListener(this);
        adpanel.add(button_add_player);
        button_del_player = new JButton("<html><div style='font-size:50px;text-align:center;'>-</div><br>del player</html>");
        button_del_player.addActionListener(PlayersPanel);
        button_del_player.setActionCommand("del");
        button_del_player.addMouseListener(this);
        adpanel.add(button_del_player);
        this.add(adpanel, BorderLayout.EAST);
        //ゲームスタートボタン
        button_start = new JButton("<html><div style='margin:50px;'>Game Start</div></html>");
        button_start.setFont(new Font("Arial", Font.BOLD, 40));
        this.add(button_start,BorderLayout.SOUTH);
        button_start.addActionListener(this);
        button_start.setActionCommand("gpanel");
        button_start.addMouseListener(this);
    }

    class PlayersPanel extends JPanel implements ActionListener{
        public PlayersPanel(){
            this.setLayout(new GridLayout(1,0));
            add('w','a','s','d','q','e');
            add('g','v','b','n','f','h');
            add('i','j','k','l','u','o');
        }
        public void actionPerformed(ActionEvent e){
            String cmd = e.getActionCommand();
            if(cmd == "add"){
                add();
            }else if(cmd == "del"){
                del();
            }
            //update
            validate();
        }
        private void add(){
            add('w', 'a', 's', 'd', 'q', 'e');
        }
        //プレイヤーの追加
        private void add(char w, char a, char s, char d, char q, char e){
            int i = playerslist.size();
            playerslist.add(new SettingPlayerPanel(w,a,s,d,q,e,i));
            this.add(playerslist.get(i));
        }
        //プレイヤーの削除
        private void del(){
            int i = playerslist.size();
            if(i<=2){return;}
            this.remove(playerslist.get(i-1));
            playerslist.remove(i-1);
        }
    }

    class SettingPlayerPanel extends JPanel implements PopupMenuListener{
        JComboBox<String> charaBox; //キャラの種類のドロップドロップダウンリスト
        JComboBox<String> colorBox; //キャラの色のドロップドロップダウンリスト
        KeyField text_up;
        KeyField text_right;
        KeyField text_down;
        KeyField text_left;
        KeyField text_attack;
        KeyField text_guard;
        JTextField name;        //キャラの名前
        SettingPlayerPanel(){
            this('w', 'a', 's', 'd', 'q', 'e',0);
        }
        SettingPlayerPanel(char w, char a, char s, char d, char q, char e, int i){
            this(w,a,s,d,q,e,"player"+i);
        }
        SettingPlayerPanel(char w, char a, char s, char d, char q, char e, String n){
            setBorder(new javax.swing.border.LineBorder(Color.BLACK,2,true));
            setLayout(new GridLayout(0,2));
            Font font = new Font("Arial",0, 24);
            //JTextFieldの作成
            name = new JTextField(n);
            name.setFont(new Font("Arial",0, 24));
            //KeyFieldの作成
            text_up = new KeyField(w);
            text_right = new KeyField(a);
            text_down = new KeyField(s);
            text_left = new KeyField(d);
            text_attack = new KeyField(q);
            text_guard = new KeyField(e);
            //ドロップダウンリストの作成
            charaBox = new JComboBox<>(charadate);
            colorBox = new JComboBox<>(colordate);
            colorBox.addPopupMenuListener(this);
            charaBox.setFont(font);
            colorBox.setFont(font);
            //背景の色
            this.setBackground(colors[0]);
            //設定部分
            this.add(new JLabel("NAME:",JLabel.CENTER));
            this.add(name);
            this.add(new JLabel("JOB:",JLabel.CENTER));
            this.add(charaBox);
            this.add(new JLabel("COLOR:",JLabel.CENTER));
            this.add(colorBox);
            this.add(new JLabel("up:",JLabel.CENTER));
            this.add(text_up);
            this.add(new JLabel("left:",JLabel.CENTER));
            this.add(text_right);
            this.add(new JLabel("down:",JLabel.CENTER));
            this.add(text_down);
            this.add(new JLabel("right:",JLabel.CENTER));
            this.add(text_left);
            this.add(new JLabel("attack:",JLabel.CENTER));
            this.add(text_attack);
            this.add(new JLabel("guard:",JLabel.CENTER));
            this.add(text_guard);
            //文字の色
            Component[] components = getComponents();
            for(Component c: components){
                c.setForeground(Color.black); 
            }
        }
        //ドロップダウンリストにおけるキャラの種類の番号の出力
        public int getCharaIndex(){
            return charaBox.getSelectedIndex();
        }
        //ドロップダウンリストにおけるキャラの色の番号の出力
        public int getColorIndex(){
            return colorBox.getSelectedIndex();
        }
        //キャラの名前の出力
        public String getName(){
            return name.getText();
        }
        //設定されているキーの出力
        public char getKey_up(){return text_up.getKeyChar();}
        public char getKey_right(){return text_right.getKeyChar();}
        public char getKey_down(){return text_down.getKeyChar();}
        public char getKey_left(){return text_left.getKeyChar();}
        public char getKey_attack(){return text_attack.getKeyChar();}
        public char getKey_gurad(){return text_guard.getKeyChar();}
        
        public void popupMenuCanceled(PopupMenuEvent e){}
        //背景の色の変更
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e){
            int ind = colorBox.getSelectedIndex();
            Color c = colors[ind];
            this.setBackground(c);
        }
        public void popupMenuWillBecomeVisible(PopupMenuEvent e){}

        class KeyField extends JButton implements ActionListener, KeyListener{
            char keyChar;   //設定されているキー
            boolean change; //キーの設定受け付けるかの真偽
            KeyField(char c){
                change = true;
                setKeyChara(c);
                addActionListener(this);
                addKeyListener(this);
                setFont(new Font("Arial", Font.PLAIN, 24));
                setFocusable(true);
            }
            //キーの設定
            private void setKeyChara(char c){
                if(!change){return;}
                setText(c+"");
                keyChar = c;
                change = false;
            }
            //設定しているキーの出力
            public char getKeyChar(){return keyChar;}
            //キーの設定受付中
            public void actionPerformed(ActionEvent e){
                setText("press key...");
                requestFocus();
                change = true;
            }
            //キーの入力
            public void keyTyped(KeyEvent e){
                setKeyChara( e.getKeyChar());
            }
            public void keyReleased(KeyEvent e){}
            public void keyPressed(KeyEvent e){}
        }
    }

    //ゲームスタート
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();    //sにはgpanelが入る
        int m = 0,n = 1080;
        n /= playerslist.size()+1;
        model.getPlayers().clear();         //モデルが持つキャラ情報をクリアにする
        for(SettingPlayerPanel pl: playerslist){
            Color col = Color.white;
            m += n;
            Vector2 vec = new Vector2(m,200);
            Chara ch;
            GameController gc;
            String na;
            int index;
            //キャラの色
            index = pl.getColorIndex();
            col = colors[index];
            //コントローラーの作成
            gc = new GameController(model,view,pl.getKey_up(),pl.getKey_right(),pl.getKey_down(),pl.getKey_left(),pl.getKey_attack(),pl.getKey_gurad());
            //名前
            na = pl.getName();
            //Modelにプレイヤーの情報を追加
            index = pl.getCharaIndex();
            java.util.List<String> charaList = Arrays.asList(charadate); //ドロップダウンリストをStringリストに変換
            if(index == charaList.indexOf("Fighter")){
                ch = new Fighter(vec , col);
                model.addPlayer(ch, gc, na);
            }else if(index == charaList.indexOf("Swordsman")){
                ch = new Swordsman(vec, col);
                model.addPlayer(ch, gc, na);
            }else if(index == charaList.indexOf("AI(Fighter)")){
                ch = new Fighter(vec, col);
                AI_Controller gcai = new AI_Controller(model, view);
                model.addPlayer(ch, gcai, na);
                gcai.setPlayer(model.getPlayers().get(model.getPlayers().size()-1));
            }else if(index == charaList.indexOf("AI(Swordsman)")){
                ch = new Swordsman(vec, col);
                AI_Controller gcai = new AI_Controller(model, view);
                model.addPlayer(ch, gcai, na);
                gcai.setPlayer(model.getPlayers().get(model.getPlayers().size()-1));
            }else{
                ch = new Chara(vec , col);  //デバック用のキャラ
                model.addPlayer(ch, gc, na);
            }

        }
        //playerの初期化
        model.initPlayers();
        //画面遷移
        view.setLayout(s);
    }
    public void mouseEntered(MouseEvent e){
        JButton b = (JButton)e.getSource();
        if(b == button_start){
            b.setForeground(Color.red);
        }else if(b == button_add_player || b == button_del_player){
            b.setForeground(Color.blue);
        }
    }
    public void mouseExited(MouseEvent e){
        JButton b = (JButton)e.getSource();
        b.setForeground(Color.black);
    }
    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}
