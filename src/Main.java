import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Main {

    public static void main(String[] args) {
        String csvFile = "D:\\AiPrj3\\Height_Weight.csv";

        try {
            CSVLoader loader = new CSVLoader();
            loader.setSource(new java.io.File(csvFile));
            loader.setNumericAttributes("1,2");
            Instances data = loader.getDataSet();

            for (int i = 0; i < data.numAttributes(); i++) {
                System.out.print(data.attribute(i).name() + "\t");
            }
            System.out.println();

            for (int i = 0; i < data.numInstances(); i++) {
                for (int j = 0; j < data.numAttributes(); j++) {
                    if (j == 0) {
                        System.out.print(data.attribute(j).value((int) data.instance(i).value(j)) + "\t");
                    } else {
                        System.out.print(data.instance(i).value(j) + "\t");
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
