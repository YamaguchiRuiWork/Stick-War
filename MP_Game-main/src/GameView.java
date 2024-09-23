import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame{
    private GameModel model;		//モデル
    private CardLayout layout;		//カードレイアウト
    private JPanel cardPanel;		//画面遷移の土台となるパネル
    protected TitlePanel tpanel;	//タイトル画面
    protected SettingPanel spanel;	//設定画面
    protected GamePanel gpanel;		//ゲーム画面
    protected FinalPanel fpanel;	//結果画面

    public GameView(GameModel m){
        model = m;
        tpanel = new TitlePanel(this);
        spanel = new SettingPanel(model, this);
        gpanel = new GamePanel(model, this);
        model.setView(gpanel);				//モデルがgpanelを保持
        fpanel = new FinalPanel(this);
        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);		//CardLayoutを用いた画面遷移
        cardPanel.add(tpanel, "tpanel");
        cardPanel.add(spanel, "spanel");
        cardPanel.add(gpanel, "gpanel");
        cardPanel.add(fpanel, "fpanel");
        this.add(cardPanel,BorderLayout.CENTER);
        tpanel.setFocusable(true);			//最初はタイトル画面にフォーカス当てる
        setTitle("Stick War");
    }
	//画面遷移
    public void setLayout(String s){
        layout.show(cardPanel, s);			//パネル切り替え
        if(s == "spanel"){
            spanel.requestFocus();
        }else if(s == "gpanel"){
            gpanel.requestFocus();
            model.TimerStart();				//タイマーを稼働してゲーム開始
        }else if(s == "fpanel"){
            fpanel.requestFocus();
        }else if(s == "tpanel"){
            tpanel.requestFocus();
        }
    }
}

