package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart:Button =findViewById(R.id.BtnStart)
        val name:EditText=findViewById(R.id.UserName)
        btnStart.setOnClickListener{
           if(name.text.isEmpty()){
               Toast.makeText(this,"Enter Name Please",Toast.LENGTH_LONG).show()
           }else
           {
               val intent= Intent(this,QuizQuestionActivity::class.java)
               startActivity(intent)
               finish()
           }


        }
    }
}