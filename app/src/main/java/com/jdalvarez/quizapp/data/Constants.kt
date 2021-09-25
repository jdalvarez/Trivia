package com.jdalvarez.quizapp.data

object Constants {
    fun getQuestions(): ArrayList<Question>{
        val questionList = ArrayList<Question>()
        val que1 = Question("primer tema",
            "Pregunta 1 de lo que sea",
            1, "opcion uno",
            "opcion2",
            "opcion tres",
            "Opcion cuatro y de aca")
        questionList.add(que1)

        val que2 = Question("primer tema",
            "Pregunta 2 de lo nada",
            3, "opcion uno y algo mas",
            "opcion dos desde casa",
            "opcion tres cuando quieras",
            "Opcion cuatro se pudre todo")
        questionList.add(que2)

        val que3 = Question("segundo tema",
            "Pregunta tres de lo no se me ocurre nada",
            4, "opcion uno que nunca toca",
            "opcion ja maica no problem",
            "opcion tres patitos se fueron a nadar",
            "Opcion cuatro y el mas vigilante se quiso quedar")
        questionList.add(que3)

        val que4 = Question("tercer tema",
            "Pregunta no se no llevo la cuenta",
            1, "opcion uno es aburrido",
            "opcion de a dos es mejor",
            "opcion tres dicen ser multitud",
            "Opcion cuatro se armo la fiesta ")
        questionList.add(que4)

        val que5 = Question("primer tomala",
            "Pregunta 5 de lo que pero pa mi esta loco sea",
            1, "opcion uno mas aunque sea chiquito",
            "2 esto esta re verde",
            "opcion tres d enuevo  no",
            "Opcion cuatro y ok me la banco y que")
        questionList.add(que5)

        val que6 = Question("ultimo tomala",
            "Pregunta 6 de lo quey estoy aquie pero pa mi esta loco sea",
            1, "opcion uno y de a poquito",
            "2nos vamos todos dije",
            "opcion tres d nos dormimos",
            "Opcion cuatrohoy en dia nadie se la banca y que")
        questionList.add(que6)
        return questionList
    }
}