package com.papb.prima.jogingkuy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.papb.prima.jogingkuy.model.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: AppCompatActivity(){


    //Firebase properties
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("USERS")

        buttonRegister.setOnClickListener(View.OnClickListener {

            var fieldIsNull = edtUsernameRegister.text.toString().equals("") || edtEmailRegister.text.toString().equals("")
            || edtPasswordRegister.text.toString().equals("") || edtConfirmPasswordRegister.text.toString().equals("")

            if(!fieldIsNull){
                var username = edtUsernameRegister.text.toString()
                var email = edtEmailRegister.text.toString()
                var password = edtPasswordRegister.text.toString()

                regsiterUser(username, email, password)

            }else{
                Toast.makeText(applicationContext, "Entry field !", Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun addDataUser(userId: String, username: String, email: String, password: String){
        val user = User(username,email,password,"",0,0,0,"", "")

        database.child(userId).setValue(user).addOnCompleteListener {
            task ->
            if (task.isSuccessful) {
                Log.d("Ref:","Sukses")
            } else {
                Log.d("Ref:",task.exception.toString())
            }
        }
    }

    fun regsiterUser(username: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    task ->
                    if (task.isSuccessful) {
                        Log.d("Auth:","Sukses")
                        //Move activity
                        val userId = auth.currentUser?.uid.toString()
                        addDataUser(userId,username, email, password)
                        startActivity(Intent(applicationContext,RegisterDataActivity::class.java))
                    } else {
                        Log.d("Auth:",task.exception.toString())
                    }
                }
    }
}