enum class Schwierigkeitsgrad {
    Einfach,
    Moderat,
    Anspruchsvoll,
    Profi;
    companion object {
        fun fromString(schwierigkeitsgrad: String): Schwierigkeitsgrad {
            return values().first { it.name.equals(schwierigkeitsgrad, ignoreCase = true) }
        }
    }
}