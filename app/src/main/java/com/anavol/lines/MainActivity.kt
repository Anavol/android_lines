package com.anavol.lines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
        val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
        var numberOfVertices = 5
        val gameIntent = Intent(this, GameActivity::class.java)


        playButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View) {

                var x = constraintLayout.width
                var y = constraintLayout.height

                gameIntent.putExtra("X", x)
                gameIntent.putExtra("Y", y)
                gameIntent.putExtra("N", numberOfVertices)
                startActivity(gameIntent)

            }
        })
        radioGroup.visibility = View.INVISIBLE

        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                numberOfVertices = radio.text.toString().toInt()
            })
    }
}


