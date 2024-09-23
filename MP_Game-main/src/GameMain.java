import javax.swing.*;

public class GameMain {
    public static void main(String argv[]){     //ゲームの起動
        GameModel model = new GameModel();	    //Modelを生成
        GameView view = new GameView(model);	//Viewを生成
        view.setBounds(0,0,1280,720);		    //画面の大きさは1280×720で固定
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
    }
}
