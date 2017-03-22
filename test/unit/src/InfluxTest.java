/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.influx.addon.InfluxConfig;
import com.influx.addon.InfluxConnection;
import java.util.List;
import org.influxdb.InfluxDB;

/**
 *
 * @author Suhail_Sullad
 */
public class InfluxTest {
    public static void main(String[] args) {
        InfluxConfig icfg = new InfluxConfig("127.0.0.1", 8086, "", "",null);
        InfluxDB idb = InfluxConnection.GetInstance(icfg);
       // System.err.println(idb.ping().getVersion());
 List<String> databases = idb.describeDatabases();
        System.out.println(databases.contains("jmeters"));
              for(String daString:databases)
            {
              System.err.println(daString);
       }
    }
}
