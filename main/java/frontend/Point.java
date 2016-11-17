package frontend;

/**
 * Created by kdziegie on 2016-05-22.
 */
public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDistance(Point p){
        return Math.sqrt((p.getX() - this.x)*(p.getX() - this.x)+(p.getY() - this.y)*(p.getY() - this.y));
    }

    public double getDistance(double x, double y){
        return Math.sqrt((x - this.x)*(x - this.x)+(y - this.y)*(y - this.y));
    }
}
