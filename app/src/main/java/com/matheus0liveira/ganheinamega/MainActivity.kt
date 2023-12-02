package com.matheus0liveira.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edit_number)
        val txtResult = findViewById<TextView>(R.id.text_result)
        val btnGenerate = findViewById<TextView>(R.id.btn_generate)

        result?.let {

            txtResult.text = it
        }

        btnGenerate.setOnClickListener {
            onClickNumberGenerator(editText.text.toString(), txtResult)
        }
    }

    private fun onClickNumberGenerator(text: String, textResult: TextView) {

        if (text.isEmpty()) return showToastError()
        val qtd = text.toInt()

        if (qtd !in 6..15) return showToastError()

        val numbers = generateRandomNumbers(qtd)

        textResult.text = numbers.joinToString(" - ")

        prefs.edit().apply {
            putString("result", textResult.text.toString())
            apply() // Async and not return status
            // editor.commit() // Sync and return status(boolean)
        }


    }

    private fun showToastError(text: String? = "Informe um número entre 6 e 15") {
        Toast
            .makeText(this, text, Toast.LENGTH_SHORT)
            .show()
    }

    private fun generateRandomNumbers(maxQtd: Int): MutableList<Int> {
        val numbers = mutableListOf<Int>()
        val random = java.util.Random()

        while (true) {
            numbers.add(random.nextInt(60) + 1)
            if (numbers.size == maxQtd) break
        }

        return numbers
    }
}