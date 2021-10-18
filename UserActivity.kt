package com.example.chatapplication.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapplication.R
import com.example.chatapplication.adapter.UserAdapter
import com.example.chatapplication.databinding.ActivityUserBinding
import com.example.chatapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UserActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserBinding

    var userList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        getUserList()
    }
        fun getUserList() {
            var firebase: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
            var databaseReference: DatabaseReference =
                FirebaseDatabase.getInstance().getReference("Users")

            databaseReference.addValueEventListener(object : ValueEventListener {


                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()

                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    userList.clear()

                    for (dataSnapShot: DataSnapshot in snapshot.children) {
                        val user = dataSnapShot.getValue(User::class.java)

                        if (user!!.userId.equals(firebase.uid)) {
                            userList.add(user)

                        }
                    }


                    val userAdapter = UserAdapter(
                        this@UserActivity,
                        userList
                    )

                    binding.recyclerView.adapter = userAdapter


                }

            })
        }
    }
