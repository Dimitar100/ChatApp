package com.dimitar.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Connect to server
        val t = Thread {
            try {
                val serverAddress = "10.0.2.2" // Replace with your server's IP address
                val serverPort = 6789 // Replace with your server's port
                val socket = Socket(serverAddress, serverPort)
                val outputStream = DataOutputStream(socket.getOutputStream())

                // Send data to the server
                val messageToSend = "Hello, server!"
                outputStream.writeUTF(messageToSend)
                outputStream.flush()

                // Receive data from the server
                val inputStream = DataInputStream(socket.getInputStream())
                val receivedMessage = inputStream.readUTF()
                println("Received from server: $receivedMessage")

                // Close the socket
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        t.start()
    }

}