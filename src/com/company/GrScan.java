package com.company;

import java.util.Arrays;
import java.util.Stack;

public class GrScan {

    private Stack<Point> shell = new Stack<>(); //создается список, который будет хранить точки, образующие выпуклую оболочку

    private int iterCount = 0; // счетчик количества итераций

    public GrScan(Point[] pts) {
        int n = pts.length; //копия числа вершин
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = pts[i];
        }
        Arrays.sort(points);
        //сортирует по полярному углу
        Arrays.sort(points, 1, n, points[0].polarApexSort());

        //после сортировки берется стартовая точка, на основе которой будут располагаться все остальные точки
        shell.push(points[0]);       // p[0] стартовая точка (Шаг 1)

        //Шаг 2
        // найдем индекс k1 первой точки, не равной points[0]
        int k1;
        for (k1 = 1; k1 < n; k1++) {
            iterCount++;
            if (!points[0].equals(points[k1])) {
                break;
            }
        }
        if (k1 == n) return;

        // найти индекс k2 первой точки, не коллинеарной с points[0] и points[k1]
        int k2;
        for (k2 = k1 + 1; k2 < n; k2++) {
            iterCount++;
            if (Point.ccw(points[0], points[k1], points[k2]) != 0) {
                break;
            }
        }
        shell.push(points[k2 - 1]);    // points[k2-1] вторая точка

        //Шаг 3
        // алгоритм Грэхема
        for (int i = k2; i < n; i++) {
            Point top = shell.pop();
            while (Point.ccw(shell.peek(), top, points[i]) <= 0) {
                iterCount++;
                top = shell.pop();
            }
            shell.push(top);
            shell.push(points[i]);
        }

        assert isConvex();//Assert — это специальная конструкция, позволяющая проверять предположения о значениях
                        // произвольных данных в произвольном месте программы
    }

    /**
     *Выводит значение точек, которые образуют выпуклую оболочку
     */
    public Iterable<Point> shell() {
        Stack<Point> s = new Stack<Point>();
        for (Point p : shell) s.push(p);
        return s;
    }

    //Вывод кол-ва итераций
    public int printCount() {
        return iterCount;
    }

    //проверить, является ли граница многоугольника строго выпуклой
    private boolean isConvex() {
        int n = shell.size();
        if (n <= 2) return true;

        Point[] points = new Point[n];
        int k = 0;
        for (Point p : shell()) {
            points[k++] = p;
        }

        for (int i = 0; i < n; i++) {
            if (Point.ccw(points[i], points[(i + 1) % n], points[(i + 2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }
}
