package com.DobriyanovMP.checkequation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.DobriyanovMP.calculateEquasion.ActivityState
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
    private var answer = 0


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
            startButtonState = true,
            equationColor = getColor(R.color.white)
        )

        equationsCount = state.allEquationsCount
        correctAnswers = state.correctEquationsCount
        wrongAnswers = state.wrongEquationsCount
        firstOperand = state.lastFirstOperand
        secondOperand = state.lastSecondOperand
        operation = state.lastOperation
        binding.StartButton.isEnabled = state.startButtonState
        binding.EquasionLayout.setBackgroundColor(state.equationColor)

        setEquation()
        binding.AllEqScoreField.text = equationsCount.toString()
        binding.AllCorrectScoreField.text = correctAnswers.toString()
        binding.AllWrongScoreField.text = wrongAnswers.toString()
        binding.PercentScore.text = "$percentOfCorrect%"


        binding.StartButton.setOnClickListener { start() }
        binding.CorrectButton.setOnClickListener { checkEquation() } // TODO: change function
        binding.WrongButton.setOnClickListener { checkEquation() } // TODO: change function
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("activityState", state)
    }

    private fun start() {
        binding.StartButton.isEnabled = false
        state.startButtonState = false
        createEquation()
    }

    private fun createEquation() { // TODO: repurpose answer
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
            1 -> answer = firstOperand + secondOperand
            2 -> answer = firstOperand - secondOperand
            3 -> answer = firstOperand * secondOperand
            4 -> answer = firstOperand / secondOperand
        }

        setEquation()
    }

    // TODO:
    //      divide function to: for correct and wrong
    //      they nust block two buttons and unlock NextButton
    //      colors and stops prepares timer
    //      function not creating nex equation
    private fun checkEquation(){
        if (binding.AnswerField.text.toString() == answer.toString()){
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.green))
            state.equationColor = getColor(R.color.green)
            correctAnswers += 1
        }
        else {
            binding.EquasionLayout.setBackgroundColor(getColor(R.color.red))
            state.equationColor = getColor(R.color.red)
            wrongAnswers += 1
        }
        equationsCount += 1
        percentOfCorrect = round(((correctAnswers / equationsCount.toDouble()) * 100))

        binding.AllEqScoreField.text = equationsCount.toString()
        binding.AllCorrectScoreField.text = correctAnswers.toString()
        binding.AllWrongScoreField.text = wrongAnswers.toString()
        binding.PercentScore.text = "$percentOfCorrect%"

        createEquation()
    }

    // TODO: create function for NextButton:
    //      locking NextButton
    //      Create next equation
    //      Unlocks answer buttons


    // TODO: add answer to the list of settings
    private fun setEquation() {
        binding.Operand1.text = firstOperand.toString()
        binding.Operand2.text = secondOperand.toString()
        binding.Operation.text = operation

        state.lastFirstOperand = firstOperand
        state.lastSecondOperand = secondOperand
        state.lastOperation = operation
        state.allEquationsCount = equationsCount
        state.correctEquationsCount = correctAnswers
        state.wrongEquationsCount = wrongAnswers
    }
}