package com.exercicios.fofocaapp2.model

class Fofoca (var description: String, var veracity: Boolean): java.io.Serializable {
    fun guessVeracity(guess: Boolean): Boolean {
        return veracity == guess
    }
}