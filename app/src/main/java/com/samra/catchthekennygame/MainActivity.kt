package com.samra.catchthekennygame

import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.samra.catchthekennygame.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var number = 0
    var score = 0
    var imageArray = ArrayList<ImageView>()

    var runnable: Runnable = Runnable {}
    var handler: Handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        //Count Down Timer
        object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                binding.timeView.text = "Time: ${p0 / 1000}"
            }

            override fun onFinish() {
                binding.timeView.text = "Time: 0"
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                alertDialog()
            }
        }.start()

        //Runnable for viewing pivture for 0.5 second each time
        runnable = object : Runnable {
            override fun run() {
                visibilityOfImage()
                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)

    }

    // Alert part
    fun alertDialog() {
        handler.removeCallbacks(runnable)
        var alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Game over")
        alert.setMessage("Restart the game?")
        alert.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                recreate()
            }
        })
        alert.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                binding.scoreView.text = "Score: 0"

            }
        })
        alert.show()

    }

    //Visibility of image randomly
    fun visibilityOfImage() {
        for (image in imageArray) {
            image.visibility = View.INVISIBLE
        }
        var randomImageId = Random.nextInt(imageArray.size)
        imageArray[randomImageId].visibility = View.VISIBLE
    }


    //Score Counter
    fun scoreCounter(view: View) {
        score++
        binding.scoreView.text = "Score: ${score}"
        println(score)
    }

}
