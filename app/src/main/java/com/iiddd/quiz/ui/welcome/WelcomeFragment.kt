package com.iiddd.quiz.ui.welcome

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iiddd.quiz.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNameInput()
    }

    private fun setupNameInput() {
        binding.etWelcomeName.setText(viewModel.getUserName())
        binding.etWelcomeName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && viewModel.getIsDefault()) {
                binding.etWelcomeName.setText("")
            }
        }

        binding.btnWelcomeStart.setOnClickListener {
            if (binding.etWelcomeName.text!!.isEmpty()) {
                binding.etWelcomeName.hideKeyboard()
                Toast.makeText(
                    context,
                    "Please enter your name",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                viewModel.saveUsername(binding.etWelcomeName.text.toString())
                viewModel.setIsDefault(false)
                binding.etWelcomeName.hideKeyboard()
                findNavController().navigate(WelcomeFragmentDirections.goToQuizQuestions())
            }
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}