package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class StartAlgo {
    /**
     * При каждом запуске генерируется случайный файл с набором, задаваемый в переменной ApexCount
     * И размером значений, задаваемый в переменной bound
     */
    public void start() throws IOException {
        Random random = new Random();
        final int bound = 100; //change
        final String FILE = "src\\files\\Apex.txt";
        final int ApexCount = 1000; //this value may be changed

        //генерируется файл Apex
        File file = new File(FILE);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(FILE);
        //Заводится Scanner, point, LinkedList
        Scanner sc = new Scanner(file);
        Point[] point = new Point[ApexCount];
        LinkedList<Point> listPoint = new LinkedList<>();
        //записываются в файл случайные значения от 0 до bound
        for (int i = 0; i < ApexCount; i++) {
            int x = random.nextInt(bound);
            int y = random.nextInt(bound);
            fileWriter.write(x + " " + y + "\n");
        }
        fileWriter.close();
        /**
         * При каждой итерации массив point хранит в себе значение x и y, взятые из файла Apex
         */
        int i = 0;
        while (sc.hasNextInt()) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            point[i] = new Point(x, y);
            listPoint.add(new Point(x, y));
            i++;
        }


        System.out.println("=====================================");

        /**
         * Засекается время до работы / после работы алгоритма
         */
        long startNanoArray = System.nanoTime();
        long startMilliArray = System.currentTimeMillis();
        System.out.println("Nano time before start array algo: " + startNanoArray);
        System.out.println("Milli time before start array algo: " + startMilliArray);

        //Передаём в GrScan массив point`ов (иначе - координаты точек)
        GrScan grScan = new GrScan(point);

        long endNanoArray = System.nanoTime();
        long endMilliArray = System.currentTimeMillis();
        System.out.println("Nano time after start array algo: " + endNanoArray);
        System.out.println("Milli time after start array algo " + endMilliArray);

        System.err.printf("Array nano distinction: %d\n", endNanoArray - startNanoArray);
        System.err.printf("Array milli distinction: %d\n", endMilliArray - startMilliArray);

        //Выводятся вершины, которые образуют выпуклую оболочку array
        //В конце вершины linkedlist должны быть равны вершинам массива
        System.out.println("Array");
        for (Point p : grScan.shell()) {
            System.out.println("(" + p.getX() + "," + p.getY() + ")");
        }

        System.out.println("====================================================");

        long startNanoLinkedList = System.nanoTime();
        long startMilliLinkedList = System.currentTimeMillis();
        System.out.println("Nano time before start linked list algo: " + startNanoLinkedList);
        System.out.println("Milli time before start linked list algo: " + startMilliLinkedList);

        LinkedGrScan linkedGrScan = new LinkedGrScan(listPoint);

        long endNanoLinkedList = System.nanoTime();
        long endMilliLinkedList = System.currentTimeMillis();
        System.out.println("Nano time after start linked list algo: " + endNanoLinkedList);
        System.out.println("Milli time after start linked list algo: " + endMilliLinkedList);

        System.err.printf("Linked List nano distinction: %d\n", endNanoLinkedList - startNanoLinkedList);
        System.err.printf("Linked List milli distinction: %d\n", endMilliLinkedList - startMilliLinkedList);

        //Выводятся вершины, которые образуют выпуклую оболочку в linked list
        System.out.println("LinkedList");
        for (Point p : linkedGrScan.shell()) {
            System.out.println("("+p.getX()+","+p.getY()+")");
        }

        /**
         * Постройка графика
         */
//        CreateGraph createGraph = new CreateGraph();
//        createGraph.start();
    }
}
