package com.example.thirty

import kotlin.random.Random

class Game {
    private val diceList = listOf(
        Dice(1, false),
        Dice(2, false),
        Dice(3, false),
        Dice(4, false),
        Dice(5, false),
        Dice(6, false),
    )
    private var numThrows = -1

    fun getDiceList(): List<Dice> {
        return diceList
    }

    fun getNumThrows(): Int {
        return numThrows
    }

    fun throwDice() {
        for (d in diceList.indices) {
            if (!diceList[d].locked) {
                var value = Random.nextInt(1, 7)
                diceList[d].value = value
            }
        }

        numThrows += 1
    }

    fun toggleLockedDice(dice: Int) {
        diceList[dice].locked = !diceList[dice].locked
    }

}