package org.example.workflow;

import org.example.config.Config;

public interface IWorkFLow {
    Config getConfig();
    String getAlgName();
    String getOperationsToLogDir();
    void start();
}
