package ScrapData;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CsvScrap {
    private final List<String[]> cells;

    public CsvScrap(File file) throws IOException {
        cells = new ArrayList<>();
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                cells.add(line.split(","));
            }
        }
    }


    public CsvScrap set(int row, int col, String value) {
        String []columns = (cells.get(row-1));
        columns[col-1] = value;
        return this;
    }

    public void save(File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            for (String[] row : cells) {
                for (String cell : row) {
                    if (!Objects.equals(cell, row[0])) {
                        out.print(",");
                    }
                    out.print(cell);
                }
                out.println();
            }
        }
    }
}