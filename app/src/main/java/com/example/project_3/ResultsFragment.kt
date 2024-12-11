package com.example.project_3

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

class ResultsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val finalWPMTextView: TextView = view.findViewById(R.id.finalWPMTextView)
        val highestWPMTextView: TextView = view.findViewById(R.id.highestWPMTextView)
        val animalPicture: ImageView = view.findViewById(R.id.imageView2)
        val ratingTextView2: TextView = view.findViewById(R.id.ratingTextView2)
        val returnToStartButton: Button = view.findViewById(R.id.returnToStartButton)
        val activity: MainActivity = context as MainActivity
        val finalWPM: Double = activity.getWPM()
        val highestWPM: Double = activity.getHighestWPM()

        // URL's
        val sloth: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7hXhCf2cZ_SbVluQXGzTeZNme_xFvClcejQ&s"
        val snail: String = "https://static-00.iconduck.com/assets.00/snail-emoji-2048x1982-zb5axiza.png"
        val dog: String = "https://images.emojiterra.com/google/android-11/512px/1f415.png"
        val horse: String = "https://static-00.iconduck.com/assets.00/horse-emoji-1024x923-i9z7uhgk.png"
        val cheetah: String = "https://static-00.iconduck.com/assets.00/leopard-emoji-512x387-29ctp6y7.png"
        val falcon: String = "https://attic.sh/8tshkmvfvbjyh944vo31fnokqllo"
        val hummingbird: String = "https://static.tiktokemoji.com/202411/13/CqnXrUl4.webp"

        finalWPMTextView.text = String.format("%.2f", finalWPM)
        highestWPMTextView.text = String.format("%.2f", highestWPM)

        when {
            finalWPM in 0.0..20.0 -> {
                Glide.with(this).load(sloth).into(animalPicture)
                ratingTextView2.text = "Super Slow"
                ratingTextView2.setTextColor(Color.parseColor("#FF0000"))
            }
            finalWPM in 21.0..40.0 -> {
                Glide.with(this).load(snail).into(animalPicture)
                ratingTextView2.text = "Slow"
                ratingTextView2.setTextColor(Color.parseColor("#FFA500"))
            }
            finalWPM in 41.0..60.0 -> {
                Glide.with(this).load(dog).into(animalPicture)
                ratingTextView2.text = "Decent"
                ratingTextView2.setTextColor(Color.parseColor("#FDDA0D"))
            }
            finalWPM in 61.0..80.0 -> {
                Glide.with(this).load(horse).into(animalPicture)
                ratingTextView2.text = "Average"
                ratingTextView2.setTextColor(Color.parseColor("#008000"))
            }
            finalWPM in 81.0..100.0 -> {
                Glide.with(this).load(cheetah).into(animalPicture)
                ratingTextView2.text = "Fast"
                ratingTextView2.setTextColor(Color.parseColor("#0000FF"))
            }
            finalWPM in 101.0..130.0 -> {
                Glide.with(this).load(falcon).into(animalPicture)
                ratingTextView2.text = "Super Fast!"
                ratingTextView2.setTextColor(Color.parseColor("#4B0082"))
            }
            finalWPM >= 131.0 -> {
                Glide.with(this).load(hummingbird).into(animalPicture)
                ratingTextView2.text = "ULTRA FAST!"
                ratingTextView2.setTextColor(Color.parseColor("#EE82EE"))
            }
        }

        returnToStartButton.setOnClickListener {
            val navControl: NavController = view.findNavController()
            navControl.navigate(R.id.action_resultsFragment_to_mainFragment)
        }
    }
}