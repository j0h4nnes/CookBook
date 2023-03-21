package cookbook.kern

final class Zutat {
    private val name: String
    private val einheit: Einheit
    private val anzahl: Double

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

    fun getAnzahl(): Double {
        return anzahl
    }

    fun getEinheit(): Einheit {
        return einheit
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}