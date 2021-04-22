package com.example.homework4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode.valueOf

class DetailedDreamActivity : AppCompatActivity() {

    private lateinit var textView_detailedTitle : TextView
    private lateinit var textView_detailedContent: TextView
    private lateinit var textView_detailedEmotion: TextView
    private lateinit var textView_detailedReflection: TextView
    private lateinit var button_detailedDelete: Button
    private lateinit var button_detailedUpdate: Button

    private val dreamViewModel: DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaileddream)

        textView_detailedTitle = findViewById(R.id.textView_detailedTitle)
        textView_detailedContent = findViewById(R.id.textView_detailedContent)
        textView_detailedReflection = findViewById(R.id.textView_detailedReflection)
        textView_detailedEmotion = findViewById(R.id.textView_detailedEmotion)
        button_detailedDelete = findViewById(R.id.button_detailedDelete)
        button_detailedUpdate = findViewById(R.id.button_detailedUpdate)

        intent = getIntent()
        var id:Int = Integer.valueOf(intent.getIntExtra("Dreamid", 0))

        val intent1: Intent = Intent(this@DetailedDreamActivity, AddActivity::class.java)
        intent1.putExtra("Dreamid", id)
        intent1.putExtra("Test", true)
        button_detailedUpdate.setOnClickListener {
           startActivity(intent1)
        }

        button_detailedDelete.setOnClickListener {
            dreamViewModel.deleteDream(id)
            finish()
        }

        dreamViewModel.getDream(id).observe(this, {
            if(it !== null){
                textView_detailedTitle.text = it.title
                textView_detailedContent.text = "WHAT HAPPENED? \n" + it.content
                textView_detailedReflection.text = "WHAT IS YOUR INTERPERTATIION? \n" + it.reflection
                textView_detailedEmotion.text = "HOW DO YOU FEEL? \n" + it.emotion
            }
        })




    }
}