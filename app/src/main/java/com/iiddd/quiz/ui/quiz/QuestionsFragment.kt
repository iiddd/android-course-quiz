package com.iiddd.quiz.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iiddd.quiz.common.Constants.QUESTION_COUNT
import com.iiddd.quiz.ui.quiz.view.QuestionView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionsFragment : Fragment() {

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    QuestionView(
                        uiState = viewModel.questionStateFlow.collectAsState(),
                        onSubmit = { answerIndex -> viewModel.onSubmit(answerIndex) },
                        questionsTotal = QUESTION_COUNT,
                        onCompletion = { navigateToResultScreen() }
                    )
                }
            }
        }
    }

    private fun navigateToResultScreen() {
        findNavController().navigate(QuestionsFragmentDirections.goToResults())
    }
}