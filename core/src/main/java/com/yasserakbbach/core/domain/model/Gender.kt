package com.yasserakbbach.core.domain.model

sealed class Gender(val name: String) {
    object Male: Gender(MALE)
    object Female: Gender(FEMALE)

    companion object {
        const val MALE = "male"
        const val FEMALE = "female"

        infix fun fromString(name: String?): Gender =
            when(name) {
                MALE -> Male
                FEMALE -> Female
                else -> throw IllegalArgumentException("Cannot get gender for $name")
            }
    }
}
