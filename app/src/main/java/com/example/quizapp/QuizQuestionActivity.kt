package com.example.quizapp
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest


class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition:Int=1
    private var mQuestionList:ArrayList<Question>?=null
    private var mSelectedOptionPosition:Int=0
    private var userName:String?=null
    private var mCorrectAns:Int=0
    private var isSelected:Boolean=true
    
    private var progressBar:ProgressBar?=null
    private var tvProgress:TextView?=null
    private var tvQuestion:TextView?=null
    private var flag:ImageView?=null

    private var optionOne:TextView?=null
    private var optionTwo:TextView?=null
    private var optionThree:TextView?=null
    private var optionFour:TextView?=null
    private var submit:Button?=null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        progressBar=findViewById(R.id.Progress_bar)
        tvProgress=findViewById(R.id.QuizProgress)
        tvQuestion=findViewById(R.id.question)
        flag=findViewById(R.id.flagImage)
        userName=intent.getStringExtra(Constants.USER_NAME)
        optionOne=findViewById(R.id.optionOne)
        optionTwo=findViewById(R.id.optionTwo)
        optionThree=findViewById(R.id.optionThree)
        optionFour=findViewById(R.id.optionFour)
        submit=findViewById(R.id.btn_submit)

        if(isSelected){
            optionOne?.setOnClickListener(this)
            optionTwo?.setOnClickListener(this)
            optionThree?.setOnClickListener(this)
            optionFour?.setOnClickListener(this)
        }
        submit?.setOnClickListener(this)
        mQuestionList = Constants.getQuestions(this)
        setQuestion()


    }


    @SuppressLint("SetTextI18n")
    private fun setQuestion() {
        isSelected=true
        defaultOptionView()
        val question: Question = mQuestionList!![mCurrentPosition - 1]

        flag?.loadImageFromUrl(question.countryInfo.flag)
        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        optionOne?.text = question.optionOne
        optionTwo?.text = question.optionTwo
        optionThree?.text = question.optionThree
        optionFour?.text = question.optionFour

        if(mCurrentPosition == mQuestionList!!.size){
            submit?.text="FINISH"
        }else
        {
            submit?.text="SUBMIT"
        }
    }

    private fun defaultOptionView(){
        val options=ArrayList<TextView>()
        optionOne?.let{
            options.add(0,it)
        }
        optionTwo?.let{
            options.add(1,it)
        }
        optionThree?.let{
            options.add(2,it)
        }
        optionFour?.let{
            options.add(3,it)
        }
        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface= Typeface.DEFAULT
            option.background=ContextCompat.getDrawable(this,R.drawable.rectangle_shape)
        }
    }

    private fun selectedOptionView(tv:TextView,selectedOptionNumber:Int){
        defaultOptionView()
        mSelectedOptionPosition=selectedOptionNumber
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(this,
            R.drawable.selected_option_bg)

    }


    private fun ImageView.loadImageFromUrl(imageUrl: String) {
        val imageLoader = ImageLoader.Builder(this.context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        val imageRequest = ImageRequest.Builder(this.context)
            .crossfade(true)
            .crossfade(300)
            .data(imageUrl)
            .target(
                onStart = {
                    // set up an image loader or whatever you need
                },
                onSuccess = { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    this.setImageBitmap(bitmap)
                 // Toast.makeText(applicationContext,"Image load successfully ", Toast.LENGTH_LONG).show()
                },
                onError = {
                    Toast.makeText(applicationContext,"Image load fail ",Toast.LENGTH_LONG).show()

                }
            )
            .build()

        imageLoader.enqueue(imageRequest)
    }

    private fun answerView(answer:Int,drawableView:Int){
        when(answer){
            1->{
                optionOne?.background=ContextCompat.getDrawable(this,drawableView)
            }
            2->{
                optionTwo?.background=ContextCompat.getDrawable(this,drawableView)
            }
            3->{
                optionThree?.background=ContextCompat.getDrawable(this,drawableView)
            }
            4 ->{
                optionFour?.background=ContextCompat.getDrawable(this,drawableView)
            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.optionOne ->{
                optionOne?.let {
                    if(isSelected)
                    selectedOptionView(it,1)
                }

            }
            R.id.optionTwo ->{
                optionTwo?.let {
                    if(isSelected)
                    selectedOptionView(it,2)
                }

            }
            R.id.optionThree ->{
                optionThree?.let {
                    if(isSelected)
                    selectedOptionView(it,3)
                }

            }
            R.id.optionFour ->{
                optionFour?.let {
                    if(isSelected)
                    selectedOptionView(it,4)
                }

            }
            R.id.btn_submit->{
                if(mSelectedOptionPosition==0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{

                            setQuestion()
                        }
                        else ->
                        {
                            val intent= Intent(this,ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME,userName)
                            intent.putExtra(Constants.CORRECT_ANS,mCorrectAns)
                            intent.putExtra(Constants.Total_Question,mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else
                {
                    val question=mQuestionList?.get(mCurrentPosition -1)
                    if(question!!.correctAns !=mSelectedOptionPosition){

                        answerView(mSelectedOptionPosition,R.drawable.wrong_selection)
                    }else{
                        mCorrectAns++
                    }
                    answerView(question.correctAns,R.drawable.correct_selection)

                    if(mCurrentPosition == mQuestionList!!.size){
                        submit?.text="Finish"
                    }else
                    {
                        submit?.text="Go To Next Question"
                    }

                    isSelected=false
                    mSelectedOptionPosition=0
                }

            }
        }

    }


}