public class Circle {
    public Vector2 c;//中心座標
    public int r;//半径
    public Circle(Vector2 vec2, int radius){
        c = vec2;
        r = radius;
    }
    public Circle(){
        this(new Vector2(0,0), 0);
    }

    //円同士の領域が重なっているか
    public boolean hit(Circle hitter){
        if(r == 0 || hitter.r == 0){return false;}
        int d = this.c.getD2(hitter.c);
        int r2 = (int)Math.pow(this.r+hitter.r, 2);
        return d <= r2;
    }
    //デバッグ用
    public String toString(){
        return "x"+ c.x + ",y" + c.y + ",r" + r;
    }
}
