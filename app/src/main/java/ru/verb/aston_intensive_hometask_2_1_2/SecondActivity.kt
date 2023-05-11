package ru.verb.aston_intensive_hometask_2_1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.verb.aston_intensive_hometask_2_1_2.MainActivity.Companion.COUNT_KEY
import ru.verb.aston_intensive_hometask_2_1_2.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also { setContentView(it.root) }
        intent.getIntExtra(COUNT_KEY, 0).apply {
            binding.textCount.text = this.toString()
        }
    }
}