package com.papb.prima.jogingkuy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()



        txtHelloWorld.setOnClickListener(View.OnClickListener {
            auth.signOut()
            finish()
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        })

    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)

        val user = FirebaseAuth.getInstance().currentUser
        if(user != null){
            txtHelloWorld.text = user.uid.toString()

        }
    }


}
