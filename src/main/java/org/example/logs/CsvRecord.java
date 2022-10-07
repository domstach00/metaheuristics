package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CsvRecord {
    public static final String[] FIELDS_ORDER = {"nr_pokolenia", "najlepsza_ocena", "średnia_ocen", "najgorsza_ocena"};

    @CsvBindByPosition(position = 0)
    private int generationNr;

    @CsvBindByPosition(position = 1)
    private double bestScore;

    @CsvBindByPosition(position = 2)
    private double averageScore;

    @CsvBindByPosition(position = 3)
    private double worstScore;

    public static String getHeader() {
        return String.format(
                "%s, %s, %s, %s\n",
                CsvRecord.FIELDS_ORDER[0],
                CsvRecord.FIELDS_ORDER[1],
                CsvRecord.FIELDS_ORDER[2],
                CsvRecord.FIELDS_ORDER[3]
        );
    }
}
