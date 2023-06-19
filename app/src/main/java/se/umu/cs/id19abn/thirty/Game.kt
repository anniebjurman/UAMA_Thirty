package se.umu.cs.id19abn.thirty

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