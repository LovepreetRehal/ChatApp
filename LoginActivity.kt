package com.example.chatapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth= FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            var email = binding.etEmail.text.toString()
            var password = binding.etPassword.text.toString()

            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext, "email and password is required", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        if (it.isSuccessful){
                            var intent = Intent(this, UserActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(applicationContext, "email or password invaild", Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }

            binding.btnSignUp.setOnClickListener{
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

    }
}