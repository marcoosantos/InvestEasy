package com.example.investeasy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.investeasy.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val formatter = DecimalFormat("R$ #,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnClean.setOnClickListener {
            binding.tieValue.setText("")
            binding.tieNumbermonth.setText("")
            binding.tieJuros.setText("")
            binding.tvResult.setText("0.00")
            binding.tvResultRendimentos.setText("0.00")
        }
        binding.btnCalcular.setOnClickListener {

            val aporteStr = binding.tieValue.text.toString()
            val mesesStr = binding.tieNumbermonth.text.toString()
            val jurosStr = binding.tieJuros.text.toString()

            if (aporteStr.isEmpty() || mesesStr.isEmpty() || jurosStr.isEmpty()) {

                Snackbar
                    .make(
                        binding.root,
                        "Preencha todos os campos",
                        Snackbar.LENGTH_LONG
                    )
                    .show()

            } else {

                val aporte: Float = binding.tieValue.text.toString().toFloat()
                val meses: Float = binding.tieNumbermonth.text.toString().toFloat()
                val taxaJuros: Float = binding.tieJuros.text.toString().toFloat() / 100

                val montante: Float = calcularMontante(aporte, meses, taxaJuros) as Float

                val totalInvestido = aporte * meses
                val rendimentos = montante - totalInvestido

                binding.tvResult.text = formatter.format(montante)
                binding.tvResultRendimentos.text = formatter.format(rendimentos)

            }

        }
    }

    private fun calcularMontante(aporte: Float, meses: Float, taxaJuros: Float): Float {
        return if (taxaJuros != 0f) {
            aporte * ((Math.pow((1 + taxaJuros.toDouble()), meses.toDouble()) - 1) / taxaJuros).toFloat()
        } else {
            aporte * meses
        }
    }
}

