package org.example.logs;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.support.Utils;

@Data
@AllArgsConstructor
@Builder
public class CsvRecord implements ICsvRecord {

    public static final String[] FIELDS_ORDER = {"nr_pokolenia", "najlepsza_ocena", "średnia_ocen", "najgorsza_ocena"};

    @CsvBindByPosition(position = 0)
    private int generationNr;

    @CsvBindByPosition(position = 1)
    private String bestScore;

    @CsvBindByPosition(position = 2)
    private String averageScore;

    @CsvBindByPosition(position = 3)
    private String worstScore;

    @Override
    public String getHeader() {
        return String.format(
                "%s; %s; %s; %s\n",
                CsvRecord.FIELDS_ORDER[0],
                CsvRecord.FIELDS_ORDER[1],
                CsvRecord.FIELDS_ORDER[2],
                CsvRecord.FIELDS_ORDER[3]
        );
    }

    @Override
    public String getLine() {
        return String.format(
                "%d; %s; %s; %s\n",
                generationNr,
                bestScore,
                averageScore,
                worstScore
        );
    }

    @Override
    public String getFileName() {
        return Utils.getInputFileNameNoExtension() + "-logger_" + Utils.getSuggestedConfigEA().getConfigEAFileName() + ".csv";
    }

}
