package com.example.quizapp.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quizapp.R
import com.example.quizapp.app_compat.MainFragment
import com.example.quizapp.consts.QuestionsList
import com.example.quizapp.databinding.FragmentGameBoardBinding
import com.example.quizapp.models.Question


class GameBoardFragment : MainFragment(), View.OnClickListener {
    private lateinit var bing: FragmentGameBoardBinding

    companion object {
        private var mCurrentPosition: Int = 1
        private lateinit var mQuestionList: ArrayList<Question>
        private var mSelectedOptionPosition: Int = 0
        private var mSelectedButtons: Int = 0
        private var submitClicked: Boolean = false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        bundle: Bundle?
    ): View {
        bing = FragmentGameBoardBinding.inflate(layoutInflater)
        return bing.root
    }

    override fun onViewCreated(view: View, bundle: Bundle?) {
        super.onViewCreated(view, bundle)

        mQuestionList = QuestionsList.getQuestions()

        bing.tvOptionOne.setOnClickListener(this)
        bing.tvOptionTwo.setOnClickListener(this)
        bing.tvOptionThree.setOnClickListener(this)
        bing.tvOptionFour.setOnClickListener(this)
        bing.btnSubmit.setOnClickListener(this)

        //Create View
        setQuestion()

        if (bundle != null) {
            if (bundle.getInt("btnSelected") != 0 && bundle.getInt("btnPosition") != 0) {
                val text: TextView = bing.root.findViewById(bundle.getInt("btnSelected"))
                text.let {
                    console(it)
                    selectedOptionView(it, bundle.getInt("btnPosition"))
                }
            } else {
                if (bundle.getBoolean("submitClicked")) {
                    console("Log")
                    val question = mQuestionList?.get(mCurrentPosition - 1)

//                    answerView(2, R.drawable.wrong_option_border_bg)
//                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size) {
                        bing.btnSubmit.text = "FINISH"
                    } else {
                        bing.btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                }
            }
        }
    }

    private fun setQuestion() {
        defaultOptionsView()
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        bing.ivImage.setImageResource(question.image)
        bing.progressBar.progress = mCurrentPosition
        bing.progressBar.max = mQuestionList.size
        bing.tvProgress.text = "$mCurrentPosition/${mQuestionList.size}"
        bing.tvQuestion.text = question.question
        bing.tvOptionOne.text = question.optionOne
        bing.tvOptionTwo.text = question.optionTwo
        bing.tvOptionThree.text = question.optionThere
        bing.tvOptionFour.text = question.optionFour

        if (mCurrentPosition == mQuestionList!!.size) {
            bing.btnSubmit?.text = "Finish"
        } else {
            bing.btnSubmit?.text = "Submit"
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        bing.tvOptionOne.let {
            options.add(0, it)
        }
        bing.tvOptionTwo.let {
            options.add(1, it)
        }
        bing.tvOptionThree.let {
            options.add(2, it)
        }
        bing.tvOptionFour.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.default_option_border_bg
                )
            }
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOption: Int) {
        defaultOptionsView()

        mSelectedOptionPosition = selectedOption
        mSelectedButtons = tv.id

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.selected_option_background_bg
            )
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                bing.tvOptionOne.let {
                    selectedOptionView(it, 1)
                }
            }

            R.id.tv_option_two -> {
                bing.tvOptionTwo.let {
                    selectedOptionView(it, 2)
                }
            }

            R.id.tv_option_three -> {
                bing.tvOptionThree.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_four -> {
                bing.tvOptionFour.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0) { // no buttons selected
                    mCurrentPosition++
                    submitClicked = false
                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            showToastMessageLong("Congrats You Made It")
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition - 1)

                    if (question!!.correctAnswer != mSelectedOptionPosition) {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                        submitClicked = true
                    }

                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionList!!.size) {
                        bing.btnSubmit.text = "FINISH"
                    } else {
                        bing.btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition = 0
                    mSelectedButtons = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                bing.tvOptionOne?.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        drawableView
                    )
                }
            }
            2 -> {
                bing.tvOptionTwo?.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        drawableView
                    )
                }
            }
            3 -> {
                bing.tvOptionThree?.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        drawableView
                    )
                }
            }
            4 -> {
                bing.tvOptionFour?.background = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        drawableView
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("btnSelected", mSelectedButtons)
        outState.putInt("btnPosition", mSelectedOptionPosition)
        outState.putBoolean("submitClicked", submitClicked)
    }
}