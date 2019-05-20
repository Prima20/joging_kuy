package com.papb.prima.jogingkuy

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import android.net.ConnectivityManager
import android.widget.Toast
import com.papb.prima.jogingkuy.model.DataRepository
import com.papb.prima.jogingkuy.model.User
import com.papb.prima.jogingkuy.model.UserResponse
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {


    lateinit var username: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.edtUsernameLogin)
        password = findViewById(R.id.edtPasswordLogin)


        btnLogin.setOnClickListener(View.OnClickListener {
            login(username.text.toString(), password.text.toString())
        })

        tvRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        })


    }

    //Login function
    fun login(username: String, password: String) {
        val postServices = DataRepository.create()
        postServices.getPosts().enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Error ", ": '${t.message}'")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()

                    userResponse.user?.map {
                        if (username.equals(it.username) && password.equals(it.password)) {
                            Log.d("Login :", "Sukses")
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

        })
    }

}