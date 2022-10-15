package com.radreichley.noctal.base.theming

class Color {
    private var argbColorVal: ULong = 0x00000000u

    val argbLongValue: Long
        get() = argbColorVal.toLong()

    companion object {
        private val regex = Regex(
            "^#?([0-9A-F]{2})?([0-9A-F]{2})([0-9A-F]{2})([0-9A-F]{2})$",
            RegexOption.IGNORE_CASE
        )
    }

    constructor(rgb: String) {
        var groups = regex.matchEntire(rgb)?.groupValues ?: throw Exception("Invalid rgb string [$rgb]")
        // NOTE(jpr): group[0] is the entire matched regex. optional non-matches are given as ""
        groups = groups.drop(if (groups[1] == "") 2 else 1)
        val converted = groups.map { it.toUByte(16) }.toMutableList()
        val alpha = if (converted.count() == 4) converted.removeFirst() else 0xFFu

        initialize(converted[0], converted[1], converted[2], alpha)
    }

    constructor(red: UByte, green: UByte, blue: UByte, alpha: UByte = 0xFFu) {
        initialize(red, green, blue, alpha);
    }

    private fun initialize(red: UByte, green: UByte, blue: UByte, alpha: UByte) {
        val a1 = alpha.toString(16)
        val a2 = alpha.toLong().shl(24).toString(16)
        val r1 = red.toString(16)
        val r2 = red.toLong().shl(16).toString(16)
        val g1 = green.toString(16)
        val g2 = green.toLong().shl(18).toString(16)
        val b1 = blue.toString(16)
        val b2 = blue.toLong().toString(16)
        argbColorVal = (alpha.toULong().shl(24) or
                red.toULong().shl(16) or
                green.toULong().shl(8) or
                blue.toULong())
    }
}

public interface ITheme {
    val backgroundColor: Color
    val onBackgroundColor: Color
    val surfaceColor: Color
    val onSurfaceColor: Color
    val errorColor: Color
    val onErrorColor: Color

    val primaryColor: Color
    val onPrimaryColor: Color
    val secondaryColor: Color
    val onSecondaryColor: Color
}
