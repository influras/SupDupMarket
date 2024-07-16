package de.gribovskij.supdupmarket.csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.FileWriter;
import java.util.List;

/**
 * Utility class for exporting products to a CSV file.
 *
 * @author Eugen Gribovskij
 */
public class CSVExporter {

    /**
     * Writes a list of products to a CSV file.
     * <p>
     * This method takes a file path and a list of products, then writes the
     * product data to the specified CSV file.
     * </p>
     *
     * @param filePath the path to the CSV file where the products will be
     * written
     * @param products the list of products to write to the CSV file
     */
    public static void writeProductsToCSV(String filePath, List<CSVProduct> products) {
        try (FileWriter writer = new FileWriter(filePath)) {
            StatefulBeanToCsv<CSVProduct> beanToCsv = new StatefulBeanToCsvBuilder<CSVProduct>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsv.write(products);
        } catch (Exception e) {
            System.err.println("Error exporting CSV file: " + e.getMessage());
        }
    }
}
