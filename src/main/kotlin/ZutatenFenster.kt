import java.awt.GridLayout
import java.awt.event.ActionListener
import javax.swing.*

class ZutatenFenster : JFrame {

    private var felderPanel: FelderPanel
    private val zutaten = mutableListOf<Zutat>()

    private var pnlBezeichnungen = JPanel()
    private var lbName = JLabel("Zutat")
    private var lbAnzahl = JLabel("Anzahl")
    private var lbEinheit = JLabel("Maßeinheit")
    private var lbEmpty = JLabel()

    private var formular: Formular
    private var gerichtRegler:GerichtRegler

    constructor(formular: Formular, gerichteRegler:GerichtRegler) {
        this.formular = formular
        this.gerichtRegler = gerichteRegler

        this.title = "Hinzufügen von Zutaten"

        // JPanel, um FelderPanel und Buttons anzuzeigen
        val panel = JPanel()
        panel.layout = BoxLayout(panel, BoxLayout.Y_AXIS)

        // JPanel für Bezeichnungen (Name, Einheit, Anzahl)
        pnlBezeichnungen.layout = GridLayout(1, 4)

        pnlBezeichnungen.add(lbAnzahl)
        pnlBezeichnungen.add(lbEinheit)
        pnlBezeichnungen.add(lbName)
        pnlBezeichnungen.add(lbEmpty)

        panel.add(pnlBezeichnungen)

        // FelderPanel
        felderPanel = FelderPanel()
        if(formular.getListZutaten().isNotEmpty()){
            felderPanel.addZutatenListe(formular.getListZutaten())
        }
        panel.add(felderPanel)

        // JButton, um Eingaben abzurufen
        val eingabenButton = JButton("Zutatenliste speichern")
        eingabenButton.addActionListener (ActionListener {
            val namen = felderPanel.getNamen()
            val anzahlen = felderPanel.getAnzahlen()
            val einheiten = felderPanel.getEinheiten()
            val felder = felderPanel.getFelderAnzahl()

            if(namen.size != felder || einheiten.size != felder || anzahlen.size != felder){
                JOptionPane.showMessageDialog(this, "Bitte alle Felder ausfüllen.")
                return@ActionListener
            }

            for(index in 0 until felder){
                val zutat = Zutat(namen[index], einheiten[index], anzahlen[index])
                zutaten.add(zutat)
            }

            this.dispose()
        })
        panel.add(eingabenButton)

        contentPane.add(panel)
        setSize(500,500)
        isVisible = true
    }

    fun getZutaten(): List<Zutat>{
        return zutaten
    }
}