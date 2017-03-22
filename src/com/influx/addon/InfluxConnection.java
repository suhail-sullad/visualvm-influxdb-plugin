/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.influx.addon;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import sun.security.jca.GetInstance;

/**
 *
 * @author Suhail_Sullad
 */
public class InfluxConnection {

    private static InfluxDB influxDB = null;

    private InfluxConnection() {

    }

    private InfluxConnection(InfluxConfig iconf) {
        if (iconf.getUSERNAME().isEmpty() || iconf.getPASSWORD().isEmpty()) {
            influxDB = InfluxDBFactory.connect(iconf.getHTTPInfluxURL());
        } else {
            influxDB = InfluxDBFactory.connect(iconf.getHTTPInfluxURL(), iconf.getUSERNAME(), iconf.getUSERNAME());
        }

        if (iconf.getDATABASE().isEmpty()) {
            createInfluxDatabase("visualVM");
        }
        influxDB.enableBatch(1500, 500, TimeUnit.MILLISECONDS);
    }

    public void createInfluxDatabase(String databaseName) {
        List<String> databases = influxDB.describeDatabases();
        if (!databases.contains(databaseName)) {
            influxDB.createDatabase(databaseName);
        }
    }

    public InfluxDB getInfluxDB() {
        return this.influxDB;
    }

    public synchronized static InfluxDB GetInstance(InfluxConfig iconfg) {
        if (null == influxDB) {
            return (new InfluxConnection(iconfg).getInfluxDB());
        }
        return influxDB;
    }

    public static void saveJVMUsage(String hostIP,String pid, Long Heap_max, Long Heap_size, Long Heap_used, Long Meta_max, Long Meta_size, Long Meta_used) {
        Point point1 = Point.measurement("memory")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("IP", hostIP)
                .tag("Process ID",pid)
                .addField("HEAP_MAX", Heap_max)
                .addField("HEAP_SIZE", Heap_size)
                .addField("HEAP_USED", Heap_used)
                .addField("META_MAX", Meta_max)
                .addField("META_SIZE", Meta_size)
                .addField("META_USED", Meta_used)
                .build();
        influxDB.write("visualVM", "autogen", point1);
    }

    public static void saveCPUUsage(String hostIP,String pid,long cpuusage,long gcusage) {
        Point point1 = Point.measurement("cpu")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("IP", hostIP)
                .tag("Process ID",pid)
                .addField("CPU", cpuusage)
                .addField("GC", gcusage)
                .build();
        influxDB.write("visualVM", "autogen", point1);
    }

public static void saveThreadUsage(String application_host,String pid, long threadlive, long threadlivepeak, long threadstarted, long threaddaemon) {
        Point point1 = Point.measurement("thread")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("IP", application_host)
                .tag("Process ID",pid)
                .addField("THREAD_LIVE", threadlive)
                .addField("THREAD_LIVE_PEAK", threadlivepeak)
                .addField("THREAD_STARTED", threadstarted)
                .addField("THREAD_DAEMON", threaddaemon)
                .build();
        influxDB.write("visualVM", "autogen", point1);
    }

 public static void saveClassUsage(String application_host,String pid, long loadedclass, long unloadedclass, long sharedloaded, long sharedunloaded) {
       Point point1 = Point.measurement("class")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("IP", application_host)
               .tag("Process ID",pid)
                .addField("CLASS_LOADED", loadedclass)
                .addField("CLASS_UNLOADED", unloadedclass)
                .addField("SHARED_CLASS_LOADED", sharedloaded)
                .addField("SHARED_CLASS_UNLOADED", sharedunloaded)
                .build();
        influxDB.write("visualVM", "autogen", point1);
    }

}
