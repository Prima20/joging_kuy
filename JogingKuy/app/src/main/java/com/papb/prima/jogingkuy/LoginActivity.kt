package com.papb.prima.jogingkuy

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import com.papb.prima.jogingkuy.R.id.textView
import com.papb.prima.jogingkuy.R.id.textView2
import android.os.AsyncTask.execute
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Debug
import android.widget.TextView
import android.widget.Toast
import com.papb.prima.jogingkuy.R.id.textView2
import com.papb.prima.jogingkuy.R.id.textView
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONArray
import org.json.JSONObject




class LoginActivity: AppCompatActivity(){


    lateinit var username: EditText
    lateinit var password: EditText

    //Webservice for login
    private var stringUrl = "https://api.myjson.com/bins/196w72"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.edtUsernameLogin)
        password = findViewById(R.id.edtPasswordLogin)


        btnLogin.setOnClickListener(View.OnClickListener {

            checkLogin(stringUrl);
            Log.d("user","Login cok")
        })

        tvRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        })


    }

    fun checkLogin(stringUrl:String){
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            AsyntaxActivity(username, password).execute(stringUrl)
        } else {
            Toast.makeText(applicationContext,"Tidak ada koneksi",Toast.LENGTH_SHORT)
        }
    }

    inner class AsyntaxActivity(text1:View,text2:View): AsyncTask<String, Void, String>() {

        val username:EditText = text1 as EditText
        val password:EditText = text2 as EditText
        private val debug = "HttpExample"

        override fun doInBackground(vararg urls: String): String? {
            try {
                return downloadURL(urls[0])
            }catch (e: IOException){
                return "Unable to retrieve webpage"
            }
        }

        private fun downloadURL(myUrl: String):String{
            var iS: InputStream?= null

            val len = 10000

            try {
                var url= URL(myUrl)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.readTimeout = 1000
                conn.connectTimeout = 15000
                conn.requestMethod = "GET"
                conn.doInput = true

                conn.connect()
                var response = conn.responseCode
                Log.d("Res","Response code : " + response)
                iS = conn.inputStream

                var contentAsString: String = readIt(iS, len)
                return contentAsString

            }finally {
                if(iS!=null){
                    iS.close()
                }
            }
        }

        fun readIt(stream:InputStream,len:Int):String{
            var reader: Reader
            reader = InputStreamReader(stream, "UTF-8")
            var buffer = CharArray(len)
            reader.read(buffer)
            return String(buffer)

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            try {
                val jObj = JSONObject(result)
                val userData:JSONArray = jObj.getJSONArray("user")

                var usr = username.text.toString()
                var pass = password.text.toString()

                for(i in 0..userData.length()-1){
                    if(userData.getJSONObject(i).getString("username").equals(usr)
                        && userData.getJSONObject(i).getString("password").equals(pass)){
                        Log.d("Login","Sukses");

                        val intent = Intent(applicationContext,MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Log.d("Login","Gagal")
                    }
                }


            } catch (e: JSONException) {
                e.printStackTrace()
                Log.d("err","occured")
            }

        }
    }
}