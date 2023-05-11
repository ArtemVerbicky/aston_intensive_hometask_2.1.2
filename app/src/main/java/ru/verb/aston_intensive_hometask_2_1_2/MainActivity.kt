package ru.verb.aston_intensive_hometask_2_1_2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ru.verb.aston_intensive_hometask_2_1_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var state: State

    companion object {
        const val STATE_KEY = "state_key"
        const val COUNT_KEY = "count_key"
    }


    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        state = savedInstanceState?.getParcelable(STATE_KEY, State::class.java) ?: State(
            count = 0,
            countBackgroundColor = Color.MAGENTA,
            zeroBackgroundColor = Color.GRAY
        )

        with(binding) {
            showCount.text = state.count.toString()
            buttonZero.setBackgroundColor(state.zeroBackgroundColor)
            buttonCount.setBackgroundColor(state.countBackgroundColor)

            buttonToast.setOnClickListener {
                showToast()
                Intent(this@MainActivity, SecondActivity::class.java).apply {
                    putExtra(COUNT_KEY, state.count)
                    startActivity(this)
                }
            }
            buttonCount.setOnClickListener {
                countUp()
            }
            buttonZero.setOnClickListener {
                setZero()
            }
        }
    }

    private fun showToast() {
        Toast.makeText(this, R.string.toast_message, Toast.LENGTH_SHORT)
            .show()
    }

    private fun countUp() {
        state = state.copy(count = state.count + 1)
        renderState(state.count)
    }

    private fun setZero() {
        state = state.copy(count = 0)
        renderState(state.count)
    }

    private fun renderState(count: Int) {
        binding.showCount.text = count.toString()
        when {
            count == 0 -> {
                binding.buttonCount.setBackgroundColor(Color.MAGENTA)
                binding.buttonZero.setBackgroundColor(Color.GRAY)
                state = state.copy(
                    countBackgroundColor = Color.MAGENTA,
                    zeroBackgroundColor = Color.GRAY
                )
            }
            count % 2 == 0 -> {
                binding.buttonCount.setBackgroundColor(Color.MAGENTA)
                binding.buttonZero.setBackgroundColor(Color.YELLOW)
                state = state.copy(
                    countBackgroundColor = Color.MAGENTA,
                    zeroBackgroundColor = Color.YELLOW
                )
            }
            else -> {
                binding.buttonCount.setBackgroundColor(Color.GREEN)
                binding.buttonZero.setBackgroundColor(Color.BLUE)
                state = state.copy(
                    countBackgroundColor = Color.GREEN,
                    zeroBackgroundColor = Color.BLUE
                )
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(STATE_KEY, state)
        super.onSaveInstanceState(outState)
    }
}


data class State(
    val count: Int,
    val zeroBackgroundColor: Int,
    val countBackgroundColor: Int
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeInt(zeroBackgroundColor)
        parcel.writeInt(countBackgroundColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<State> {
        override fun createFromParcel(parcel: Parcel): State {
            return State(parcel)
        }

        override fun newArray(size: Int): Array<State?> {
            return arrayOfNulls(size)
        }
    }
}