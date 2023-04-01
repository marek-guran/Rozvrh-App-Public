package com.marekguran.rozvrh

import android.os.AsyncTask
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.io.BufferedReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class FirebaseUpozornenia : FirebaseMessagingService() {
    private val TAG = "FirebaseUpozornenia"

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    fun sendRegistrationToServer(token: String) {
        SendTokenTask().execute(token)
    }

    private inner class SendTokenTask : AsyncTask<String, Void, Boolean>() {
        override fun doInBackground(vararg tokens: String): Boolean {
            // Replace YOUR_SERVER_KEY with your FCM server key
            val apiKey = "Your API key"

            // Set up the HTTP request
            val url = "https://fcm.googleapis.com/v1/projects/moj-rozvrh/registrationTokens/${tokens[0]}"
            val request = URL(url).openConnection() as HttpsURLConnection
            request.requestMethod = "POST"
            request.setRequestProperty("Authorization", "Bearer $apiKey")
            request.setRequestProperty("Content-Type", "application/json")
            request.doOutput = true

            // Set up the request body
            val jsonRequest = JSONObject()
            jsonRequest.put("custom_key", "custom_value")
            val requestBody = jsonRequest.toString().toByteArray(Charsets.UTF_8)

            // Send the request
            request.outputStream.write(requestBody)

            // Read the response
            val responseCode = request.responseCode
            val responseMessage = request.responseMessage
            val inputStream = request.inputStream
            val responseBody = inputStream.bufferedReader().use(BufferedReader::readText)

            // Handle the response
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.d(TAG, "Successfully sent registration token to server: $responseBody")
                return true
            } else {
                Log.e(TAG, "Failed to send registration token to server: $responseMessage ($responseCode)")
                return false
            }
        }

        override fun onPostExecute(result: Boolean) {
            if (!result) {
                // Handle failure
            }
        }
    }
}
