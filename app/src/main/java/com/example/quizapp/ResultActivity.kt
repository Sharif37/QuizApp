package com.example.quizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private var userName:TextView?=null
    private var solveQuestion:TextView?=null
    private var btnFinish:Button?=null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        userName=findViewById(R.id.ResultUserName)
        solveQuestion=findViewById(R.id.solveQuestion)
        btnFinish=findViewById(R.id.btnFinishResult)

        userName!!.text=intent.getStringExtra(Constants.USER_NAME)
        val correctAns=intent.getIntExtra(Constants.CORRECT_ANS,0)
        val totalQuestion=intent.getIntExtra(Constants.Total_Question,0)

        solveQuestion!!.text="You have solve $correctAns out of $totalQuestion Questions"

        btnFinish!!.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))

        }

    }
}