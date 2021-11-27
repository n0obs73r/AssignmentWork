package com.example.assignment

import android.util.Patterns

object Utils {

    enum class ValidCodes {Missing, INVALID_MAIL, INVALID_PASSWORD, SUCCESS}

    fun validateInput(email: String?, password: String?): ValidCodes {
        if(email.isNullOrEmpty() or password.isNullOrEmpty()) {
            return ValidCodes.Missing
        }

        val _email = email as String
        val _pass = password as String

        if(!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            return ValidCodes.INVALID_MAIL
        }

        if(_pass.length < 8) {
            return ValidCodes.INVALID_PASSWORD
        }

        return ValidCodes.SUCCESS
    }
}