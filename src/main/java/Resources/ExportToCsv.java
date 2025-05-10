package Resources;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportToCsv {

    public static void exportToCsvFile(String filename, List<String[]> rows) {
	try (FileWriter writer = new FileWriter(filename)) {
	    for (String[] row : rows) {
		writer.append(String.join(",", row));
		writer.append("\n");
	    }
	    System.out.println("CSV file exported successfully: " + filename);
	} catch (IOException e) {
	    System.err.println("Error writing CSV: " + e.getMessage());
	}
    }

}
