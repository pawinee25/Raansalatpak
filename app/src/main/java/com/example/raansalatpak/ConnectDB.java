package com.example.raansalatpak;

import com.adedom.library.Dru;

import java.sql.Connection;

public class ConnectDB {
    public static String BASE_URL = "172.20.10.8";
   // 172.16.75.125 192.168.1.13
    public static String BASE_IMAGE = "http://" + BASE_URL + "/ics/images/";

    public static Connection getConnection() {
        return Dru.connection(BASE_URL, "pawinee", "123456", "healthy");
    }

}
