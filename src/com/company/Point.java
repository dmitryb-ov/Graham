package com.company;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Point o) {
        if(this.y < o.y) return -1;
        if(this.y > o.y) return 1;
        if(this.x < o.x) return -1;
        if(this.x > o.x) return 1;
        return 0;
    }

    public Comparator<Point> polarApexSort(){
        return new PolarApex();
    }

    //скалярное произведение двух векторов, чем меньше значение, тем больше угол
    public static int ccw(Point a, Point b, Point c){
        int ar = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if(ar < 0) return -1;
        else if(ar > 0) return 1;
        else return 0;
    }

    private class PolarApex implements Comparator<Point>{

        @Override
        public int compare(Point o1, Point o2) {
            int dx1 = o1.x-x;
            int dy1 = o1.y-y;
            int dx2 = o2.x-x;
            int dy2 = o2.y-y;

            if(dy1 >= 0 && dy2 < 0) return -1;
            else if(dy2 >= 0 && dy1 < 0) return 1;
            else if(dy1 == 0 && dy2 == 0){
                if(dx1 >= 0 && dx2 <0) return -1;
                else if(dx2 >= 0 && dx1 < 0) return 1;
                else return 0;
            }
            else return -ccw(Point.this, o1, o2);
        }
    }
}
