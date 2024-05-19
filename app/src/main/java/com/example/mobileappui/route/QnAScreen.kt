package com.example.mobileappui.route

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileappui.R
import com.example.mobileappui.dtos.common.PaginatedDataDto
import com.example.mobileappui.dtos.question.QuestionDto
import com.example.mobileappui.retrofit.ApiClient
import com.example.mobileappui.services.question.QuestionService
import retrofit2.Call
import retrofit2.Response

class QnAScreen : Fragment() {
    private val questionService: QuestionService = ApiClient.questionService
    var currentQuestionIndex = 0
    var correctCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.qna_screen, container, false)
        view.findViewById<Button>(R.id.back).setOnClickListener {
            returnToMenu()
        }

        questionService.getAllQuestion(1)
            .enqueue(object : retrofit2.Callback<PaginatedDataDto<QuestionDto>> {
                @SuppressLint("MissingInflatedId", "CutPasteId")
                override fun onResponse(
                    call: Call<PaginatedDataDto<QuestionDto>>,
                    response: Response<PaginatedDataDto<QuestionDto>>
                ) {
                    if (response.isSuccessful) {
                        val questions = response.body()?.data
                        updateUIWithQuestion(view, questions!![currentQuestionIndex])

                        view.findViewById<Button>(R.id.next_question).setOnClickListener {
                            view.findViewById<LinearLayout>(R.id.result).visibility = View.GONE
                            currentQuestionIndex++
                            if (currentQuestionIndex < questions.size) {
                                updateUIWithQuestion(view, questions[currentQuestionIndex])
                                if (currentQuestionIndex == questions.size - 1) {
                                    view.findViewById<Button>(R.id.next_question).text = "Đã hiểu"
                                }
                            } else {
                                // Xử lý khi hết câu hỏi
                                view.findViewById<TextView>(R.id.score).text =
                                    "$correctCount/10 câu"
                                view.findViewById<LinearLayout>(R.id.scoreLayout).visibility =
                                    View.VISIBLE
                                view.findViewById<LinearLayout>(R.id.result).visibility = View.GONE

                                view.findViewById<ImageButton>(R.id.exit).setOnClickListener {
                                    returnToMenu()
                                }
                                view.findViewById<ImageButton>(R.id.restart).setOnClickListener {
                                    currentQuestionIndex = 0
                                    correctCount = 0
                                    updateUIWithQuestion(view, questions[currentQuestionIndex])
                                    view.findViewById<LinearLayout>(R.id.scoreLayout).visibility =
                                        View.GONE
                                }
                                view.findViewById<ImageButton>(R.id.continueQuiz)
                                    .setOnClickListener {}
                            }
                        }
                    } else {
                        Log.d("question", response.errorBody().toString())
                        Log.d("question", "error")
                    }
                }

                override fun onFailure(call: Call<PaginatedDataDto<QuestionDto>>, t: Throwable) {
                    Log.d("question", t.message.toString())
                }
            })
        return view
    }

    private fun returnToMenu() {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, Menu())
        fragmentTransaction.commit()
    }

    private fun updateUIWithQuestion(view: View, question: QuestionDto) {
        val correctAnswer = question.correctAnswer
        view.findViewById<TextView>(R.id.question).text = question.question
        val buttonIds =
            arrayOf(R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4)
        val answers = arrayOf(
            question.answerId.answer1,
            question.answerId.answer2,
            question.answerId.answer3,
            question.answerId.answer4
        )
        for (i in buttonIds.indices) {
            val button = view.findViewById<Button>(buttonIds[i])
            button.text = answers[i]
        }

        val buttons = listOf(
            view.findViewById(R.id.answer1),
            view.findViewById(R.id.answer2),
            view.findViewById(R.id.answer3),
            view.findViewById<Button>(R.id.answer4)
        )
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val result = view.findViewById<LinearLayout>(R.id.result)
                val resultText = view.findViewById<TextView>(R.id.result_text)
                if (button.text == correctAnswer) {
                    correctCount++
                    resultText.text = "Chính xác!!!"
                    resultText.backgroundTintList =
                        resources.getColorStateList(R.color.green)
                } else {
                    resultText.text = "Sai rồi!!!"
                    resultText.backgroundTintList =
                        resources.getColorStateList(R.color.red)
                }
                result.visibility = View.VISIBLE
                buttons.forEach { it.isEnabled = false }
            }
        }
        buttons.forEach { it.isEnabled = true }
    }
}