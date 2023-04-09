package cookbook.adapter.beobachter.muster

import cookbook.kern.Gericht

interface Observer {
    fun aktualisiere(gerichte: List<Gericht>?)
}