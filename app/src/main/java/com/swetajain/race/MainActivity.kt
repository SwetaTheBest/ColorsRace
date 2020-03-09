package com.swetajain.race

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var raceEnd = false
    private var greenJob: Job? = null
    private var redJob: Job? = null
    private var blueJob: Job? = null
    private var purpleJob: Job? = null
    private var orangeJob: Job? = null
    private var yellowJob: Job? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton: Button = findViewById(R.id.buttonStart)
        startButton.setOnClickListener {
            startUpdate()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startUpdate() {
        resetRun()

        greenJob = CoroutineScope(Dispatchers.Main).launch {
            AndroidContinuation.Android
            startRunning(progressBarGreen)
        }
        redJob = CoroutineScope(Dispatchers.Main).launch {
            AndroidContinuation.Android
            startRunning(progressBarRed)
        }
        blueJob = CoroutineScope(Dispatchers.Main).launch {
            AndroidContinuation.Android
            startRunning(progressBarBlue)
        }

        orangeJob = CoroutineScope(Dispatchers.Main).launch {
            AndroidContinuation.Android
            startRunning(progressBarOrange)
        }

        yellowJob = CoroutineScope(Dispatchers.Main).launch {
            AndroidContinuation.Android
            startRunning(progressBarYellow)
        }

        purpleJob = CoroutineScope(Dispatchers.Main).launch {
            AndroidContinuation.Android
            startRunning(progressBarPurple)
        }


    }

    fun ClosedRange<Int>.random() =
        Random.nextInt(endInclusive - start) + start


    override fun onDestroy() {
        super.onDestroy()
        resetRun()

    }

    private fun resetRun() {
        raceEnd = false
        greenJob?.cancel()
        redJob?.cancel()
        blueJob?.cancel()
        purpleJob?.cancel()
        yellowJob?.cancel()
        orangeJob?.cancel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun startRunning(progressBar: RoundCornerProgressBar) {
        progressBar.progress = 0f
        while (progressBar.progress < 1000 && !raceEnd) {
            delay(10)
            progressBar.progress += (1..10).random()
        }
        if (!raceEnd) {
            raceEnd = true
            var toast = Toast(this)
            toast = Toast.makeText(
                this,
                " The ${progressBar.tooltipText} color won!",
                Toast.LENGTH_LONG
            )
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            // toast.setMargin(1.0F, 1.0F)
            toast.show()
        }
    }
}