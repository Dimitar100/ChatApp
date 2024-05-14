package com.chatapp.dimitar.messages

class OnlineUsers {
    companion object {
        private val onlineUsers: HashSet<String> = HashSet()

        fun insertUser(username: String) {
           onlineUsers.add(username)
        }

        fun isUserOnline(username: String): Boolean{
            return onlineUsers.contains(username)
        }

        fun removeUser(username: String){
            onlineUsers.remove(username)
        }
    }
}