package com.polytech.amusees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.polytech.amusees.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var animFadeout: Animation
    private lateinit var animFadeout2: Animation
    private lateinit var animZoominFadeout: Animation
    private lateinit var animFadein: Animation

    //pour empêcher de recliquer sur le bouton et reset l'animation
    private var animationStarted: Boolean = false

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

        animFadein = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fade_in)

        animFadeout2 = AnimationUtils.loadAnimation(
            this.context,
            R.anim.fade_out)

        animZoominFadeout = AnimationUtils.loadAnimation(
            this.context,
            R.anim.zoom_in_fade_out)

        binding.btEnter.setOnClickListener {
            if (!animationStarted) {
                bt_enter.startAnimation(animFadeout)
                background.startAnimation(animZoominFadeout)
                logo.startAnimation(animFadein)

                animationStarted = true
            }
        }

        animZoominFadeout.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
//                //
            }

            override fun onAnimationRepeat(p0: Animation?) {
//                //
            }

            override fun onAnimationEnd(p0: Animation?) {
                Thread.sleep(1000)
                logo.startAnimation(animFadeout2)
            }
        })

        animFadeout2.setAnimationListener(object : Animation.AnimationListener {
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
        animationStarted = false
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_loginFragment)
    }
}