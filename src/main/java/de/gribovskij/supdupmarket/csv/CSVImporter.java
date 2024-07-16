package de.gribovskij.supdupmarket.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Utility class for importing products from a CSV file.
 *
 * @author Eugen Gribovskij
 */
public class CSVImporter {

    /**
     * Reads products from a CSV file and returns a list of CSVProduct
     * instances.
     *
     * @param filePath the path to the CSV file
     * @return a list of CSVProduct instances read from the CSV file
     */
    public static List<CSVProduct> importProductsFromCSV(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<CSVProduct> csvToBean = new CsvToBeanBuilder<CSVProduct>(reader)
                    .withType(CSVProduct.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
