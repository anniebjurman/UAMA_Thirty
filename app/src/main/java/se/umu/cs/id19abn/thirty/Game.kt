package se.umu.cs.id19abn.thirty

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.util.Objects
import kotlin.random.Random

class Game() : Parcelable {
    private var diceList = arrayListOf(
        Dice(1, false, false),
        Dice(2, false, false),
        Dice(3, false, false),
        Dice(4, false, false),
        Dice(5, false, false),
        Dice(6, false, false),
    )
    private var currentThrow = 0
    private var currentRound = 1
    private var maxNumRounds = 10
//    private var roundIsOver = false
    private var currentStep = 1
    private var chosenLevel = 0
    private var totalPoints = 0
    private var historyScores = arrayListOf<Score>()
    private var usedLevels = arrayListOf<String>()

    constructor(parcel: Parcel) : this() {
        diceList = arrayListOf<Dice>().also { parcel.readTypedList(ArrayList<Dice>(), Dice.CREATOR) }
        currentThrow = parcel.readInt()
        currentRound = parcel.readInt()
        maxNumRounds = parcel.readInt()
//        roundIsOver = parcel.readByte() != 0.toByte()
        currentStep = parcel.readInt()
        chosenLevel = parcel.readInt()
        totalPoints = parcel.readInt()
    }

    fun resetGame() {
        diceList = arrayListOf(
            Dice(1, false, false),
            Dice(2, false, false),
            Dice(3, false, false),
            Dice(4, false, false),
            Dice(5, false, false),
            Dice(6, false, false),
        )
        currentThrow = 0
        currentRound = 1
        maxNumRounds = 10
//        roundIsOver = false
        currentStep = 1
        chosenLevel = 0
        totalPoints = 0
        historyScores = arrayListOf()
        usedLevels = arrayListOf()
    }

    fun getHistoryScores(): ArrayList<Score> {
        return historyScores
    }

    fun getDiceList(): ArrayList<Dice> {
        return diceList
    }

    fun getMaxNumRounds(): Int {
        return maxNumRounds
    }

    fun getCurrentRound(): Int {
        return currentRound
    }

    fun getCurrentThrow(): Int {
        return currentThrow
    }

    fun checkUsedLevel(level: String): Boolean {
        var result = true
        usedLevels.forEach {
            if (level == it) {
                result = false
            }
        }
        return result
    }

    fun setChosenLevel(level: String) {
        usedLevels.add(level)
        when (level) {
            "Low" -> {chosenLevel = 0}
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
//            roundIsOver = false
            updateStep()
            return
        }

        currentThrow += 1
        generateNewDice()

        if (currentThrow == 3) {
//            roundIsOver = true
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
                historyScores.add(Score(dice, sum, currentRound))
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
                historyScores.add(Score(dice, sum, currentRound))
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(diceList)
        parcel.writeInt(currentThrow)
        parcel.writeInt(currentRound)
        parcel.writeInt(maxNumRounds)
//        parcel.writeByte(if (roundIsOver) 1 else 0)
        parcel.writeInt(currentStep)
        parcel.writeInt(chosenLevel)
        parcel.writeInt(totalPoints)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }

}