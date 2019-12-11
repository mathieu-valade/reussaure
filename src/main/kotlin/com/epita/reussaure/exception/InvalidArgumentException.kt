package com.epita.reussaure.exception

import com.epita.reussaure.validator.Fault

class InvalidArgumentException(
        val field: String,
        val fault: Fault
) : Exception("Invalid argument: ${field} ${fault.message}")