package com.example.raansalatpak;

import com.adedom.library.Dru;

import java.sql.Connection;

public class ConnectDB {
    public static Connection getConnection() {
        return Dru.connection("172.20.10.6","pawinee","123456","healthy");
    }

}
