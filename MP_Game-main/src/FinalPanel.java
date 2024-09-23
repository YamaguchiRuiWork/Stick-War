import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FinalPanel extends JPanel implements ActionListener{
    private GameView view;  //ビュー
    private JLabel label;   //結果を貼り付けるラベル

    public FinalPanel(GameView v){
        view = v;
        Color c = new Color(80,80,80);
        this.setBackground(c);
        this.setLayout(new BorderLayout());
        JPanel bPanel = new JPanel();
        bPanel.setBackground(c);

        //キャラを再設定ボタン
        JButton button_next = new JButton("Return Config");
        button_next.setFont(new Font("Arial", Font.BOLD, 24));
        button_next.addActionListener(this);
        button_next.setActionCommand("spanel");
        //キャラそのままで,再ゲームスタートボタン
        JButton button_continue = new JButton("Continue");
        button_continue.setFont(new Font("Arial", Font.BOLD, 24));
        button_continue.addActionListener(this);
        button_continue.setActionCommand("gpanel");
        //結果表示用ラベル
        label = new JLabel("",JLabel.CENTER);

        bPanel.add(button_next, BorderLayout.CENTER);
        bPanel.add(button_continue, BorderLayout.CENTER);
        this.add(bPanel, BorderLayout.SOUTH);
        this.add(label,BorderLayout.CENTER);
    }

    //結果表示用ラベルに結果を表示
    public void setResult(GameModel model){
        ArrayList<Player> players = model.getPlayers();
        String str = "<html>";
        for(Player p: players){
            //勝者
            if(p.getChara().getAlive()){
                Color c = p.getChara().getColor();
                String s;
                s = Integer.toHexString(c.getRed()*0x10000 + c.getGreen()*0x100 +c.getBlue());   //キャラの色を16進数6桁の文字列に変換
                str += "<div style='padding:20px;font-size:50px;background:#ffffff;color:#"+s+";'>WINNER: "+ p.getName()+"</div><br>";
                break;
            }
        }
        //全てのプレイヤー
        for(Player p: players){
            Color c = p.getChara().getColor();
            String s;
            s =Integer.toHexString(c.getRed()*0x10000 + c.getGreen()*0x100 +c.getBlue());       //キャラの色を16進数6桁の文字列に変換
            str += "<div style='font-size:15px;color:#"+s+";'>";
            str += p.getName()+"/   被ダメ: "+p.getChara().getPercent()+" /   与ダメ: "+p.getChara().getYoDamage();
            str += "</div><br>";
        }
        str += "</html>";
        label.setText(str);
    }

    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if(s == "gpanel"){                  //キャラそのままで,再ゲームスタート
            view.spanel.actionPerformed(e);
        }else{                              //キャラを再設定
            view.setLayout(s);
        }
    }
}
