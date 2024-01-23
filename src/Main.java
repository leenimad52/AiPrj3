import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        try {
            // Load data from CSV file
            Instances data = loadCSV("C:\\Users\\User\\Desktop\\AiPrj3\\AiPrj3\\Height_Weight.csv");

            // Convert height from inches to centimeters and weight from pounds to kilograms
            convertUnits(data);

            // Print main statistics
            printStatistics(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Instances loadCSV(String filePath) throws Exception {
        // Load data from CSV using Weka's CSVLoader
        CSVLoader loader = new CSVLoader();
        loader.setSource(new java.io.File(filePath));
        Instances data = loader.getDataSet();

        return data;
    }

    private static void convertUnits(Instances data) {
        // Convert height from inches to centimeters and weight from pounds to kilograms
        for (int i = 0; i < data.numInstances(); i++) {
            double heightInInches = data.instance(i).value(0);
            double weightInPounds = data.instance(i).value(1);

            double heightInCms = inchesToCentimeters(heightInInches);
            double weightInKgs = poundsToKilograms(weightInPounds);

            // Update the values in the dataset
            data.instance(i).setValue(0, heightInCms);
            data.instance(i).setValue(1, weightInKgs);
        }
    }

    private static void printStatistics(Instances data) {
        // Print main statistics
        System.out.println("Main Statistics of Features:");
        System.out.println("Attribute\tMean\tMedian\tStdDev\tMin\tMax");

        for (int i = 0; i < data.numAttributes(); i++) {
            // Skip non-numeric attributes
            if (!data.attribute(i).isNumeric()) {
                continue;
            }

            double[] values = data.attributeToDoubleArray(i);
            Arrays.sort(values);

            double mean = calculateMean(data, i);
            double median = calculateMedian(values);
            double stdDev = data.attributeStats(i).numericStats.stdDev;
            double min = values[0];
            double max = values[values.length - 1];

            System.out.printf("%s\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\n",
                    data.attribute(i).name(), mean, median, stdDev, min, max);
        }
    }

    private static double calculateMean(Instances data, int attributeIndex) {
        // Check if the attribute is numeric before calculating the mean
        if (data.attribute(attributeIndex).isNumeric()) {
            return data.meanOrMode(attributeIndex);
        } else {
            // If the attribute is not numeric, return NaN or handle accordingly
            return Double.NaN;
        }
    }

    private static double inchesToCentimeters(double inches) {
        return inches * 2.54;
    }

    private static double poundsToKilograms(double pounds) {
        return pounds * 0.453592;
    }

    private static double calculateMedian(double[] values) {
        int middle = values.length / 2;
        if (values.length % 2 == 1) {
            return values[middle];
        } else {
            return (values[middle - 1] + values[middle]) / 2.0;
        }
    }

}
