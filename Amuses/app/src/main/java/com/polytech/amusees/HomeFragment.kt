package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.polytech.amusees.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var animFadeout: Animation
    private lateinit var animZoominFadeout: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this


        binding.apply {
            btEnter.text = getString(R.string.enter)
        }

        animFadeout = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fade_out)

        animZoominFadeout = AnimationUtils.loadAnimation(
            this.context,
            R.anim.zoom_in_fade_out)

        binding.btEnter.setOnClickListener {
            bt_enter.startAnimation(animFadeout)
            background.startAnimation(animZoominFadeout)
        }

        animZoominFadeout.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
//                //
            }

            override fun onAnimationRepeat(p0: Animation?) {
//                //
            }

            override fun onAnimationEnd(p0: Animation?) {
                enter(view)
            }
        })

        return binding.root
    }

    private fun enter(view: View?) {
        Toast.makeText(activity, "Connexion !", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_loginFragment)
    }
}