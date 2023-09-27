package by.melanholik.weatherdata.models;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Schedule {

    public static void write() throws Exception {

        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        // Show it
        new SwingWrapper<>(chart).displayChart();
    }

    public static void whiteTemCByTime(List<SensorData> sensorsData) {
        double[] xData = new double[sensorsData.size()];
        double[] yData = new double[sensorsData.size()];
        sensorsData.sort(Comparator.comparing(SensorData::getDateWeather));
        double  minValueHour = 0;
        if (sensorsData.size() > 0) {
            minValueHour =  localDateTimeToDouble(sensorsData.get(0).getDateWeather());

        } else {
            return;
        }
        for (int i = 0; i < sensorsData.size(); i++) {
            yData[i] = sensorsData.get(i).getValue();
            xData[i] = localDateTimeToDouble(sensorsData.get(i).getDateWeather()) - minValueHour;
        }
        XYChart chart = QuickChart.getChart("Temp " + sensorsData.get(0).getSensor().getName(), "Time", "Temp", "Temp(time)", xData, yData);
        new SwingWrapper<>(chart).displayChart();
    }


    private static double localDateTimeToDouble(LocalDateTime time){
        return time.getYear() * 8760 + time.getMonthValue() * 744 + time.getDayOfMonth() * 24 + time.getHour();
    }

}
