package com.example.homework4

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity(){

    private lateinit var editTextTitle : EditText
    private lateinit var editTextContent : EditText
    private lateinit var editTextReflection: EditText
    private lateinit var spinnerEmotion: Spinner
    private lateinit var buttonSave : Button
    private val emotions = arrayOf<String>("fear","panic","loss of self", "grief", "freedom", "love", "joy", "vulnerable", "confused","sad")

    private val dreamViewModel : DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        editTextTitle = findViewById(R.id.edit_word_dreamTitle)
        editTextContent = findViewById(R.id.edit_word_whatHappened)
        editTextReflection = findViewById(R.id.edit_word_interpretation)
        spinnerEmotion = findViewById(R.id.spinner_feelings)
        buttonSave = findViewById(R.id.button_save)

        ArrayAdapter.createFromResource(
            this,
            R.array.emotions,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerEmotion.adapter = adapter
        }

        intent = getIntent()

        if(intent.getBooleanExtra("Test", false)){ // need to reconfigure the logic behind addactivity
            dreamViewModel.getDream(Integer.valueOf(intent.getIntExtra("Dreamid", 0))).observe(this,{
                editTextTitle.setText(it.title)
                editTextContent.setText(it.content)
                editTextReflection.setText(it.reflection)
                spinnerEmotion.setSelection(getIndex(emotions, it.emotion))
            })

            buttonSave.setOnClickListener {
                if(TextUtils.isEmpty(editTextTitle.text) || TextUtils.isEmpty(editTextContent.text) || TextUtils.isEmpty(editTextReflection.text) || TextUtils.isEmpty(spinnerEmotion.selectedItem.toString())){
                    toastError("Missing Fields")
                }
                else{
                    dreamViewModel.updateDream(editTextTitle.text.toString(), editTextContent.text.toString(), editTextReflection.text.toString(), spinnerEmotion.selectedItem.toString(), Integer.valueOf(intent.getIntExtra("Dreamid", 0)))
                    intent = null // so that process can repeat
                    finish()
                }
            }

        }

        else {
            buttonSave.setOnClickListener {
                if (TextUtils.isEmpty(editTextTitle.text) || TextUtils.isEmpty(editTextContent.text) || TextUtils.isEmpty(editTextReflection.text) || TextUtils.isEmpty(spinnerEmotion.selectedItem.toString())) {
                    toastError("Missing Fields")
                } else {
                    val dream = Dream(editTextTitle.text.toString(), editTextContent.text.toString(), editTextReflection.text.toString(), spinnerEmotion.selectedItem.toString())
                    dreamViewModel.insert(dream)
                    finish()
                }
            }
        }
    }

    private fun toastError(text: String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    private fun getIndex(array: Array<String>, input: String) : Int{
        var count: Int = 0
        for(item in array){
            if (item.equals(input)){
                return count
            }
            count = count + 1
        }
        return 0
    }

}