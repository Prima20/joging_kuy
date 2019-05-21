package com.papb.prima.jogingkuy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {


    lateinit var username: EditText
    lateinit var password: EditText

    //Firebase properties
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.edtUsernameLogin)
        password = findViewById(R.id.edtPasswordLogin)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //Login button click
        btnLogin.setOnClickListener(View.OnClickListener {
            var fieldisNull = (username.text.toString().equals("") || password.text.toString().equals(""))

            if (!fieldisNull) {
                login(username.text.toString(),password.text.toString())

            } else {
                Toast.makeText(applicationContext, "Entry field !", Toast.LENGTH_SHORT).show()
            }
        })

        //To Register Activity
        tvRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
    }

    fun login(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Auth:", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }

                }
    }

}