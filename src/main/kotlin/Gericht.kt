

class Gericht : Entity {

    val rezept: String
    val zubereitungszeit: Zubereitungszeit
    val schwierigkeitsgrad: Schwierigkeitsgrad
    val portionen: Int
    val kcal: Int
    val zutaten: List<Zutat>
    val zubereitung: String
    val bild: String

    constructor(id:Int, rezept:String, zubereitungszeit: Zubereitungszeit, schwierigkeitsgrad:Schwierigkeitsgrad, portionen:Int, kcal:Int, zutaten:List<Zutat>, zubereitung:String, bild: String):super(id){
        this.rezept = rezept
        this.zubereitungszeit = zubereitungszeit
        this.schwierigkeitsgrad = schwierigkeitsgrad
        this.portionen = portionen
        this.kcal = kcal
        this.zutaten = zutaten
        this.zubereitung = zubereitung
        this.bild = bild
    }

    constructor(rezept:String, zubereitungszeit: Zubereitungszeit, schwierigkeitsgrad:Schwierigkeitsgrad, portionen:Int, kcal:Int,
                zutaten:List<Zutat>, zubereitung:String, bild: String):super(){
        this.rezept = rezept
        this.zubereitungszeit = zubereitungszeit
        this.schwierigkeitsgrad = schwierigkeitsgrad
        this.portionen = portionen
        this.kcal = kcal
        this.zutaten = zutaten
        this.zubereitung = zubereitung
        this.bild = bild
    }

    constructor():super(){
        this.rezept = ""
        this.zubereitungszeit = Zubereitungszeit(0, Zeiteinheit.Minuten)
        this.schwierigkeitsgrad = Schwierigkeitsgrad.Einfach
        this.portionen = 0
        this.kcal = 0
        this.zutaten = emptyList()
        this.zubereitung = ""
        this.bild = "noPicture.png"
    }

    override fun toString(): String {
        return rezept
    }
}