package cookbook.kern

class Gericht : Entitaet {

    val rezept: String
    val zubereitungszeit: Zubereitungszeit
    val schwierigkeitsgrad: Schwierigkeitsgrad
    val portionen: Int
    val kcal: Int
    val zutaten: List<Zutat>
    val zubereitung: String
    val bildPfad: String

    constructor(id:Int, rezept:String, zubereitungszeit: Zubereitungszeit, schwierigkeitsgrad:Schwierigkeitsgrad, portionen:Int, kcal:Int, zutaten:List<Zutat>, zubereitung:String, bildPfad: String):super(id){
        this.rezept = rezept
        this.zubereitungszeit = zubereitungszeit
        this.schwierigkeitsgrad = schwierigkeitsgrad
        this.portionen = portionen
        this.kcal = kcal
        this.zutaten = zutaten
        this.zubereitung = zubereitung
        this.bildPfad = bildPfad
    }

    constructor(rezept:String, zubereitungszeit: Zubereitungszeit, schwierigkeitsgrad:Schwierigkeitsgrad, portionen:Int, kcal:Int,
                zutaten:List<Zutat>, zubereitung:String, bildPfad: String):super(){
        this.rezept = rezept
        this.zubereitungszeit = zubereitungszeit
        this.schwierigkeitsgrad = schwierigkeitsgrad
        this.portionen = portionen
        this.kcal = kcal
        this.zutaten = zutaten
        this.zubereitung = zubereitung
        this.bildPfad = bildPfad
    }

    constructor():super(){
        this.rezept = ""
        this.zubereitungszeit = Zubereitungszeit(0, Zeiteinheit.Minuten)
        this.schwierigkeitsgrad = Schwierigkeitsgrad.Einfach
        this.portionen = 0
        this.kcal = 0
        this.zutaten = emptyList()
        this.zubereitung = ""
        this.bildPfad = "noPicture.png"
    }

    override fun toString(): String {
        return rezept
    }
}