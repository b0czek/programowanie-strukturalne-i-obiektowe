package CpuUsage;

import CpuUsage.CpuUsageMonitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

public class CpuUsageLogger {
    private CpuUsageMonitor monitor;
    private String filename;

    public CpuUsageLogger(CpuUsageMonitor monitor, String filename) {
        this.monitor = monitor;
        this.filename = filename;
        this.logUsage(monitor.getCurrentCpuUsage());
        monitor.addObserver(this::logUsage);
    }

    private void logUsage(long cpuLoad) {
        try (FileWriter fw = new FileWriter(filename, true)){
            LocalTime now = LocalTime.now();
            fw.append(String.format("[%02d:%02d:%02d]: %d%s\n", now.getHour(), now.getMinute(), now.getSecond(), cpuLoad, "%"));
        } catch (IOException e) {
            System.out.println("nie mozna zapisac stanu do pliku");
        }
    }



}
