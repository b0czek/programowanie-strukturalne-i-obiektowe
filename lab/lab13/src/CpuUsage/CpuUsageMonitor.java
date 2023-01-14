package CpuUsage;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CpuUsageMonitor {
    private ArrayList<CpuUsageObserver> observers = new ArrayList<>();
    private OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();

    private long currentCpuUsage = 0;

    public CpuUsageMonitor() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long usage =  Math.round(operatingSystemMXBean.getCpuLoad() * 100); // %
                if(usage != currentCpuUsage) {
                    currentCpuUsage = usage;
                    notifyObservers();
                }

            }
        }, 0 ,1000);
    }


    public void addObserver(CpuUsageObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(CpuUsageObserver observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        for(CpuUsageObserver observer : observers) {
            observer.update(currentCpuUsage);
        }
    }

    public long getCurrentCpuUsage() {
        return currentCpuUsage;
    }
}
