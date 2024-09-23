import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TitlePanel extends JPanel implements ActionListener, MouseListener{
    private GameView view;                      //ビュー
    private JButton button_next;                //スタートボタン
    private BufferedImage background = null;    //背景画面

    public TitlePanel(GameView v){
        view = v;
        this.setLayout(null);                   //レイアウトなし
        button_next = new JButton("Next");
        button_next.setBounds(1000, 550, 200, 100);
        button_next.setFont(new Font("Arial", Font.BOLD, 40));
        button_next.addActionListener(this);
        button_next.addMouseListener(this);
        button_next.setActionCommand("spanel");
        this.add(button_next);

        //背景画像の変換(pngからBufferedImageクラスへ)
        try{
            background = ImageIO.read(getClass().getResource("title.jpg"));
        }catch(IOException ex){
            ex.printStackTrace();
            background = null;
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        view.setLayout(s);
    }
    public void mouseEntered(MouseEvent e){
        button_next.setForeground(Color.red);   //ボタンの文字が赤色に変化
    }
    public void mouseExited(MouseEvent e){
        button_next.setForeground(Color.black); //ボタンの文字が黒色に変化
    }
    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
}