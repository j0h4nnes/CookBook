package cookbook.adapter.regler

import cookbook.adapter.beobachter.muster.Observer
import cookbook.adapter.beobachter.muster.Subjekt
import cookbook.applikation.GerichteVerwalter
import cookbook.kern.Gericht

class GerichtRegler : Subjekt {

    private var verwaltung: GerichteVerwalter? = null
    private var observer: MutableList<Observer>? = null

    constructor(verwaltung: GerichteVerwalter) {
        this.observer = ArrayList()
        this.verwaltung = verwaltung
    }

    fun erstelleGericht(gericht: Gericht): Gericht? {
        return this.verwaltung?.erstelleGericht(gericht)
    }

    fun aendereGericht(gericht: Gericht): Gericht? {
        return this.verwaltung?.aendereGericht(gericht, gericht.id)
    }

    fun loescheGericht(id: Int): Boolean? {
        return this.verwaltung?.loescheGericht(id)
    }

    fun hole(id: Int): Gericht?{
        return this.verwaltung?.hole(id)
    }

    fun holeAlle(): List<Gericht>? {
        return this.verwaltung?.holeAlle()
    }

    override fun meldeAn(beobachter: Observer) {
        if (!this.observer?.contains(beobachter)!!) {
            this.observer?.add(beobachter)
        }
    }

    override fun meldeAb(beobachter: Observer) {
        if (this.observer?.contains(beobachter) == true) {
            this.observer?.remove(beobachter)
        }
    }

    override fun benachrichtige() {
        for (beobachter in observer!!) {
            beobachter.aktualisiere(this.verwaltung?.holeAlle())
        }
    }
}