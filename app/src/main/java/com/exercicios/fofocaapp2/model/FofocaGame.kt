package com.exercicios.fofocaapp2.model

class FofocaGame(private var score: Int = 0): java.io.Serializable {
    private final var fofocaList = mutableListOf<Fofoca>()
    lateinit var currentFofoca: Fofoca
    private var status = Status.STARTED
    private var currentIndexFofocaList = 0

    fun addFofocaList(fofocaList: List<Fofoca>) {
        fofocaList.forEach { fofoca -> addFofoca(fofoca) }
    }

    fun addFofoca(fofoca: Fofoca) {
        this.fofocaList.add(fofoca)
    }

    fun getFofocaList(): MutableList<Fofoca> {
        return fofocaList.toMutableList()
    }

    fun getAmountOfFofocas(): Int {
        return fofocaList.size
    }

    fun getScore(): Int {
        return score
    }

    fun startGame() {
        if(fofocaList.isNotEmpty()) {
            fofocaList.shuffle()
            setNextFofoca()
        }
    }

    fun setNextFofoca() {
        if(currentIndexFofocaList < fofocaList.size) {
            currentFofoca = fofocaList[currentIndexFofocaList++]
        }
    }


    fun timeOut() {
        this.wrongAnswer()
    }

    fun guessFofocaVeracity(guess: Boolean) {
        status =
        if (currentFofoca.guessVeracity(guess)) {
            rightAnswer()
        } else {
            wrongAnswer()
        }
    }

    private fun rightAnswer(): Status {
        score++
        return Status.RIGHT_ANSWER
    }

    private fun wrongAnswer(): Status {
        score--
        return Status.WRONG_ANSWER
    }

    fun getStatusDescription(): String {
        return status.description
    }

    fun gameOver(): Boolean {
        return this.currentIndexFofocaList == fofocaList.size
    }

    private enum class Status(val description: String) {
        STARTED("Game Started"),
        RIGHT_ANSWER("Right Answer"),
        WRONG_ANSWER("Wrong Answer"),
        FINISH("Game Finished")
    }
}