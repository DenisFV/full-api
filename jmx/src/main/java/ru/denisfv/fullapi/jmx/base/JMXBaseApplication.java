package ru.denisfv.fullapi.jmx.base;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class JMXBaseApplication {

    public static void main(String[] args) throws Exception {
        JMXBaseController controller = new JMXBaseController();

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(controller, new ObjectName("JMXSetting", "name", "JMXController"));

        while (true) {
            Thread.sleep(1000);
            System.out.println(controller.isEnabled());
        }
    }
}
