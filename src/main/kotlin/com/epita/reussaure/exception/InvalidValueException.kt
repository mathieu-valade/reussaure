package com.epita.reussaure.exception

import com.epita.reussaure.validator.Fault

class InvalidValueException(
        val fault: Fault

) : Exception("Invalid value: ${fault.message}")