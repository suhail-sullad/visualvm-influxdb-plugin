/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visualvm.plugin;

import com.sun.tools.visualvm.application.Application;
import com.sun.tools.visualvm.application.jvm.Jvm;
import com.sun.tools.visualvm.application.jvm.JvmFactory;
import com.sun.tools.visualvm.application.jvm.MonitoredDataListener;
import com.sun.tools.visualvm.core.datasource.DataSource;
import com.sun.tools.visualvm.tools.jmx.JmxModel;
import com.sun.tools.visualvm.tools.jmx.JmxModel.ConnectionState;
import com.sun.tools.visualvm.tools.jmx.JmxModelFactory;
import com.sun.tools.visualvm.tools.jmx.JvmMXBeans;
import com.sun.tools.visualvm.tools.jmx.JvmMXBeansFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author Suhail_Sullad
 */
public class InfluxModel {

    private DataSource source;
    private MonitoredDataListener mdlnr;

    static InfluxModel create(Application application) {
        InfluxModel model = new InfluxModel();
        model.source = application;
        int pCount = 1;
        JmxModel jmxModel = JmxModelFactory.getJmxModelFor(application);
        if (jmxModel != null && jmxModel.getConnectionState() == ConnectionState.CONNECTED) {
            JvmMXBeans mxbeans = JvmMXBeansFactory.getJvmMXBeans(jmxModel);
            if (mxbeans != null) {
                OperatingSystemMXBean osbean = mxbeans.getOperatingSystemMXBean();
                if (osbean != null) {
                    pCount = osbean.getAvailableProcessors();
                }
            }
        }       
        model.mdlnr = new CustomMonitorDataListner(application.getHost().getInetAddress().getHostAddress(), application.getId(), pCount);
        return model;
    }

    public void attachListner() {
        Jvm jv = JvmFactory.getJVMFor((Application) source);
        jv.addMonitoredDataListener(mdlnr);
    }

    public void dettachListner() {
        Jvm jv = JvmFactory.getJVMFor((Application) source);
        try {
            jv.removeMonitoredDataListener(mdlnr);
        } catch (Exception e) {

        }
    }

    public void initialize() {
        // Jvm jv = JvmFactory.getJVMFor((Application)source);
        /*jv.addMonitoredDataListener(new MonitoredDataListener() {
                public void monitoredDataEvent(MonitoredData md) {
                   System.out.println(md.getGenUsed()[0]);
                }
            });*/
    }

    private InfluxModel() {
    }

}
