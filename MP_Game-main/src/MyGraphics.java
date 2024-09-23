import java.awt.*;

public class MyGraphics{
    private Graphics2D g2;	//拡張するGraphics2Dクラス
    private float[] hsb;	//色彩・彩度・明度を入れる配列

    //配列を生成する
    public MyGraphics(){
        hsb = new float[3];
    }
	//拡張するGraphics2Dクラスを変数に持たせる
    public void link(Graphics2D g2){
        this.g2 = g2;
    }
	//色の明度を変更する
    public Color changebright(Color c, float bright){
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
        if(bright < 0){bright = 0;}
        hsb[2] = bright;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }
	//色の彩度を変更する
    private Color changesaturation(Color c, float saturation){
        Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), hsb);
        if(saturation < 0){saturation = 0;}
        hsb[1] = saturation;
        Color c2 = new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
        return new Color(c2.getRed(),c2.getGreen(),c2.getBlue(),c.getAlpha());
    }
	//v中心で,横幅・縦幅を指定して,楕円を描く
    public void mydrawOval(Vector2 v, int width, int height){
        g2.drawOval(v.x-width/2, v.y-height/2, width, height);
    }
	//v中心で,横幅・縦幅を指定して,楕円を塗りつぶす
    public void myfillOval(Vector2 v, int width, int height){
        g2.fillOval(v.x-width/2, v.y-height/2, width, height);
    }
	//v1からv2へ線を描く
    public void mydrawLine(Vector2 v1, Vector2 v2){
        g2.drawLine(v1.x, v1.y, v2.x, v2.y);
    }
	//端点・長さ・水平方向から時計回りの角度を指定して,線を描く
    public void drawLineAnfgle(Vector2 v, int r, double angle){
        g2.drawLine(v.x, v.y, v.x+(int)((double)r*Math.cos(angle)), v.y-(int)((double)r*Math.sin(angle)));
    }
    //最初の半径と線の太さと色を指定して,v中心で彩度と半径が違う複数の円を描く
    public void fillOval_G(Vector2 vec, int r, int t, Color col){
        for(int i = r-t/2; i > 0; i -= t*2){
            g2.setColor(changesaturation(col, (float)i/(float)r));
            this.mydrawOval(vec, i*2, i*2);
        }
    }
}
