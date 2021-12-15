package com.example.recipeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ShowInformation : AppCompatActivity() {

    lateinit var tvTitle: TextView
    lateinit var tvAuthor: TextView
    lateinit var tvIinstructions: TextView
    lateinit var tvIngredients: TextView
    lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_information)

        tvTitle = findViewById(R.id.dTitle)
        tvAuthor = findViewById(R.id.dAuthor)
        tvIinstructions = findViewById(R.id.dInstructions)
        tvIngredients = findViewById(R.id.dIngredients)
        btnBack = findViewById(R.id.btnBack)

        val disData = intent.getSerializableExtra("displayData") as recipeDataItem

        Log.d("gg", "$disData")
        tvTitle.text = disData.title
        tvAuthor.text = disData.author
        tvIngredients.text = disData.ingredients
        tvIinstructions.text = disData.instructions

        btnBack.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}