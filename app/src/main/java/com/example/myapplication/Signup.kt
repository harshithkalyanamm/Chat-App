package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var edtname: EditText
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()

        edtname = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignup = findViewById(R.id.btnSignup)


        btnSignup.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name= edtname.text.toString()


            signUp(name,email,password)
        }


    }

    private fun signUp(name:String, email: String, password: String){

        //Logic for Creating an user

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for entering the home page
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)


                    val intent = Intent(this@Signup, MainActivity::class.java)
                    startActivity(intent)



                } else {
                    // If sign in fails, display a message to the user.



                    Toast.makeText(this@Signup,"Some error occurred", Toast.LENGTH_SHORT).show()


                }
            }

    }

    private fun addUserToDatabase(name: String, email:String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }



}