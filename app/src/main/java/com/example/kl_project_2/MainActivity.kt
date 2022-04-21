package com.example.kl_project_2

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        //Pogu definēšana. btn1-"Player starts". btn2-"Computer starts"
        val btn1: Button = findViewById<View>(R.id.button) as Button
        val btn2: Button = findViewById<View>(R.id.button2) as Button

        //setOnClickListener ļauj veikt funkcijas, kad uzspiež uz pogas
        btn1.setOnClickListener{
            //Otrās aktivitātes definēšana
            val intent = Intent(this, MainActivity2::class.java)
            //intent.putExtra("starts","P") ļauj aizsūtīt informāciju uz nākamo aktivitāti. Šajā gadījumā aizsūtā informāciju "P", kas nākamājā
            //aktivitāte pasaka, ka spēlētājs sāk spēli
            intent.putExtra("starts","P")
            //Pārvieto spēlētāju uz otro aktivitāte
            startActivity(intent)
        }
        //Nospiežot uz pogas btn2("Computer starts") aizsūta spēlētāju uz otro aktivitāti un nosūta vēl informācjiu
        //ka dators sāk spēli
        btn2.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("starts","C")
            startActivity(intent)
        }
    }
}