package se.umu.cs.id19abn.thirty

import android.os.Parcel
import android.os.Parcelable

// Data class to represent a dice in the game
// Implements Parcelable to be able to send object between activities (in intents)
data class Dice(var value: Int, var locked: Boolean, var counted: Boolean ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
        parcel.writeByte(if (locked) 1 else 0)
        parcel.writeByte(if (counted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Dice> {
        override fun createFromParcel(parcel: Parcel): Dice {
            return Dice(parcel)
        }

        override fun newArray(size: Int): Array<Dice?> {
            return arrayOfNulls(size)
        }
    }
}