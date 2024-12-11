package com.example.project_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val androidImage: ImageView = view.findViewById(R.id.imageView)
        val easyButton: Button = view.findViewById(R.id.easyButton)
        val mediumButton: Button = view.findViewById(R.id.mediumButton)
        val hardButton: Button = view.findViewById(R.id.hardButton)
        val imageURL: String = "https://developer.android.com/static/images/brand/android-head_flat.png"
        Glide.with(this).load(imageURL).into(androidImage) // GLIDE!

        easyButton.setOnClickListener {
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_mainFragment_to_easyModeFragment)
        }
        mediumButton.setOnClickListener {
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_mainFragment_to_mediumModeFragment2)
        }
        hardButton.setOnClickListener {
            val navController: NavController = view.findNavController()
            navController.navigate(R.id.action_mainFragment_to_hardModeFragment3)
        }
    }
}

// https://developer.android.com/static/images/brand/android-head_flat.png
// https://www.clipartmax.com/png/small/294-2949830_cool-android-logo-png.png