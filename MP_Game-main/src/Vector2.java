public class Vector2{
    public int x;
    public int y;

    public Vector2(){
        this(0,0);
    }
    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Vector2(Vector2 v){//for deep copy
        this.x = v.getX();
        this.y = v.getY();
    }
    public void add(Vector2 v){
        this.x += v.x;
        this.y += v.y;
    }
    public void add(int x, int y){
        this.x += x;
        this.y += y;
    }
    //加算したベクトルを返す
    public Vector2 addv(Vector2 v){
        Vector2 r_v = new Vector2(this.x+v.x, this.y+v.y);
        return r_v;
    }

    public boolean equals(Vector2 other){
        return (this.x == other.x && this.y == other.y);
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    //回転
    public void rotate(double angle){
        double cos,sin,dx,dy;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        dx = x*cos - y*sin;
        dy = x*sin + y*cos;
        x = (int)Math.round(dx);
        y = (int)Math.round(dy);
    }
    //回転したベクトルを返す
    public Vector2 rotateV(double angle){
        double cos,sin,dx,dy;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        dx = x*cos - y*sin;
        dy = x*sin + y*cos;
        return new Vector2((int)Math.round(dx), (int)Math.round(dy));
    }

    public int getD2(Vector2 vec){//距離の二乗を返す
        return (int)Math.pow((vec.x-x),2) + (int)Math.pow((vec.y-y),2);
    }
    public Vector2 cal_idp(Vector2 v, double a){//internal division point
        return new Vector2((int)Math.floor(x*(1-a)+v.x*a),(int)Math.floor(y*(1-a)+v.y*a));
    }
}