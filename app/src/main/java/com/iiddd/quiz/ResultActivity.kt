package com.iiddd.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private var header: TextView? = null
    private var congratsImage: ImageView? = null
    private var congratsHeader: TextView? = null
    private var name: TextView? = null
    private var score: TextView? = null
    private var finishButton: Button? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }


}