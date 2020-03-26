package com.example.xardkor

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpGetRequest : AsyncTask<String, Void, String>() {

    companion object {
        const val REQUEST_METHOD = "GET"
        const val READ_TIMEOUT = 15000
        const val CONNECTION_TIMEOUT = 15000
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String {
        var result: String = "weather"
        var inputLine: String?

        try {
            val url = URL(params[0])
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = REQUEST_METHOD
            connection.readTimeout = READ_TIMEOUT
            connection.connectTimeout = CONNECTION_TIMEOUT

            val inputStreamReader = InputStreamReader(connection.inputStream)
            val reader = BufferedReader(inputStreamReader)
            val strBuilder = StringBuilder()

            inputLine = reader.readLine()
            while(inputLine != null){
                strBuilder.append(inputLine)
                inputLine = reader.readLine()
            }

            reader.close()
            inputStreamReader.close()

            result = strBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return result
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}