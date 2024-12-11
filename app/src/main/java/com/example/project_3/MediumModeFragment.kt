package com.example.project_3

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
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MediumModeFragment : Fragment() {

    var seconds = 0
    var minutes = 0
    lateinit var timerTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medium_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var chars: Int = 0;
        var words: Double = 0.0
        var wpm: Double = 0.0
        var active: Boolean = true
        var start: Boolean = false
        timerTextView = view.findViewById(R.id.timeTextView1)
        val wordsTextView: TextView = view.findViewById(R.id.wordsTextView1)
        val wpmTextView: TextView = view.findViewById(R.id.wpmTextView1)
        val mediumQuoteTextView: TextView = view.findViewById(R.id.mediumModeQuote)
        val mediumEditText: EditText = view.findViewById(R.id.mediumEditText)
        val activity: MainActivity = context as MainActivity
        val mediumQuotes: List<String> = listOf("Just because something bears the aspect of the inevitable one should not, therefore, go along willingly with it.",
            "I'm on the top of the world looking down on creation and the only explanation I can find is the love that I've found ever since you've been around. Your love's put me at the top of the world",
            "We fell in love, we had our fun, you always had enough dreams for both of us. I wonder what would come of us if we could trade these ends for beginnings.",
            "I don't care if they like me. I didn't come here to make friends. I don't even care if they respect me. I know who I am. I've got enough respect for myself. I do not want them to beat me.",
            "But I would walk five hundred miles and I would walk five hundred more, just to be the man who walks a thousand miles to fall down at your door.")
        val chosenQuote = mediumQuotes[(0..4).random()]
        mediumQuoteTextView.text = chosenQuote
        // Formula for WPM: (# of chars / 5) / Total Time in Minutes

        val handler: Handler = Handler(Looper.getMainLooper())
        var runnable: Runnable = Runnable { }

        mediumEditText.addTextChangedListener(object: TextWatcher {
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
                        mediumEditText.setTextColor(Color.parseColor("#FF0000"))
                    } else if (currentLength == chosenQuote.length) {
                        runnable = Runnable { }
                        mediumEditText.setTextColor(Color.parseColor("#000000"))
                        mediumEditText.isFocusable = false
                        mediumEditText.isFocusableInTouchMode = false
                        mediumEditText.isEnabled = false

                        // move onto next final screen & save data
                        activity.setWPM(wpm)
                        if (wpm > activity.getHighestWPM()) {
                            activity.setHighestWPM(wpm)
                        }
                        val navController: NavController = view.findNavController()
                        navController.navigate(R.id.action_mediumModeFragment_to_resultsFragment)
                    } else {
                        active = true
                        mediumEditText.setTextColor(Color.parseColor("#000000"))
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
