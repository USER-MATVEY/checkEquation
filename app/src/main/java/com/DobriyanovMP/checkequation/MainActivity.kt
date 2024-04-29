package com.DobriyanovMP.checkequation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.DobriyanovMP.checkequation.databinding.ActivityMainBinding
import kotlin.math.round
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var state: ActivityState

    private var firstOperand = 10
    private var secondOperand = 20
    private var operation = "-"
    private var equationsCount = 20
    private var correctAnswers = 10
    private var wrongAnswers = 20
    private var percentOfCorrect = 100.0
    private var showedAnswer = 0
    private var trueAnswer = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state = savedInstanceState?.getParcelable("activityState")?: ActivityState(
            allEquationsCount = 0,
            correctEquationsCount = 0,
            wrongEquationsCount = 0,
            lastFirstOperand = 0,
            lastSecondOperand = 0,
            lastOperation = "+",
            lastAnswer = 0,
            lastTrueAnswer = 0,
            startButtonState = true,
            equationColor = getColor(R.color.white),
            correctButtonState = false,
            wrongButtonState = false,
            nextButtonState = true
        )

        equationsCount = state.allEquationsCount
        correctAnswers = state.correctEquationsCount
        wrongAnswers = state.wrongEquationsCount
        firstOperand = state.lastFirstOperand
        secondOperand = state.lastSecondOperand
        operation = state.lastOperation
        showedAnswer = state.lastAnswer
        trueAnswer = state.lastTrueAnswer
        binding.StartButton.visibility =
            if (!state.startButtonState) View.GONE else View.VISIBLE
        binding.NextButton.visibility =
            if (!state.startButtonState) View.VISIBLE else View.GONE
        binding.EquasionLayout.setBackgroundColor(state.equationColor)

        setEquation()
        binding.AllEqScoreField.text = equationsCount.toString()
        binding.AllCorrectScoreField.text = correctAnswers.toString()
        binding.AllWrongScoreField.text = wrongAnswers.toString()
        binding.PercentScore.text = "$percentOfCorrect%"

        binding.CorrectButton.isEnabled = state.correctButtonState
        binding.WrongButton.isEnabled = state.wrongButtonState
        binding.NextButton.isEnabled = state.nextButtonState

        binding.StartButton.setOnClickListener { start() }
        binding.NextButton.setOnClickListener { createEquation() }
        binding.CorrectButton.setOnClickListener { correctButtonPressed() }
        binding.WrongButton.setOnClickListener { wrongButtonPressed() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("activityState", state)
    }

    private fun start() {
        binding.StartButton.visibility = View.GONE
        binding.NextButton.visibility = View.VISIBLE
        state.startButtonState = false
        createEquation()
    }

    private fun createEquation() {
        binding.CorrectButton.isEnabled = true
        binding.WrongButton.isEnabled = true
        binding.NextButton.isEnabled = false

        state.nextButtonState = false
        state.correctButtonState = true
        state.wrongButtonState = true

        var tmp: Int
        val newOperation = Random.nextInt(1, 5)

        when (newOperation) {
            1 -> operation = "+"
            2 -> operation = "-"
            3 -> operation = "*"
            4 -> operation = "\\"
        }

        firstOperand = Random.nextInt(1, 100)
        secondOperand = Random.nextInt(1, 100)

        if (newOperation == 4) {
            while (firstOperand % secondOperand != 0){
                firstOperand = Random.nextInt(1, 100)
                secondOperand = Random.nextInt(1, 100)
                if (firstOperand < secondOperand){
                    tmp = firstOperand
                    firstOperand = secondOperand
                    secondOperand = tmp
                }
            }
        }

        when (newOperation) {
            1 -> trueAnswer = firstOperand + secondOperand
            2 -> trueAnswer = firstOperand - secondOperand
            3 -> trueAnswer = firstOperand * secondOperand
            4 -> trueAnswer = firstOperand / secondOperand
        }

        if (Random.nextInt(0, 100) >= 50){
            when (newOperation) {
                1 -> showedAnswer = trueAnswer + Random.nextInt(10, 100)
                2 -> showedAnswer = trueAnswer - Random.nextInt(10, 100)
                3 -> showedAnswer = trueAnswer * Random.nextInt(2, 10)
                4 -> showedAnswer = trueAnswer / Random.nextInt(2, 10)
            }
        }
        else { showedAnswer = trueAnswer }

        setEquation()
    }

    // TODO:
    //      colors and stops prepares timer
    //      For both functions!!!
    private fun correctButtonPressed(){
        binding.CorrectButton.isEnabled = false
        binding.WrongButton.isEnabled = false
        binding.NextButton.isEnabled = true

        state.nextButtonState = true
        state.correctButtonState = false
        state.wrongButtonState = false

        if (showedAnswer == trueAnswer){
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.green))
            state.equationColor = getColor(R.color.green)
            correctAnswers += 1
        }
        else{
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.red))
            state.equationColor = getColor(R.color.red)
            wrongAnswers += 1
        }
        updateStatistics()
    }

    private fun wrongButtonPressed(){
        binding.CorrectButton.isEnabled = false
        binding.WrongButton.isEnabled = false
        binding.NextButton.isEnabled = true

        state.nextButtonState = true
        state.correctButtonState = false
        state.wrongButtonState = false

        if (showedAnswer == trueAnswer){
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.red))
            state.equationColor = getColor(R.color.red)
            wrongAnswers += 1
        }
        else{
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.green))
            state.equationColor = getColor(R.color.green)
            correctAnswers += 1
        }
        updateStatistics()
    }

    private fun updateStatistics(){
        equationsCount += 1
        percentOfCorrect = round(((correctAnswers / equationsCount.toDouble()) * 100))

        binding.AllEqScoreField.text = equationsCount.toString()
        binding.AllCorrectScoreField.text = correctAnswers.toString()
        binding.AllWrongScoreField.text = wrongAnswers.toString()
        binding.PercentScore.text = "$percentOfCorrect%"
    }

    private fun setEquation() {
        binding.Operand1.text = firstOperand.toString()
        binding.Operand2.text = secondOperand.toString()
        binding.Operation.text = operation
        binding.AnswerField.text = showedAnswer.toString()

        state.lastFirstOperand = firstOperand
        state.lastSecondOperand = secondOperand
        state.lastOperation = operation
        state.lastAnswer = showedAnswer
        state.allEquationsCount = equationsCount
        state.correctEquationsCount = correctAnswers
        state.wrongEquationsCount = wrongAnswers
    }
}