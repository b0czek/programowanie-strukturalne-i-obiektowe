import CpuUsage.CpuUsageDisplay;
import CpuUsage.CpuUsageLogger;
import CpuUsage.CpuUsageMonitor;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        CpuUsageMonitor monitor = new CpuUsageMonitor();
        CpuUsageLogger logger = new CpuUsageLogger(monitor, "log.txt");

        SwingUtilities.invokeLater(() -> new CpuUsageDisplay(monitor));

    }
}