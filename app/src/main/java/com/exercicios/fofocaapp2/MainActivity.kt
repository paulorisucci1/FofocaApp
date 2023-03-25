package com.exercicios.fofocaapp2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.exercicios.fofocaapp2.model.Fofoca
import com.exercicios.fofocaapp2.model.FofocaGame

class MainActivity : AppCompatActivity() {

    lateinit var fofocaGame: FofocaGame
    lateinit var mainPlayButton: Button
    lateinit var mainRegisterButton: Button
    lateinit var intentPlayActivity: Intent
    lateinit var intentRegisterActivity: Intent
    lateinit var mainAmountOfFofocasTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPlayButton = findViewById(R.id.mainPlayButton)
        mainRegisterButton = findViewById(R.id.mainRegisterButton)
        mainAmountOfFofocasTextView = findViewById(R.id.mainAmountOfFofocasTextView)
        intentRegisterActivity = Intent(this@MainActivity, FofocaRegisterActivity::class.java)
        intentPlayActivity = Intent(this@MainActivity, FofocaGameActivity::class.java)
        fofocaGame = FofocaGame()

        val registerResultContract = registerForActivityResult(ActivityResultContracts
            .StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                val fofocaList = it.data?.getSerializableExtra("FOFOCA_LIST" ) as ArrayList<Fofoca>
                this.fofocaGame.addFofocaList(fofocaList)
            } else {
                Toast.makeText(this, "Operação de cadastro cancelada", Toast.LENGTH_SHORT).show()
            }
            this.fofocaGame.getFofocaList()
        }

        val playResultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == RESULT_OK) {
                Toast.makeText(this, "Fim de jogo!", Toast.LENGTH_SHORT).show()
            }
        }

        mainRegisterButton.setOnClickListener { registerResultContract.launch(intentRegisterActivity) }
        mainPlayButton.setOnClickListener {
            intentPlayActivity.putExtra("FOFOCA_GAME", fofocaGame)
            playResultContract.launch(intentPlayActivity)
        }

    }

    override fun onResume() {
        super.onResume()
        this.mainAmountOfFofocasTextView.text = "Quantidade de fofocas: ${this.fofocaGame.getAmountOfFofocas()}"
    }
}
