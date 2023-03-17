package cookbook.start

import cookbook.adapter.regler.GerichtRegler
import cookbook.applikation.GerichteVerwalter
import cookbook.plugin.MainFrame
import cookbook.plugin.datenbasis.JSONEntityController

fun main(){
    val verwalter = JSONEntityController()
    val verwaltung = GerichteVerwalter(verwalter)
    val gerichteVerwalter = GerichtRegler(verwaltung)
    MainFrame(gerichteVerwalter)
}
