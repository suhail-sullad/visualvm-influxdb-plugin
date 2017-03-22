package com.visualvm.plugin;

import com.influx.addon.InfluxConfig;
import com.influx.addon.InfluxConnection;
import com.sun.tools.visualvm.core.datasource.DataSource;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import org.influxdb.InfluxDB;
import org.netbeans.lib.profiler.ui.components.HTMLTextArea;

class JvmCapabilitiesViewComponent extends JPanel {

    private JTextField influxHost = new JTextField("localhost", 20);
    private JTextField influxPort = new JTextField("8086", 20);
    private JTextField influxDBName = new JTextField("visualVM", 20);
    private JButton startBtn = new JButton("Start");
    private JButton stopBtn = new JButton("Stop");
    private InfluxDB iflxdb = null;

    public JvmCapabilitiesViewComponent(InfluxModel model) {
        initComponents(model);
    }

    private void initComponents(final InfluxModel model) {

        setSize(10, 10);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        add(new JLabel("Influx Host"));
        add(influxHost);
        add(new JLabel("Influx Port"));
        add(influxPort);
        add(new JLabel("Influx DB"));
        influxDBName.setEditable(false);
        add(influxDBName);
        add(startBtn);
        add(stopBtn);
        
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.err.println("Clicked start button");
                InfluxConfig icfg = new InfluxConfig(influxHost.getText(), Integer.parseInt(influxPort.getText()), "", "", "");
                iflxdb = InfluxConnection.GetInstance(icfg);
                if (null != iflxdb) {
                    model.attachListner();
                    startBtn.setEnabled(false);
                }
            }
        });
        stopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.err.println("Clicked stop button");
                if(null!=iflxdb)
                {
                    model.dettachListner();
                    iflxdb.disableBatch();
                    iflxdb.close();
                    startBtn.setEnabled(true);
                }
            }
        });
        
    }
}
