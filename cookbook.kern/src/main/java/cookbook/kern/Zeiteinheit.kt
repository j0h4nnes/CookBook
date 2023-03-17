package cookbook.kern

enum class Zeiteinheit{
    Sekunden,
    Minuten,
    Stunden;

    companion object {
        fun fromString(zeiteinheit: String): Zeiteinheit {
            return values().first { it.name.equals(zeiteinheit, ignoreCase = true) }
        }
    }
}