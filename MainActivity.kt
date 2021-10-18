package com.example.chatapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.chatapplication.R
import com.example.chatapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference:DatabaseReference

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this, R.layout.activity_main)

        auth= FirebaseAuth.getInstance()



            binding.btnSignUp.setOnClickListener{
            val userName= binding.etName.text.toString()
            val email= binding.etEmail.text.toString()
            val password= binding.etPassword.text.toString()
            val confirmPassword= binding.etConfirmPassword.text.toString()

            if(TextUtils.isEmpty(userName)){
                Toast.makeText(applicationContext, "userName is required", Toast.LENGTH_SHORT).show()
            }

            if(TextUtils.isEmpty(email)){
                Toast.makeText(applicationContext, "email is required ", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(applicationContext, "password is required ", Toast.LENGTH_SHORT).show()
            }
            if(TextUtils.isEmpty(confirmPassword)){
                Toast.makeText(applicationContext, "confirm Password is required ", Toast.LENGTH_SHORT).show()
            }

            if(password != confirmPassword){
                Toast.makeText(applicationContext,"password not mach ",Toast.LENGTH_SHORT).show()

            }
                registerUser(userName, email, password)
        }
        binding.btnLogin.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registerUser (userName:String,email:String,password:String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    var user:FirebaseUser? = auth.currentUser
                    var userId:String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance().getReference("user").child(userId)

                    var hashMap:HashMap<String ,String> = HashMap()
                    hashMap.put("userId",userId)
                    hashMap.put("userName",userName)
                    hashMap.put("ProfileImage","")

                    databaseReference.setValue(hashMap).addOnCompleteListener(this){
                        if(it.isSuccessful)
                        {
                            val intent = Intent(this@MainActivity, UserActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

    }

}