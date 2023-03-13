fun main(){
    val verwalter = JSONEntityController()
    val verwaltung = GerichteVerwalter(verwalter)
    val gerichteVerwalter = GerichtRegler(verwaltung)
    MainFrame(gerichteVerwalter)
}
