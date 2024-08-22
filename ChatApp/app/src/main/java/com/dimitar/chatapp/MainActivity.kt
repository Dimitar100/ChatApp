package com.dimitar.chatapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimitar.chatapp.chat.ChatSocketServiceImpl
import com.dimitar.chatapp.databinding.ActivityMainBinding
import com.dimitar.chatapp.di.AppModule
import com.dimitar.chatapp.ui.chat.ChatFragment
import com.dimitar.chatapp.ui.home.ChatAdapter
import com.dimitar.chatapp.ui.home.HomeFragment
import com.dimitar.chatapp.ui.home.HomeViewModel
import com.dimitar.chatapp.util.CurrentChat
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //jwt = getIntent().getExtras()!!.getString("JWT").toString();

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_chat
            )
        )
      //  setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        username = intent.extras!!.getString("username")!!

        lifecycleScope.launch {
            CurrentChat.chatSocketService = ChatSocketServiceImpl(AppModule.provideHttpClient())
            (CurrentChat.chatSocketService as ChatSocketServiceImpl).initSession(username)

            CurrentChat.chatSocketService?.observeMessages()!!.collect {
                Log.d("INCOMING_FRAGMENT_1", it.content)
                Log.d("CHECK", getCurrentDestinationLabel()!!)
                if(getCurrentDestinationLabel().equals("ChatFragment")){
                    ChatFragment.viewModel.getMessages()
                }else{
                    HomeFragment.viewModel.getAllChats()
                }
            }

        }
    }

    fun getCurrentDestinationLabel(): String? {
        return navController.currentDestination?.label?.toString()
    }


}