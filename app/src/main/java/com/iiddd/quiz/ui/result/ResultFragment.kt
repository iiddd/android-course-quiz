package com.iiddd.quiz.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iiddd.quiz.R
import com.iiddd.quiz.common.Constants
import com.iiddd.quiz.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: ResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUserData()
        setupFinishButton()
    }

    private fun setupUserData() {
        binding.tvResultName.text = viewModel.getUserName()
        binding.tvResultScore.text = resources.getString(
            R.string.result_score,
            viewModel.getUserScore(),
            Constants.QUESTION_COUNT
        )
    }

    private fun setupFinishButton() {
        binding.finishButton.setOnClickListener {
            viewModel.clearUserScore()
            findNavController().navigate(ResultFragmentDirections.goToWelcome())
        }
    }
}