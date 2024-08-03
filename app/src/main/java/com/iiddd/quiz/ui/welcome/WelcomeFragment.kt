package com.iiddd.quiz.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iiddd.quiz.ui.welcome.view.WelcomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private val viewModel: WelcomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    WelcomeView(
                        username = viewModel.getUserName(),
                        onStartClick = { onStartClick() },
                        onSaveUserName = { viewModel.saveUsername(it) }
                    )
                }
            }
        }
    }

    private fun onStartClick() {
        viewModel.setIsDefault(false)
        findNavController().navigate(WelcomeFragmentDirections.goToQuizQuestions())
    }
}