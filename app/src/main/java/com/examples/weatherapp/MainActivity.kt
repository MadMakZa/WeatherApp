package com.examples.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var user_field: EditText? = null
    private var main_btn: Button? = null
    private var result_info: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_field = findViewById(R.id.user_field)
        main_btn = findViewById(R.id.main_btn)
        result_info = findViewById(R.id.result_info)
        init()
    }

    private fun init(){
        main_btn?.setOnClickListener {
            if(user_field?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город", Toast.LENGTH_LONG).show()
            else{
                val city: String = user_field?.text.toString()
                val key: String = "2f9d86b6f0efbadae9d47297c65b5e0b"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key&units=metric"


                doAsync {
                    val apiResponse = URL(url).readText()
                    Log.d("INFO", apiResponse)
                    //работа с массивоми данных
                    val weather = JSONObject(apiResponse).getJSONArray("weather") //вытягиваем значение по ключу
                    val desc = weather.getJSONObject(0).getString("description") //вытягиваем из вытинутого значение по ключу дескрипшн

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val temp = main.getString("temp")

                    result_info?.text = "Температура: $temp\n$desc"
                }
            }
        }
    }
}