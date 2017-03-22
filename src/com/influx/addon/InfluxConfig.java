/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.influx.addon;

/**
 *
 * @author Suhail_Sullad
 */
public class InfluxConfig {

    private String HOSTNAME = "localhost";
    private Integer PORT = 8086;
    private String USERNAME = "";
    private String PASSWORD = "";
    private String DATABASE = "";

    public InfluxConfig(String HOSTNAME, Integer PORT, String USERNAME, String PASSWORD, String DATABASE) {
        this.HOSTNAME = HOSTNAME;
        this.PORT = PORT;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.DATABASE = DATABASE;
    }

    public String getHOSTNAME() {
        return HOSTNAME;
    }

    public void setHOSTNAME(String HOSTNAME) {
        this.HOSTNAME = HOSTNAME;
    }

    public Integer getPORT() {
        return PORT;
    }

    public void setPORT(Integer PORT) {
        this.PORT = PORT;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getHTTPInfluxURL() {
        return String.format("http://%1$s:%2$d", HOSTNAME, PORT);
    }

    public String getDATABASE() {
        return DATABASE;
    }

    public void setDATABASE(String DATABASE) {
        this.DATABASE = DATABASE;
    }

}
