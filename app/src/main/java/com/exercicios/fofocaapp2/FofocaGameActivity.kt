package com.exercicios.fofocaapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.*
import com.exercicios.fofocaapp2.model.FofocaGame

class FofocaGameActivity : AppCompatActivity() {

    lateinit var fofocaTextView: TextView
    lateinit var fofocaTruthRadioButton: RadioButton
    lateinit var fofocaLieRadioButton: RadioButton
    lateinit var gameTimerProgressBar: ProgressBar
    private lateinit var gameResponseButton: Button
    lateinit var fofocaGame: FofocaGame
    lateinit var fofocaGameRadioGroup: RadioGroup
    private lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fofoca_game)

        fofocaTextView = findViewById(R.id.fofocaTextView)
        fofocaTruthRadioButton = findViewById(R.id.fofocaTruthRadioButton)
        fofocaLieRadioButton = findViewById(R.id.fofocaLieRadioButton)
        gameTimerProgressBar = findViewById(R.id.gameTimerProgressBar)
        gameResponseButton = findViewById(R.id.gameResponseButton)
        fofocaGameRadioGroup = findViewById(R.id.fofocaGameRadioGroup)

        fofocaGame = intent.getSerializableExtra("FOFOCA_GAME") as FofocaGame
        fofocaGame.startGame()

        fofocaTextView.text = fofocaGame.currentFofoca.description

        gameResponseButton.setOnClickListener(ResponseOnClickListener())

        declareThread()
    }

    private fun declareThread() {
        timer = Timer()
        timer.run()
    }
    private fun timeOut() {
        fofocaGame.timeOut()
        Toast.makeText(this, "Time out.", Toast.LENGTH_SHORT).show()
        prepareLayoutForNextFofoca()
    }

    private fun proccessGuess() {
        val userGuess = fofocaTruthRadioButton.isChecked
        fofocaGame.guessFofocaVeracity(userGuess)
        Toast.makeText(this, fofocaGame.getStatusDescription(), Toast.LENGTH_SHORT).show()
    }

    private fun prepareLayoutForNextFofoca() {
        if(fofocaGame.gameOver()) {
            Toast.makeText(this, "Pontuação final: "+fofocaGame.getScore(), Toast.LENGTH_SHORT).show()
            timer.stopRunning()
            setResult(RESULT_OK)
            finish()
        } else {
            fofocaGame.setNextFofoca()
            clearFields()
        }
    }

    private fun clearFields() {
        fofocaGameRadioGroup.clearCheck()
        fofocaTextView.text = fofocaGame.currentFofoca.description
        gameTimerProgressBar.progress = 0
    }

    inner class ResponseOnClickListener: OnClickListener {
        override fun onClick(p0: View?) {
            if(!fofocaTruthRadioButton.isChecked && !fofocaLieRadioButton.isChecked) {
                Toast
                    .makeText(this@FofocaGameActivity, "Selecione uma opção",
                        Toast.LENGTH_SHORT)
                    .show()
                return
            }
            this@FofocaGameActivity.proccessGuess()
            this@FofocaGameActivity.prepareLayoutForNextFofoca()
        }
    }

    inner class Timer() : Runnable {
        private var running = true

        fun stopRunning() {
            running = false
        }

        override fun run() {
            Thread {
                while (gameTimerProgressBar.progress < 100) {
                    if(!running) {
                        break
                    }
                    Thread.sleep(50)
                    gameTimerProgressBar.progress+=1
                    if(gameTimerProgressBar.progress == 99) {
                        Log.i("THREAD_GAME", "gameTimerProgressBar.progress = "+gameTimerProgressBar.progress)
                        runOnUiThread { timeOut() }
                    }
                }
            }.start()
        }
    }
}