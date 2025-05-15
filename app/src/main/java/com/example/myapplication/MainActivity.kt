package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.myapplication.R.id.datePicker
import java.time.LocalDate
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //acessando o spinner sexo
        val spinnerSexo = findViewById<Spinner>(R.id.spinner_sexo)

        //acessando a caixa de idade
        val editTextIdade = findViewById<EditText>(R.id.edit_text_idade)

        //acessando o texto resultado
        val textViewResultado = findViewById<TextView>(R.id.text_view_resultado)

        //acessando o botão de calcular
        val buttonCalcular = findViewById<Button>( R.id.button_calcular)

        val pegadorData = findViewById<DatePicker>(datePicker)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            listOf("masculino" , "feminino")
        )
        spinnerSexo.adapter =adapter

        buttonCalcular.setOnClickListener(){
            val sexoSelecionado = spinnerSexo.selectedItem as String
            val dataNasc = LocalDate.of(
                pegadorData.year,
                pegadorData.month+1,
                pegadorData.dayOfMonth
            )
            val diaAtual = LocalDate.now()
            val idade = ChronoUnit.YEARS.between(dataNasc,diaAtual).toInt()
            val tempoContribuicao = editTextIdade.text.toString().toInt()

            var idadeMin = 0
            var tempoContMin = 0
            var resultado = 0
            var resutadoSegundo = 0
            if(sexoSelecionado == "masculino"){
                resultado = 65 - idade
                resutadoSegundo = 35 - tempoContribuicao
                idadeMin = 65
                tempoContMin = 35

            }else{
                resultado = 62 - idade
                resutadoSegundo = 30 -tempoContribuicao
                idadeMin = 62
                tempoContMin = 30
            }
            if (resultado <=0 ||resutadoSegundo<=0) {
                textViewResultado.text = "Você já pode se aposentar!"
            }
            else{
                val dataAposentadoriaIdade = dataNasc.plusYears(idadeMin.toLong())
                val dataAposentadoriaContribuicao = dataNasc.plusYears(tempoContMin.toLong())
                val diasParaAposentarIdade = ChronoUnit.DAYS.between(diaAtual, dataAposentadoriaIdade)
                val diasParaAposentarTrabalhando = ChronoUnit.DAYS.between(diaAtual, dataAposentadoriaContribuicao)
                textViewResultado.text = "Faltam $diasParaAposentarIdade para aposentar, data de aposentadoria por idade: $dataAposentadoriaIdade \n Faltam $diasParaAposentarTrabalhando para aposentar, data de aposentadoria por contribuição: $dataAposentadoriaContribuicao"

            }
        }
    }

}