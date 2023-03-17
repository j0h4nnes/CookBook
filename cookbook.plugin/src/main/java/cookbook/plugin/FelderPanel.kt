package cookbook.plugin

import cookbook.kern.Einheit
import cookbook.kern.Zutat
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.text.NumberFormat
import javax.swing.*

class FelderPanel:JPanel(), ActionListener {

    private val hinzufuegenButton: JButton
    private val felderPanel: JPanel
    private var felderAnzahl = 0

    init {
        layout = BorderLayout()

        // JPanel, um die hinzugefügten Felder anzuzeigen
        felderPanel = JPanel()
        felderPanel.layout = BoxLayout(felderPanel, BoxLayout.Y_AXIS)
        val scrollPane = JScrollPane(felderPanel)

        // JButton, um neue Felder hinzuzufügen
        hinzufuegenButton = JButton("Weitere Zutat hinzufügen")
        hinzufuegenButton.addActionListener(this)

        add(scrollPane, BorderLayout.CENTER)
        add(hinzufuegenButton, BorderLayout.SOUTH)
    }

    override fun actionPerformed(e: ActionEvent) {
        if (e.source == hinzufuegenButton) {
            felderAnzahl++

            val feldPanel = JPanel()
            feldPanel.layout = GridLayout(1, 0)

            // JTextFields für Name und Einheit
            val nameTextField = JTextField(2)
            val einheitComboBox: JComboBox<Einheit> = JComboBox(Einheit.values())

            val doubleFormat = NumberFormat.getNumberInstance()
            doubleFormat.isParseIntegerOnly = false
            val anzahlTextField = JFormattedTextField(doubleFormat)
            anzahlTextField.value = 1.0 // Setzt den Anfangswert

            feldPanel.add(anzahlTextField)
            feldPanel.add(einheitComboBox)
            feldPanel.add(nameTextField)

            // JButton, um dieses Feld zu entfernen
            val entfernenButton = JButton("Zutat entfernen")
            entfernenButton.addActionListener {
                felderPanel.remove(feldPanel)
                felderPanel.revalidate()
                felderPanel.repaint()
                felderAnzahl--
            }
            feldPanel.add(entfernenButton)

            felderPanel.add(feldPanel)
            felderPanel.revalidate()
            felderPanel.repaint()
        }
    }

    fun getFelderAnzahl(): Int {
        return felderAnzahl
    }

    fun getNamen(): List<String> {
        val namen = mutableListOf<String>()
        val felder = felderPanel.components
        for (i in 0 until felderAnzahl) {
            val feldPanel = felder[i] as JPanel
            val nameTextField = feldPanel.getComponent(2) as JTextField
            namen.add(nameTextField.text)
        }
        return namen
    }

    fun getEinheiten(): List<Einheit> {
        val einheiten = mutableListOf<Einheit>()
        val felder = felderPanel.components
        for (i in 0 until felderAnzahl) {
            val feldPanel = felder[i] as JPanel
            val einheitTextField = feldPanel.getComponent(1) as JComboBox<Einheit>
            einheiten.add(einheitTextField.getItemAt(i))
        }
        return einheiten
    }

    fun getAnzahlen(): DoubleArray {
        val anzahlen = DoubleArray(felderAnzahl)
        val felder = felderPanel.components
        (223).toDouble()
        for (i in 0 until felderAnzahl) {
            val feldPanel = felder[i] as JPanel
            val anzahlTextField = feldPanel.getComponent(0) as JFormattedTextField
            anzahlen[i] = anzahlTextField.value.toString().toDouble()
        }
        return anzahlen
    }

    fun addZutatenListe(lsZutat: List<Zutat>){
        for (zutat in lsZutat) {
            felderAnzahl++

            val feldPanel = JPanel()
            feldPanel.layout = GridLayout(1, 0)

            // JTextFields für Name und Einheit
            val nameTextField = JTextField(2)
            nameTextField.text = zutat.getName()
            val einheitComboBox: JComboBox<Einheit> = JComboBox(Einheit.values())
            einheitComboBox.selectedItem = zutat.getEinheit()

            val doubleFormat = NumberFormat.getNumberInstance()
            doubleFormat.isParseIntegerOnly = false
            val anzahlTextField = JFormattedTextField(doubleFormat)
            anzahlTextField.value = zutat.getAnzahl()

            feldPanel.add(anzahlTextField)
            feldPanel.add(einheitComboBox)
            feldPanel.add(nameTextField)

            // JButton, um dieses Feld zu entfernen
            val entfernenButton = JButton("Zutat entfernen")
            entfernenButton.addActionListener {
                felderPanel.remove(feldPanel)
                felderPanel.revalidate()
                felderPanel.repaint()
                felderAnzahl--
            }
            feldPanel.add(entfernenButton)

            felderPanel.add(feldPanel)
            felderPanel.revalidate()
            felderPanel.repaint()
        }
    }
}