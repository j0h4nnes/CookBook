package cookbook.plugin

import cookbook.adapter.regler.GerichtRegler
import cookbook.kern.*
import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionListener
import java.lang.Exception
import javax.swing.*
import javax.swing.border.EmptyBorder

class Formular : JFrame {

    private var btnSpeichern = JButton("Speichern")
    private var btnBildHochladen = JButton("Hochladen von Bildern")
    private var btnZutaten = JButton("Zutaten hinzufügen")

    private var pnlNavigationsleiste = JPanel()
    private var pnlErstellung: JPanel = JPanel()
    private val pnlRezeptname: JPanel = JPanel()
    private val pnlZubereitungszeit: JPanel = JPanel()
    private val pnlZeiteinheit: JPanel = JPanel()
    private val pnlSchwierigkeitsgrad: JPanel = JPanel()
    private val pnlPortion: JPanel = JPanel()
    private val pnlKcal: JPanel = JPanel()
    private val pnlZutaten: JPanel = JPanel()
    private val pnlZubereitung: JPanel = JPanel()

    var tfRezeptname: JTextField = JTextField()
    var tfZubereitungszeit: JTextField = JTextField()
    var tfZeiteinheit: JComboBox<Zeiteinheit> = JComboBox()
    var tfSchwierigkeitsgrad: JComboBox<Schwierigkeitsgrad> = JComboBox()
    var tfPortion: JTextField = JTextField()
    var tfKcal: JTextField = JTextField()
    var lsZutaten: List<Zutat> = emptyList()
    var taZubereitung: JTextArea = JTextArea()

    private var rezept = JLabel("Rezepterstellung")

    private var rezeptname = JLabel("Rezeptname")
    private var zubereitungszeit = JLabel("Zubereitungszeit")
    private var zeiteinheit = JLabel("Zeiteinheit")
    private var schwierigkeitsgrad = JLabel("Schwierigkeitsgrad")
    private var portionen = JLabel("Portionen")
    private var kcal = JLabel("Kcal")
    private var zutaten = JLabel("Zutaten")
    private var zubereitung = JLabel("Zubereitung")

    var bildPfad = "noPicture.png"

    private var gerichtRegler: GerichtRegler
    private var updateGericht: Gericht? = null

    constructor(gerichteRegler:GerichtRegler){
        this.gerichtRegler = gerichteRegler

        // Navigationsleiste konfigurieren
        pnlNavigationsleiste.layout = GridLayout(1, 3)

        // Button Speichern
        btnSpeichern.font = Font("Arial", Font.PLAIN, 20)

        btnSpeichern.addActionListener(ActionListener {
            if (!pruefeEingabenZuGericht(this.tfRezeptname.text,
                    this.tfZubereitungszeit.text, this.tfZeiteinheit.selectedIndex,
                    this.tfSchwierigkeitsgrad.selectedIndex, this.tfPortion.text,
                    this.tfKcal.text, this.lsZutaten, this.taZubereitung.text)){
                JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfüllen!\r\n"+
                        "Tipp: Die Felder Zubereitungszeit, Portion und Kcal müssen Zahlenwerte enthalten.\r\n"+
                        "Es muss eine Zutat hinzugefügt worden sein!")
                return@ActionListener
            }

            if (updateGericht != null) {
                gerichtRegler.loescheGericht(updateGericht!!.id)
            }

            val gericht = Gericht(
                this.tfRezeptname.text, Zubereitungszeit(
                    this.tfZubereitungszeit.text.toInt(),
                    this.tfZeiteinheit.getItemAt(this.tfZeiteinheit.selectedIndex)
                ),
                this.tfSchwierigkeitsgrad.getItemAt(this.tfSchwierigkeitsgrad.selectedIndex), this.tfPortion.text.toInt(),
                this.tfKcal.text.toInt(), this.lsZutaten, this.taZubereitung.text, this.bildPfad
            )

            val erstelltesGericht = erstelleGerichtJSON(gericht)

            if (erstelltesGericht != null) {
                JOptionPane.showMessageDialog(this, erstelltesGericht.rezept + " wurde erfolgreich erstellt.")
                this.gerichtRegler.benachrichtige()
            }
            this.dispose()

            val gerichte = this.gerichtRegler.holeAlle()
            if (gerichte != null) {
                GerichteUebersicht(this.gerichtRegler)
            }
        })

        // Label Rezepterstellung
        rezept.font = Font("Arial", Font.PLAIN, 30)
        rezept.horizontalAlignment = SwingConstants.CENTER

        // Label Logo Rezepte-Buch für Rezeperstellung
        btnBildHochladen.font = Font("Arial", Font.PLAIN, 20)

        btnBildHochladen.addActionListener {
            val chooser = JFileChooser()
            val result = chooser.showOpenDialog(this)
            if (result == JFileChooser.APPROVE_OPTION) {
                val file = chooser.selectedFile
                println("Selected file: ${file.name}")
                bildPfad = file.absolutePath
            }
        }

        // Zum Panel Navigationsleiste hinzufügen
        pnlNavigationsleiste.add(btnSpeichern)
        pnlNavigationsleiste.add(rezept)
        pnlNavigationsleiste.add(btnBildHochladen)

        // Panel Rezeptname
        pnlRezeptname.layout = GridLayout(2, 1)

        rezeptname.font = Font("Arial", Font.BOLD, 20)
        tfRezeptname = JTextField()

