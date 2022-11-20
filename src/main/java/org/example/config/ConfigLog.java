package org.example.config;

import lombok.*;
import org.example.support.Utils;

import java.io.File;
import java.nio.file.Paths;

@Data
@RequiredArgsConstructor
public class ConfigLog {

    private final static String logPathTemplate =  "E:/Projekty/Pwr/metaheuristics/src/main/resources/logs/%s/%s/"; //Paths.get(Utils.getCurrentWorkingDirectory() + "src", "main", "resources", "logs", "%s", "%s") + File.separator;
    private final static String inputPathTemplate = Paths.get(Utils.getCurrentWorkingDirectory(), "src", "main", "resources", "input") + File.separator;

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
