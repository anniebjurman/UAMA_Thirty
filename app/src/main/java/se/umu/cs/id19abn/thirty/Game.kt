package se.umu.cs.id19abn.thirty

import android.util.Log
import java.util.Objects
import kotlin.random.Random

class Game {
    private val diceList = listOf(
        Dice(1, false, false),
        Dice(2, false, false),
        Dice(3, false, false),
        Dice(4, false, false),
        Dice(5, false, false),
        Dice(6, false, false),
    )
    private var currentThrow = 0
    private var currentRound = 1
    private var numRounds = 10
    private var roundIsOver = false
    private var currentStep = 1
    private var chosenLevel = 0
    private var totalPoints = 0
    private var historyPoints = arrayListOf<Score>()

    fun getRoundIsOver(): Boolean {
        return roundIsOver
    }
    fun getDiceList(): List<Dice> {
        return diceList
    }

    fun getCurrentRound(): Int {
        return currentRound
    }

    fun getCurrentThrow(): Int {
        return currentThrow
    }

    fun setChosenLevel(level: String) {
        when (level) {
            "low" -> {chosenLevel = 0}
            "1" -> {chosenLevel = 1}
            "2" -> {chosenLevel = 2}
            "3" -> {chosenLevel = 3}
            "4" -> {chosenLevel = 4}
            "5" -> {chosenLevel = 5}
            "6" -> {chosenLevel = 6}
            "7" -> {chosenLevel = 7}
            "8" -> {chosenLevel = 8}
            "9" -> {chosenLevel = 9}
            "10" -> {chosenLevel = 10}
            "11" -> {chosenLevel = 11}
            "12" -> {chosenLevel = 12}
        }
    }

    fun throwDice() {
        if (currentThrow == 3) {
            currentThrow = 0
            currentRound += 1
            roundIsOver = false
            updateStep()
            return
        }

        currentThrow += 1
        generateNewDice()

        if (currentThrow == 3) {
            roundIsOver = true
            resetDice()
        }

        updateStep()
    }

    fun countPoints(): String {
        val dice = mutableListOf<Int>()
        var sum = 0
        val diceIndices = mutableListOf<Int>()
        for (d in diceList.indices) {
            if (diceList[d].locked) {
                diceList[d].counted = true
                diceList[d].locked = false

                sum += diceList[d].value
                dice.add(diceList[d].value)
                diceIndices.add(d)
            }
        }

        if (chosenLevel == 0) {
            return if (sum == 1 || sum == 2 || sum == 3) {
                totalPoints += sum
                historyPoints.add(Score(dice, sum, currentRound))
                "$sum points added!"

            } else {
                diceIndices.forEach {
                    diceList[it].counted = false
                    diceList[it].locked = true
                }
                "Selected dice does not add upp to 1, 2 or 3"
            }
        } else {
            return if (sum == chosenLevel) {
                totalPoints += sum
                historyPoints.add(Score(dice, sum, currentRound))
                "$sum points added!"
            } else {
                diceIndices.forEach {
                    diceList[it].counted = false
                    diceList[it].locked = true
                }
                "Selected dice does not add upp to $chosenLevel"
            }
        }

    }

    fun getTotalPoints(): Int {
        return totalPoints
    }

    private fun generateNewDice() {
        for (d in diceList.indices) {
            if (!diceList[d].locked) {
                val value = Random.nextInt(1, 7)
                diceList[d].value = value
            }
        }
    }

    private fun updateStep() {
        if (currentThrow == 0) {
            currentStep = 1
        } else if (currentThrow < 3) {
            currentStep = 2
        } else if (currentThrow == 3) {
            currentStep = 3
        }
    }

    fun setCurrentStep(step: Int) {
        currentStep = step

        if (currentStep == 1) {
            currentRound += 1
            currentThrow = 0
            resetDice()
        }
    }

    fun getCurrentStep(): Int {
        return currentStep
    }

    private fun resetDice() {
        diceList.forEach {
            it.locked = false
            it.counted = false
        }
    }

    fun toggleLockedDice(dice: Int) {
        diceList[dice].locked = !diceList[dice].locked
    }

}