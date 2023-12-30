package com.example.quizapp

import android.content.Context
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.Locale

object Constants {


    fun getFlagInfo(context: Context):ArrayList<CountryInfo>{
        val flag:ArrayList<CountryInfo> = ArrayList()
        val obj=JSONObject(getJSONFromAssets(context)!!)
        val countryFlags=obj.getJSONArray("flags")
        for(i in 0 until countryFlags.length()){
            var countryCode=" "
            val  flagObj=countryFlags.getJSONObject(i)
            val flagUrl=flagObj.getString("flag")
            val country=flagObj.getString("country")
            if (flagObj.has("code")) {
                countryCode = flagObj.getString("code")

            }


            val flagInfo=CountryInfo(flagUrl,country,countryCode)
            flag.add(flagInfo)

        }

        return flag ;
    }

    private fun getJSONFromAssets(context:Context): String? {
        var json:String?=null
        val charset: Charset = Charsets.UTF_8
        try {
            val IS= context.assets.open("flags.json")
            val size=IS.available()
            val buffer=ByteArray(size)
            IS.read(buffer)
            IS.close()
            json= String(buffer,charset)
        }catch (ex: IOException){
            ex.printStackTrace()
            return null
        }
        return json

    }

    fun getQuestions(context: Context):ArrayList<Question> {
        val question = ArrayList<Question>()
        val flagInfo= getFlagInfo(context)
        val ques1 = Question(
            1,
            "What country does this flag belong to?",
            "Argentina",
            "Australia",
            "Armenia",
            "Austria",
            1,
            flagInfo[0]
            )
        question.add(ques1)

        val ques2 = Question(
            1,
            "What country does this flag belong to?",
            "Argentina",
            "Australia",
            "Andorra",
            "Austria",
            3,
            flagInfo[1]
        )
        question.add(ques2)

        val ques3 = Question(
            1,
            "What country does this flag belong to?",
            "United Arab Emirates",
            "Australia",
            "Armenia",
            "Austria",
            1,
            flagInfo[2]
        )
        question.add(ques3)

        val ques4 = Question(
            1,
            "What country does this flag belong to?",
            "Argentina",
            "Afghanistan",
            "Armenia",
            "United Arab Emirates",
            2,
            flagInfo[3]
        )
        question.add(ques4)

        val ques5 = Question(
            1,
            "What country does this flag belong to?",
            "Argentina",
            "Antigua & Barbuda",
            "Armenia",
            "United Arab Emirates",
            2,
            flagInfo[4]
        )
        question.add(ques5)


        return question

    }

}