package com.papb.prima.jogingkuy

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        buttonRegister.setOnClickListener(View.OnClickListener {
//            if(checkPassword()){
                val intent = Intent(applicationContext, RegisterDataActivity::class.java)
                startActivity(intent)
//            }else{
//                Toast.makeText(applicationContext, "Cannot regsiter", Toast.LENGTH_SHORT).show()
//            }
        })

    }

    fun regsiter(){
        var username = edtUsernameRegister.text.toString()
        var email = edtEmailRegister.text.toString()
        var password = edtPasswordRegister.text.toString()
    }

    fun checkPassword(): Boolean{
        var password = edtPasswordRegister.text.toString()
        var repassword = edtConfirmPasswordRegister.text.toString()

        if(password.equals(repassword) && (!password.equals(null) && !repassword.equals(null))){
            return true;
        }else{
            Toast.makeText(applicationContext, "Please enter corect pasword", Toast.LENGTH_SHORT).show()
            return false;
        }
    }
}