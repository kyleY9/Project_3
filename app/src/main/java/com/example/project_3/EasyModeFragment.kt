package com.example.project_3

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class EasyModeFragment : Fragment() {

    var seconds = 0
    var minutes = 0
    lateinit var timerTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_easy_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var chars: Int = 0;
        var words: Double = 0.0
        var wpm: Double = 0.0
        var active: Boolean = true
        var start: Boolean = false
        timerTextView = view.findViewById(R.id.timeTextView)
        val wordsTextView: TextView = view.findViewById(R.id.wordsTextView)
        val wpmTextView: TextView = view.findViewById(R.id.wpmTextView)
        val easyQuoteTextView: TextView = view.findViewById(R.id.easyModeQuote)
        val easyEditText: EditText = view.findViewById(R.id.easyEditText)
        val activity: MainActivity = context as MainActivity
        val easyQuotes: List<String> = listOf("You have the power to heal your life, and you need to know that.",
            "They don't know that we know they know we know.",
            "Don't it make you sad to know that life is more than who you are?",
            "Leave something for someone but don't leave someone for something.",
            "The people with ideas have no power and the people with power have no ideas.");
        val chosenQuote = easyQuotes[(0..4).random()]
        easyQuoteTextView.text = chosenQuote
        // Formula for WPM: (# of chars / 5) / Total Time in Minutes

        val handler: Handler = Handler(Looper.getMainLooper())
        var runnable: Runnable = Runnable { }

        easyEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!start) {
                    runnable = Runnable {
                        updateTime()
                        handler.postDelayed(runnable, 1000)
                    }
                    handler.postDelayed(runnable, 1000)
                }
                start = true

                if (s != null) {
                    val currentLength: Int = s.toString().length
                    if (currentLength > chosenQuote.length || chosenQuote.substring(0, currentLength) != s.toString()) {
                        active = false
                        easyEditText.setTextColor(Color.parseColor("#FF0000"))
                    } else if (currentLength == chosenQuote.length) {
                        runnable = Runnable { }
                        easyEditText.setTextColor(Color.parseColor("#000000"))
                        easyEditText.isFocusable = false
                        easyEditText.isFocusableInTouchMode = false
                        easyEditText.isEnabled = false

                        // move onto next final screen & save data
                        activity.setWPM(wpm)
                        if (wpm > activity.getHighestWPM()) {
                            activity.setHighestWPM(wpm)
                        }
                        val navController: NavController = view.findNavController()
                        navController.navigate(R.id.action_easyModeFragment_to_resultsFragment)

                    } else {
                        active = true
                        easyEditText.setTextColor(Color.parseColor("#000000"))
                    }

                    if (active) {
                        chars = s.toString().length
                        words = (chars / 5.0)
                        wordsTextView.text = "# Words: " + String.format("%.2f", words)
                        if (seconds != 0) {
                            wpm = (words / (minutes + (seconds / 60.0)))
                            wpmTextView.text = "WPM: " + String.format("%.2f", wpm)
                        }
                    }
                }
            }
        })
    }

    private fun updateTime() {
        seconds++
        if (seconds >= 60) {
            minutes++
            seconds = 0
        }
        timerTextView.text = "Time: " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
    }
}