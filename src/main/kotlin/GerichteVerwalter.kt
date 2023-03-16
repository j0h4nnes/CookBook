

class GerichteVerwalter {
    private var verwalter: EntityController

    constructor(verwalter: EntityController) {
        this.verwalter = verwalter
    }

    fun loescheGericht(id: Int): Boolean {
        return verwalter.loesche(id)
    }

    fun aendereGericht(gericht: Gericht, id: Int): Gericht {
        return verwalter.aendere(gericht, id)
    }

    fun erstelleGericht(gericht: Gericht): Gericht {
        return verwalter.erstelle(gericht)
    }

    fun holeAlle(): List<Gericht> {
        return verwalter.holeAlle()
    }
}