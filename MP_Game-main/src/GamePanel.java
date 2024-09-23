import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel{
    private GameModel model;                    //モデル
    private GameView view;                      //ビュー
    protected int width, height;                //stageの大きさ
    private int origin_x, origin_y;             //原点のx,y座標(Javaの座標基準)
    private Graphics2D g2;                      //2Dグラフィックス
    private MyGraphics mg;                      //追加グラフィックス
    private BufferedImage background = null;    //背景画像
    private BasicStroke stroke;
    
    public GamePanel(GameModel m, GameView v){
        this(1080, 600, 100, 660, m, v, "backimg.jpg");
    }
    public GamePanel(int w, int h, int x, int y, GameModel m, GameView v, String filename){
        model = m;
        view = v;
        width = w;
        height = h;
        origin_x = x;
        origin_y = y;
        mg = new MyGraphics();
        this.setBackground(Color.white);
        //背景画像の変換(png形式からBufferedImageクラスへ)
        try{
            background = ImageIO.read(getClass().getResource(filename));
        }catch(IOException ex){
            ex.printStackTrace();
            background = null;
        }
        stroke = new BasicStroke(6);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D)g;                     //GraphicsをGraphics2Dへキャスト
        mg.link(g2);                            //Graphics2DをMyGraphicsが保持
        g.drawImage(background, 0, 0, this);    //背景画像
        this.drawHuman(g);
        this.drawstage(g);
    }
    //ステージ描画
    private void drawstage(Graphics g){
        g.setColor(Color.black);
        g2.setStroke(stroke);
        g.drawRect(100, 60, width, height);
    }
	//キャラ描画
    private void drawHuman(Graphics g){
        for(Player player: model.getPlayers()){
            if(!(player.getAlive())){continue;}		//消滅判定
            Chara chara = player.getChara();
            Vector2 h_v = chara.getHead();
            Color col = chara.getColor();
			//当たり判定エフェクト
            g2.setStroke(stroke);
            for(Circle cir: chara.getHitzone()){
                mg.fillOval_G(convert(h_v.addv(cir.c)), cir.r, 6, new Color(col.getRed(), col.getGreen(), col.getBlue(), 125));
            }
            //キャラの枠組み
            Pose p = chara.getPose();
            g.setColor(col);
            g2.setStroke(stroke);
            mg.mydrawOval(convert(h_v),40,40);
            mg.mydrawLine(convert(h_v.addv(p.getNeck())), convert(h_v.addv(p.getHip())));
            mg.mydrawLine(convert(h_v.addv(p.getNeck())), convert(h_v.addv(p.getArmR0())));
            mg.mydrawLine(convert(h_v.addv(p.getArmR0())), convert(h_v.addv(p.getArmR1())));
            mg.mydrawLine(convert(h_v.addv(p.getNeck())), convert(h_v.addv(p.getArmL0())));
            mg.mydrawLine(convert(h_v.addv(p.getArmL0())), convert(h_v.addv(p.getArmL1())));
            mg.mydrawLine(convert(h_v.addv(p.getHip())), convert(h_v.addv(p.getLegR0())));
            mg.mydrawLine(convert(h_v.addv(p.getLegR0())), convert(h_v.addv(p.getLegR1())));
            mg.mydrawLine(convert(h_v.addv(p.getHip())), convert(h_v.addv(p.getLegL0())));
            mg.mydrawLine(convert(h_v.addv(p.getLegL0())), convert(h_v.addv(p.getLegL1())));
            //キャラの頭の中
            g.setColor(mg.changebright(col, (float)(200-chara.getPercent())/200F));	//色の明度をキャラの%に従って変更(%が200以上だと,色一定)
			//死亡していない
            if(chara.getAlive()){
                mg.myfillOval(convert(h_v),35,35);
			//死亡している
            }else{
                for(int i = 0; i < 6; i++){
                    int time = chara.getMotion().getTime();
                    if(time == 0){break;}
					//time利用してランダムな位置に円を描画
                    int area = 20 * time;
                    Vector2 randv = new Vector2((int)(Math.random()*area-area/2),(int)(Math.random()*area-area/2));
                    mg.myfillOval(convert(h_v.addv(randv)),20,20);
                }
            }
            //剣士であれば,剣を描画
			double swordangle = chara.getSwordangle();
            if(swordangle != -1){
                g.setColor(Color.gray);
                //刀
                mg.drawLineAnfgle(convert(h_v.addv(p.getArmR1())), 65, swordangle);
                //つか
                mg.drawLineAnfgle(convert(h_v.addv(p.getArmR1())), 15, swordangle + Math.PI);
                //つば
                mg.mydrawLine(convert(h_v.addv(p.getArmR1().addv(new Vector2(5,15).rotateV(swordangle)))),convert(h_v.addv(p.getArmR1().addv(new Vector2(5,-15).rotateV(swordangle)))));
            }
			//スタートカウント
            long time = model.getCount();
            if(time >= 0 && time < 5){		//3~0のカウントの間の時
                g.setColor(Color.gray);
                g.setFont(new Font("Arial", 0, 200));
                g.drawString(Long.toString(time), 600, 360);
            }
        }
    }
	//ゲーム上の座標を,Java基準の座標に置き換える
    private Vector2 convert(Vector2 b_v){
        Vector2 a_v = new Vector2(b_v.x+origin_x,-(b_v.y)+origin_y);
        return a_v;
    }
    //毎フレーム呼び出す
    public void update(){
        this.repaint();
    }
	//ゲーム終了時の画面遷移
    public void gameover(){
        view.setLayout("fpanel");
        view.fpanel.setResult(model);
    }
}