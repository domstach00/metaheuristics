package org.example.config;

import lombok.*;
import org.example.support.Utils;

@Data
@RequiredArgsConstructor
public class ConfigLog {

    private final static String logPathTemplate = "src/main/resources/logs/%s/%s/";
    private final static String inputPathTemplate = "src/main/resources/input/";

    private final String uniDate = Utils.getCurrentFormatDate();

    private final String currentFileName;
    private String logsPath;

    public void formatLogPath(String algName, String operations) {
        this.logsPath = String.format(logPathTemplate,
                algName.toLowerCase(),
                operations
        );
    }

    public String getInputPath() {
        return inputPathTemplate + getCurrentFileName();
    }
}
