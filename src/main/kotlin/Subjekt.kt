interface Subjekt {
    fun meldeAn(beobachter: Observer)

    fun meldeAb(beobachter: Observer)

    fun benachrichtige()
}