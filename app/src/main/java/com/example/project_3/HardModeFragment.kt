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

class HardModeFragment : Fragment() {

    var seconds = 0
    var minutes = 0
    lateinit var timerTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hard_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var chars: Int = 0;
        var words: Double = 0.0
        var wpm: Double = 0.0
        var active: Boolean = true
        var start: Boolean = false
        timerTextView = view.findViewById(R.id.timeTextView2)
        val wordsTextView: TextView = view.findViewById(R.id.wordsTextView2)
        val wpmTextView: TextView = view.findViewById(R.id.wpmTextView2)
        val hardQuoteTextView: TextView = view.findViewById(R.id.hardModeQuote)
        val hardEditText: EditText = view.findViewById(R.id.hardEditText)
        val activity: MainActivity = context as MainActivity
        val hardQuotes: List<String> = listOf("Sometimes, when you're writing a poem - or a story - your brain gets fixated on a specific point. If you try so hard to make it perfect, then you'll never make any progress. Just force yourself to get something down on the paper, and tidy it up later! Another way to think about it is this: if you keep your pen in the same spot for too long, you'll just get a big dark puddle of ink. So just move your hand, and go with the flow!",
            "He thinks he's got free will, but really he's trapped in a maze, in a system. All he can do is consume, he's pursued by demons that are probably just in his own head, and even if he does manage to escape by slipping out one side of the maze, what happens? He comes right back in the other side. People think it's a happy game. It's not a happy game. It's a nightmare world, and the worst thing is, it's real and we live in it. It's all code. If you listen closely, you can hear the numbers.",
            "Frodo and Sam stood as if enchanted. The wind puffed out. The leaves hung silently again on stiff branches. There was another burst of song, and then suddenly, hopping and dancing along the path, there appeared above the reeds an old battered hat with a tall crown and a long blue feather stuck in the band.",
            "Now that it was evening, they encamped by the river of forgetfulness, whose water no container can hold. It is necessary for all to drink a fixed amount of the water, but some do not have the wisdom to keep from drinking more than this amount. As one drinks one becomes forgetful of everything. In the middle of the night when they were asleep there was thunder and an earthquake, and then suddenly just like shooting stars they were borne upward, each in a different direction to his birth.",
            "I used to hate myself. I hated everything about me. My voice, my face, my entire existence. I thought, \"Why was I born at all?\" It felt like the world didn't need me, and I kept convincing myself that was true. But over time, I started to see something different. I saw the people around me-the ones who cared. The ones who were patient with me, even when I pushed them away. And little by little, I began to wonder... if I'm so unnecessary, why do they stay? Why do they keep trying to reach me? Maybe I've been wrong all this time. Maybe I'm here for a reason after all.")
        val chosenQuote = hardQuotes[(0..4).random()]
        hardQuoteTextView.text = chosenQuote
        // Formula for WPM: (# of chars / 5) / Total Time in Minutes

        val handler: Handler = Handler(Looper.getMainLooper())
        var runnable: Runnable = Runnable { }

        hardEditText.addTextChangedListener(object: TextWatcher {
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
                        hardEditText.setTextColor(Color.parseColor("#FF0000"))
                    } else if (currentLength == chosenQuote.length) {
                        runnable = Runnable { }
                        hardEditText.setTextColor(Color.parseColor("#000000"))
                        hardEditText.isFocusable = false
                        hardEditText.isFocusableInTouchMode = false
                        hardEditText.isEnabled = false

                        // move onto next final screen & save data
                        activity.setWPM(wpm)
                        if (wpm > activity.getHighestWPM()) {
                            activity.setHighestWPM(wpm)
                        }
                        val navController: NavController = view.findNavController()
                        navController.navigate(R.id.action_hardModeFragment_to_resultsFragment)
                    } else {
                        active = true
                        hardEditText.setTextColor(Color.parseColor("#000000"))
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