interface EntityController {
    fun erstelle(gericht: Gericht): Gericht
    fun loesche(id: Int): Boolean
    fun aendere(gericht: Gericht, id: Int): Gericht
    fun hole(id: Int): Gericht
    fun holeAlle(): List<Gericht>
}