package com.example.quizapp.consts

import com.example.quizapp.R
import com.example.quizapp.models.Question

object QuestionsList {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"


    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // 1
        val que1 = Question(
            1, "What product name does this logo belong to? ",
            R.drawable.google_slides,
            "YouTube", "Slides",
            "Android", "Fit", 2
        )

        questionsList.add(que1)

        // 2
        val que2 = Question(
            2, "What product name does this logo belong to?",
            R.drawable.stadia,
            "Contacts", "Forms",
            "Stadia", "News", 3
        )

        questionsList.add(que2)

        // 3
        val que3 = Question(
            3, "What product name does this logo belong to?",
            R.drawable.podcast,
            "Podcasts", "Messages",
            "Voice", "Keep", 1
        )

        questionsList.add(que3)

        // 4
        val que4 = Question(
            4, "What product name does this logo belong to?",
            R.drawable.wear_os,
            "Google One", "Google Play",
            "Play Protect", "Wear OS By Google", 4
        )

        questionsList.add(que4)

        // 5
        val que5 = Question(
            5, "What product name does this logo belong to?",
            R.drawable.shopping_logo,
            "News", "Travel",
            "Google Shopping", "Location", 3
        )

        questionsList.add(que5)

        // 6
        val que6 = Question(
            6, "What product name does this logo belong to?",
            R.drawable.ic_flag_of_germany,
            "Germany", "Georgia",
            "Greece", "none of these", 1
        )

       // questionsList.add(que6)

        // 7
        val que7 = Question(
            7, "What product name does this logo belong to?",
            R.drawable.ic_flag_of_denmark,
            "Dominica", "Egypt",
            "Denmark", "Ethiopia", 3
        )

       // questionsList.add(que7)

        // 8
        val que8 = Question(
            8, "What product name does this logo belong to?",
            R.drawable.ic_flag_of_india,
            "Ireland", "Iran",
            "Hungary", "India", 4
        )

       // questionsList.add(que8)

        // 9
        val que9 = Question(
            9, "What product name does this logo belong to?",
            R.drawable.ic_flag_of_new_zealand,
            "Australia", "New Zealand",
            "Tuvalu", "United States of America", 2
        )

       // questionsList.add(que9)

        // 10
        val que10 = Question(
            10, "What product name does this logo belong to?",
            R.drawable.ic_flag_of_kuwait,
            "Kuwait", "Jordan",
            "Sudan", "Palestine", 1
        )

       // questionsList.add(que10)

        return questionsList
    }
    // END
}
// END