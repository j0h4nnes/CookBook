import java.awt.*
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.EmptyBorder

class MainFrame : JFrame{

    private var label = JLabel()

    private var btnRezepterstellung: JButton
    private var btnAlleGerichte: JButton
    private var copyright: JLabel
    private var rezepteBuchLogo: JLabel
    private var hauptPanel: JPanel
    private var buttonPanel: JPanel
    private var gerichtRegler: GerichtRegler

    constructor(gerichtRegler: GerichtRegler) {
        this.gerichtRegler = gerichtRegler

        hauptPanel = JPanel()
        hauptPanel.layout = BorderLayout()

        // Logo
        rezepteBuchLogo = JLabel()
        rezepteBuchLogo.size = Dimension(200, 200)
        rezepteBuchLogo.border = EmptyBorder(40,0,0,0)
        rezepteBuchLogo.horizontalAlignment = SwingConstants.CENTER

        // Logo im Label verankern
        try {
            var imgLogo: Image = ImageIO.read(javaClass.classLoader.getResource("TCB_Logo.png"))
            imgLogo = imgLogo.getScaledInstance(453, 180, Image.SCALE_SMOOTH)
            rezepteBuchLogo.icon = ImageIcon(imgLogo)
        } catch (ex: Exception) {
            println(ex)
        }
        val pnlMitte = JPanel()
        pnlMitte.layout = GridLayout(2, 1)

        // Label unter Logo
        label = JLabel()
        label.text = "Das einfache Kochbuch!"
        label.font = Font("Arial", Font.PLAIN, 20)
        label.horizontalAlignment = SwingConstants.CENTER

        // Button Panel für den Button Rezepterstellung
        buttonPanel = JPanel()
        buttonPanel.layout = GridLayout(2, 1, 10, 10)
        buttonPanel.border = EmptyBorder(0, 20, 10, 20)

        // Button für die Übersicht aller Gerichte
        btnAlleGerichte = JButton("Übersicht der Gerichte")
        btnAlleGerichte.font = Font("Arial", Font.PLAIN, 20)

        btnAlleGerichte.addActionListener {
            GerichteUebersicht(this.gerichtRegler)
        }

        // Button für Rezepterstellung
        btnRezepterstellung = JButton(" zur Rezepterstellung")
        btnRezepterstellung.font = Font("Arial", Font.PLAIN, 20)

        btnRezepterstellung.addActionListener {
            Formular(this.gerichtRegler)
        }

        // ButtonPanel die Buttons hinzufügen
        buttonPanel.add(btnAlleGerichte)
        buttonPanel.add(btnRezepterstellung)

        // Panel Mitte befüllen
        pnlMitte.add(label)
        pnlMitte.add(buttonPanel)

        // Fußzeile hinzufügen
        copyright = JLabel()
        copyright.text = "Erstellt von Irina Joos & Johannes Karcher"
        copyright.horizontalAlignment = SwingConstants.CENTER
        copyright.font = Font("Arial", Font.PLAIN, 15)

        // Hauptpanel alle Komponenten hinzufügen
        hauptPanel.add(rezepteBuchLogo, BorderLayout.NORTH)
        hauptPanel.add(pnlMitte, BorderLayout.CENTER)
        hauptPanel.add(copyright, BorderLayout.SOUTH)

        // Klasse Hauptfenster alles hinzufügen
        this.title = "Rezepte-Buch"
        this.setSize(600, 700)
        this.isVisible = true
        this.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        this.add(hauptPanel)
    }
}