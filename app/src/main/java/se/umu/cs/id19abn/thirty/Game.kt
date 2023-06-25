package se.umu.cs.id19abn.thirty

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
    private var chosenLevel = 1
    private var totalPoints = 0
    private var historyPoints = arrayListOf<Any>()

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

    fun countPoints() {
        val dice = arrayListOf<Int>()
        var sum = 0
        for (d in diceList.indices) {
            if (diceList[d].locked) {
                dice.add(diceList[d].value)
                sum += diceList[d].value
            }
        }

        // TODO: low is not implemented
        if (sum == chosenLevel) {
            totalPoints += sum
            //historyPoints.add({dice, sum})
        } else {

        }
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

    fun setCurrentSet(step: Int) {
        currentStep = step
    }

    fun getCurrentStep(): Int {
        return currentStep
    }
    private fun resetDice() {
        diceList.forEach { it.locked = false }
    }

    fun toggleLockedDice(dice: Int) {
        diceList[dice].locked = !diceList[dice].locked
    }

}