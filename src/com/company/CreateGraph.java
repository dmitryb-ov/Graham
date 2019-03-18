package com.company;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * В данном классе создается график, исходя из сгенерированных точек
 * Не нужно сюда обращать большого внимания, класс сделан просто из-за визуального удобства
 */
public class CreateGraph implements ExampleChart<XYChart> {

    public void start() {
        ExampleChart<XYChart> exampleChart = new CreateGraph();
        XYChart chart = exampleChart.getChart();
        new SwingWrapper<XYChart>(chart).displayChart();
    }

    @Override
    public XYChart getChart() {
        LinkedList<Double> x = new LinkedList<>();
        LinkedList<Double> y = new LinkedList<>();
        File file = new File("src\\files\\Apex.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert sc != null;
        while (sc.hasNextInt()) {
            x.add((double) sc.nextInt());
            y.add((double) sc.nextInt());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(10);
        chart.addSeries("Graham Scan", x, y);
        return chart;

    }
}
