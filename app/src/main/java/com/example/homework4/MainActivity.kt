package com.example.homework4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var dreamRecyclerView: RecyclerView
    private lateinit var button_add: Button

    private val dreamViewModel: DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dreamRecyclerView = findViewById(R.id.dreamRecyclerView)
        button_add = findViewById(R.id.button_add)

        val adapter = DreamListAdapter()
        dreamRecyclerView.adapter = adapter
        dreamRecyclerView.layoutManager = LinearLayoutManager(this)

            dreamViewModel.allDreams.observe(this, {
                    dreams -> dreams?.let{
                        adapter.submitList(it)
                    }
            })

        button_add.setOnClickListener{
            var intent = Intent(this@MainActivity, AddActivity::class.java)
            startActivity(intent)
        }

    }
}