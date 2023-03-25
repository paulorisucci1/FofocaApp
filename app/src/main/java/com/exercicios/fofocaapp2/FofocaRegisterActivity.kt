package com.exercicios.fofocaapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.exercicios.fofocaapp2.model.Fofoca

class FofocaRegisterActivity : AppCompatActivity() {

    lateinit var descriptionEditTextRegister: EditText
    lateinit var veracityTrueRadioButtonRegister: RadioButton
    lateinit var veracityFalseRadioButtonRegister: RadioButton
    lateinit var addButtonRegister: Button
    lateinit var saveButtonRegister: Button
    lateinit var cancelButtonRegister: Button
    val fofocaList = mutableListOf<Fofoca>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fofoca_register)

        descriptionEditTextRegister = findViewById(R.id.fofocaDescriptionEditTextRegister)
        veracityTrueRadioButtonRegister = findViewById(R.id.veracityTrueRadioButtonRegister)
        veracityFalseRadioButtonRegister = findViewById(R.id.veracityFalseRadioButtonRegister)
        addButtonRegister = findViewById(R.id.addButtonRegister)
        saveButtonRegister = findViewById(R.id.saveButtonRegister)
        cancelButtonRegister = findViewById(R.id.cancelButtonRegister)

        addButtonRegister.setOnClickListener {
            var description = descriptionEditTextRegister.text.toString()
            var veracity = veracityTrueRadioButtonRegister.isChecked
            var newFofoca = Fofoca(description, veracity)
            fofocaList.add(newFofoca)

            descriptionEditTextRegister.text.clear()
            Toast.makeText(this, "Fofoca adicionada ao carrinho.", Toast.LENGTH_SHORT).show()
        }

        saveButtonRegister.setOnClickListener {
            if(fofocaList.isEmpty()) {
                Toast.makeText(this, "Adicione fofocas ao carrinho antes de salvar.",
                    Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent().apply {
                    putExtra("FOFOCA_LIST", ArrayList(fofocaList))
                }
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        cancelButtonRegister.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}