package com.dimitar.chatapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.Login)
        loginButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }
        val registerButton = findViewById<Button>(R.id.Register)
        registerButton.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
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