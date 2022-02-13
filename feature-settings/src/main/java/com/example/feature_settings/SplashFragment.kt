package com.example.feature_settings

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import java.util.*

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val handler = Handler()
        handler.postDelayed({
            Navigation.findNavController(view = view).navigate(R.id.navigateToLoginFragment)
        }, 1000)

        return view
    }
}