        pnlRezeptname.add(rezeptname)
        pnlRezeptname.add(tfRezeptname)

        // Panel Zubereitungszeit
        pnlZubereitungszeit.layout = GridLayout(2,1)
        zubereitungszeit.font = Font("Arial", Font.BOLD, 20)
        tfZubereitungszeit = JTextField()
        pnlZubereitungszeit.add(zubereitungszeit)
        pnlZubereitungszeit.add(tfZubereitungszeit)

        pnlZeiteinheit.layout = GridLayout(2, 1)
        zeiteinheit.font = Font("Arial", Font.BOLD, 20)
        tfZeiteinheit = JComboBox(Zeiteinheit.values())
        pnlZeiteinheit.add(zeiteinheit)
        pnlZeiteinheit.add(tfZeiteinheit)

        // Panel Schwierigkeitsgrad
        pnlSchwierigkeitsgrad.layout = GridLayout(2,1)

        schwierigkeitsgrad.font = Font("Arial", Font.BOLD, 20)
        tfSchwierigkeitsgrad = JComboBox(Schwierigkeitsgrad.values())

        pnlSchwierigkeitsgrad.add(schwierigkeitsgrad)
        pnlSchwierigkeitsgrad.add(tfSchwierigkeitsgrad)

        // Panel Portionen
        pnlPortion.layout = GridLayout(2,1)

        portionen.font = Font("Arial", Font.BOLD, 20)
        tfPortion = JTextField()

        pnlPortion.add(portionen)
        pnlPortion.add(tfPortion)

        // Panel Kcal
        pnlKcal.layout = GridLayout(2,1)

        kcal.font = Font("Arial", Font.BOLD, 20)
        tfKcal = JTextField()

        pnlKcal.add(kcal)
        pnlKcal.add(tfKcal)

        // Panel Zutaten
        pnlZutaten.layout = GridLayout(2,1)
        zutaten.font = Font("Arial", Font.BOLD, 20)

        btnZutaten.font = Font("Arial", Font.PLAIN,18)
        btnZutaten.addActionListener {
            val zutatenFenster = ZutatenFenster(this, this.gerichtRegler)
            lsZutaten = zutatenFenster.getZutaten()
        }

        pnlZutaten.add(zutaten)
        pnlZutaten.add(btnZutaten)

        // Panel Zubereitung
        pnlZubereitung.layout = GridLayout(2, 1)
        zubereitung.font = Font("Arial", Font.BOLD, 20)
        taZubereitung = JTextArea()

        pnlZubereitung.add(zubereitung)
        pnlZubereitung.add(taZubereitung)


        // Rezepterstellungs Komponenente
        pnlErstellung = JPanel()
        pnlErstellung.layout = GridLayout(8, 1, 0, 10)
        pnlErstellung.border = EmptyBorder(0, 10, 0, 10)
        pnlErstellung.add(pnlRezeptname)
        pnlErstellung.add(pnlZubereitungszeit)
        pnlErstellung.add(pnlZeiteinheit)
        pnlErstellung.add(pnlSchwierigkeitsgrad)
        pnlErstellung.add(pnlPortion)
        pnlErstellung.add(pnlKcal)
        pnlErstellung.add(pnlZutaten)
        pnlErstellung.add(pnlZubereitung)

        val scrollErstellung = JScrollPane(pnlErstellung)
        this.layout = BorderLayout()
        this.add(pnlNavigationsleiste, BorderLayout.NORTH)
        this.add(scrollErstellung, BorderLayout.CENTER)

        this.title = "Rezepterstellung"
        this.setSize(1000, 800)
        this.isVisible = true
    }

    fun pruefeEingabenZuGericht(rezept:String, dauer:String, zeiteinheit:Int, schwierigkeitsgrad:Int, portionen:String, kcal:String,
                                        zutaten:List<Zutat>, zubereitung:String): Boolean {
        val dauerInt:Int
        val portionenInt:Int
        val kcalInt:Int
        try {
            dauerInt = dauer.toInt()
            portionenInt = portionen.toInt()
            kcalInt = kcal.toInt()
        }catch (e:Exception){
            return false
        }
        if (rezept.isEmpty() || dauerInt < 0 || zeiteinheit == -1 || schwierigkeitsgrad == -1 || portionenInt < 0
            || kcalInt < 0 || zutaten.isEmpty() || zubereitung.isEmpty()) {
            return false
        }
        return true
    }

    fun erstelleGerichtJSON(gericht: Gericht):Gericht? {
        val erstelltesGericht: Gericht? = this.gerichtRegler.erstelleGericht(gericht)
        return erstelltesGericht
    }

    fun getListZutaten():List<Zutat>{
        return lsZutaten
    }

    fun fillFormular(gericht: Gericht){
        this.updateGericht = gericht

        this.tfRezeptname.text = gericht.rezept
        this.tfZubereitungszeit.text = gericht.zubereitungszeit.dauer.toString()
        this.tfZeiteinheit.selectedItem = gericht.zubereitungszeit.zeiteinheit
        this.tfSchwierigkeitsgrad.selectedItem = gericht.schwierigkeitsgrad
        this.tfPortion.text = gericht.portionen.toString()
        this.tfKcal.text = gericht.kcal.toString()
        this.lsZutaten = gericht.zutaten
        this.taZubereitung.text = gericht.zubereitung
        this.bildPfad = gericht.bildPfad

        this.repaint()
    }
}