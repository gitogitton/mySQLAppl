package com.example.mysqlappl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect {

    public static Connection getConnection(){
        Connection con = null; // 初期化

//        //win10
//        final String url = "jdbc:mysql://192.168.11.10:3306/uketuke_dev?verifyServerCertificate=false&useSSL=true"; /*証明書チェックしない／SSL使用*/
//        final String user = "root";
//        final String password = "root";

        //CentOS
        final String url = "jdbc:mysql://192.168.11.4:3306/uketuke_dev"; //centos7への接続
	    final String user = "root";
	  	final String password = "1234567890";

        try{
        	//JDBCドライバクラスをロード（？）
//            Class.forName("com.mysql.cj.jdbc.Driver"); //mysql-connector-java-8.0.20.jar では Class mysqltype がない！ とExceptionが発生する
            Class.forName("com.mysql.jdbc.Driver"); //mysql-connector-java-5.1.30.jar では正常動作。しかし、このドライバは非推奨なんだが・・・・

            //ＤＢ接続のプロパティー設定
            Properties info = new Properties();
            info.setProperty("useUnicode", "true"); //UNICODE（UTF-8, UTF-16, UTF-32）を使う
            info.setProperty("useJDBCCompliantTimezoneShift", "true"); //
            info.setProperty("useLegacyDatetimeCode", "false"); //レガシーの日時コードを使用しない
            info.setProperty("serverTimezone", "UTC"); //UTC :協定世界時
            info.setProperty("user", user);
            info.setProperty("password", password);
            //ＤＢに接続
            con = DriverManager.getConnection(url, info);
//            con = DriverManager.getConnection(url, user, password);

System.out.println("DB接続成功!!!");

            return con;

        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
            throw new IllegalMonitorStateException(); // クラスがなかった時の例外処理
        } // SQLでエラーが起きた時の例外処理

    }

}
