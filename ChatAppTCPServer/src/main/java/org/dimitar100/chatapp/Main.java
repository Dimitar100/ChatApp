package org.dimitar100.chatapp;

import java.io.*;
import java.net.*;
import java.sql.*;

class Main
{
    public static void main(String args[])throws Exception
    {
        try
        {
            String line, newLine;
            ServerSocket ss=new ServerSocket(6789);
            System.out.println("Server Started...");
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                while(true)
                {
                    // Waiting for socket connection
                    Socket s=ss.accept();
                    System.out.println("Client connected");

                    // DataInputStream to read data from input stream
                    DataInputStream inp = new DataInputStream(s.getInputStream());
                    // DataOutputStream to write data on outut stream
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());
                    DataInputStream in = new DataInputStream(System.in);

                    //Connection to MySQL DB
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/temp", "root", "dar11na");
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from test");
                    while(rs.next()){
                        System.out.println(rs.getInt(1) + " " + rs.getString(2));
                    }
                    con.close();

                }
            } catch (Exception e) {
                System.out.println(e);
            }
            // Communication Endpoint for the client and server.
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
