package CpuUsage;

import javax.swing.*;
import java.util.Dictionary;

public class CpuUsageDisplay {
    private JFrame frame;
    private JLabel usageLabel;
    private JSlider slider;
    private CpuUsageMonitor cpuUsageMonitor;

    public CpuUsageDisplay(CpuUsageMonitor monitor) {
        this.cpuUsageMonitor = monitor;
        cpuUsageMonitor.addObserver(this::updateUsage);

        this.frame = new JFrame("zuzycie procesora");

        usageLabel = new JLabel(createUsageString(cpuUsageMonitor.getCurrentCpuUsage()));
        frame.getContentPane().add(usageLabel);

        slider = new JSlider(JSlider.VERTICAL, 0, 100, (int) cpuUsageMonitor.getCurrentCpuUsage());


        slider.setEnabled(false);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        frame.getContentPane().add(slider);


        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void updateUsage(long cpuUsage) {
        SwingUtilities.invokeLater(() -> {
            usageLabel.setText(createUsageString(cpuUsage));
            slider.setValue((int) cpuUsage);
        });
    }

    private String createUsageString(long cpuUsage) {
        return cpuUsage + "%";
    }

}
