package com.company;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Алгоритм тот же, только сделан на LinkedList
 */
public class LinkedGrScan {
    private Stack<Point> shell = new Stack<>();

    public LinkedGrScan(LinkedList<Point> listPoint) {
        int n = listPoint.size();
        LinkedList<Point> listPoints = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            listPoints.add(listPoint.get(i));
        }
        Collections.sort(listPoints);
        Collections.sort(listPoints, listPoints.getFirst().polarApexSort());

        shell.push(listPoints.getFirst());

        int k1;
        for (k1 = 1; k1 < n; k1++)
            if (!listPoints.getFirst().equals(listPoints.get(k1))) break;
        if (k1 == n) return;

        int k2;
        for (k2 = k1 + 1; k2 < n; k2++)
            if (Point.ccw(listPoints.getFirst(), listPoints.get(k1), listPoints.get(k2)) != 0) break;
        shell.push(listPoints.get(k2 - 1));

        for (int i = k2; i < n; i++) {
            Point top = shell.pop();
            while (Point.ccw(shell.peek(), top, listPoints.get(i)) <= 0) {
                top = shell.pop();
            }
            shell.push(top);
            shell.push(listPoints.get(i));
        }

        assert isConvex();
    }

    public Iterable<Point> shell() {
        Stack<Point> s = new Stack<>();
        for (Point p : shell) s.push(p);
        return s;
    }

    private boolean isConvex() {
        int n = shell.size();
        if (n <= 2) return true;

        LinkedList<Point> listPoints = new LinkedList<>();
        int k = 0;
        for (Point p : shell()) {
            listPoints.add(p);
        }

        for (int i = 0; i < n; i++) {
            if (Point.ccw(listPoints.get(i), listPoints.get((i + 1) % n), listPoints.get((i + 2) % n)) <= 0) {
                return false;
            }
        }
        return true;
    }
}
