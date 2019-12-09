package com.epita.reussaure.utils

infix fun Int.timesDo(function: () -> Unit) {
    repeat(this) { function() }
}
