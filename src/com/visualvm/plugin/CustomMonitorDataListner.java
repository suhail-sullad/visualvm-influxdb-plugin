/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visualvm.plugin;

import com.influx.addon.InfluxConnection;
import com.sun.tools.visualvm.application.jvm.MonitoredData;
import com.sun.tools.visualvm.application.jvm.MonitoredDataListener;

/**
 *
 * @author Suhail_Sullad
 */
public class CustomMonitorDataListner implements MonitoredDataListener {

    private String application_host;
    public String application_id;
    private final int processorsCount;

    private long prevUpTime = -1;
    private long prevProcessCpuTime = -1;
    private long prevProcessGcTime = -1;

    public CustomMonitorDataListner(String application_host, String application_id,int processorcount) {
        this.application_host = application_host;
        this.application_id = application_id;
        this.processorsCount = processorcount;
        
    }

    public void monitoredDataEvent(MonitoredData md) {
        long [] heapdata = getMemoryValues(md);
        long [] cpudata = getCPUValues(md);
        long [] threaddata = getThreadValues(md);
        long [] classdata = getClassLoadedValues(md);
        InfluxConnection.saveCPUUsage(application_host,application_id,cpudata[0],cpudata[1]);
        InfluxConnection.saveJVMUsage(application_host,application_id, heapdata[0], heapdata[1], heapdata[2], heapdata[3], heapdata[4], heapdata[5]);
        InfluxConnection.saveThreadUsage(application_host,application_id,threaddata[0],threaddata[1],threaddata[2],threaddata[3]);
        InfluxConnection.saveClassUsage(application_host,application_id,classdata[0],classdata[1],classdata[2],classdata[3]);
    }
    long[] getCPUValues(MonitoredData data) {
        long cpuUsage = -1;
        long gcUsage = -1;
         
        long upTime = data.getUpTime() * 1000000;
        
        long processCpuTime = data.getProcessCpuTime() / processorsCount;
        long processGcTime  = data.getCollectionTime() * 1000000 / processorsCount;

        if (prevUpTime != -1) {
            long upTimeDiff = upTime - prevUpTime;

            if (prevProcessCpuTime != -1) {
                long processTimeDiff = processCpuTime - prevProcessCpuTime;
                cpuUsage = upTimeDiff > 0 ? Math.min((long)(1000 * (float)processTimeDiff /
                                                     (float)upTimeDiff), 1000) : 0;
            }

            if (prevProcessGcTime != -1) {
                long processGcTimeDiff = processGcTime - prevProcessGcTime;
                gcUsage = upTimeDiff > 0 ? Math.min((long)(1000 * (float)processGcTimeDiff /
                                                    (float)upTimeDiff), 1000) : 0;
                if (cpuUsage != -1 && cpuUsage < gcUsage) gcUsage = cpuUsage;
            }
        }

        prevUpTime = upTime;
        prevProcessCpuTime = processCpuTime;
        prevProcessGcTime  = processGcTime;

        return new long[] {
            Math.max(cpuUsage, 0),
            Math.max(gcUsage, 0)
        };
    }

    
    long[] getMemoryValues(MonitoredData data) {
        return new long[] {
            Math.max(data.getGenMaxCapacity()[0], 0),
            Math.max(data.getGenCapacity()[0], 0),
            Math.max(data.getGenUsed()[0], 0),
            Math.max(data.getGenMaxCapacity()[1], 0),
            Math.max(data.getGenCapacity()[1], 0),
            Math.max(data.getGenUsed()[1], 0)
        };
    }

    long [] getThreadValues(MonitoredData data){
        return new long []{
            data.getThreadsLive(),
            data.getThreadsLivePeak(),
            data.getThreadsStarted(),
            data.getThreadsDaemon()
         };
    }
    
    long [] getClassLoadedValues(MonitoredData data){
        return new long[] {
            data.getLoadedClasses(),
            data.getUnloadedClasses(),
            data.getSharedLoadedClasses(),
            data.getSharedUnloadedClasses()
        };
    }
}
