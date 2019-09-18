package com.anavol.lines

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class GameActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var numberOfVertices = intent.getIntExtra("N" , 5)
        val intent = Intent(this, MainActivity::class.java)
        val gameIntent = Intent(this, GameActivity::class.java)
        gameIntent.putExtra("X", intent.getIntExtra("X", 1000))
        gameIntent.putExtra("Y", intent.getIntExtra("Y", 1000))
        gameIntent.putExtra("N", numberOfVertices)
        customView.setValues(6)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View) {


                startActivity(gameIntent)
                finish()
            }
        })
    }
}
