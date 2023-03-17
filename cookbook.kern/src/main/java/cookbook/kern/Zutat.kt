package cookbook.kern

class Zutat {
    private var name: String = ""
    private var einheit: Einheit
    private var anzahl = 0.0

    constructor() : this("", Einheit.Gramm, 0.0)
    constructor(name: String, anzahl: Double) {
        this.name = name
        this.einheit = Einheit.Gramm
        this.anzahl = anzahl
    }

    constructor(name: String, einheit: Einheit, anzahl: Double) {
        this.name = name
        this.anzahl = anzahl
        this.einheit = einheit
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getAnzahl(): Double {
        return anzahl
    }

    fun setAnzahl(anzahl: Double) {
        this.anzahl = anzahl
    }

    fun getEinheit(): Einheit {
        return einheit
    }

    fun setEinheit(einheit: Einheit) {
        this.einheit = einheit
    }
}