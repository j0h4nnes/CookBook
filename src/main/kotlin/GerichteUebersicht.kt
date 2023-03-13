import java.awt.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.border.EmptyBorder

class GerichteUebersicht(gerichteRegler: GerichtRegler) : Observer, JFrame() {

    private var gerichteRegler:GerichtRegler

    private val lbGericht = JLabel("Gerichte:")
    private val elementList: JList<Gericht>

    private val lbSearch = JLabel("Suche nach Begriff:")
    private val tfSearch = JTextField()
    private val btSearch = JButton("Suche starten")
    private val lbFilterSWG = JLabel("Filter Schwierigkeitsgrad:")
    private val cbFilterSWG = mutableListOf<JCheckBox>()
    private val lbFilterKcal = JLabel("Filter Kcal (max Kcal):")
    private val tfFilterKcal = JTextField()
    private val btFilter = JButton("Filtern")

    private val lbBild = JLabel()
    private val lbRezept = JLabel()
    private val lbZubereitungszeit = JLabel()
    private val lbSchwierigkeitsgrad = JLabel()
    private val lbPortion = JLabel()
    private val lbKcal = JLabel()
    private val lbZutat = JLabel("Zutaten:")
    private val listModel = DefaultListModel<String>()
    private val listZutaten = JList(listModel)
    private val lbZubereitung = JTextArea()

    private val btDelete = JButton("Rezept löschen")
    private val btUpdate = JButton("Rezept bearbeiten")
    private val btNeu = JButton("Neues Rezept hinzufügen")

    private lateinit var imgLogo: Image

    init {
        this.gerichteRegler = gerichteRegler
        this.gerichteRegler.meldeAn(this)

        // Erstelle die Element-Liste
        elementList = JList(gerichteRegler.holeAlle()!!.toTypedArray())
        elementList.preferredSize = Dimension(150, 0)
        elementList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        elementList.addListSelectionListener {
            if (!it.valueIsAdjusting) {
                val selectedElement = elementList.selectedValue
                if(selectedElement != null) {
                    // Bild im Label verankern
                    try {
                        val picFile = File(selectedElement.bild)
                        if(picFile.isAbsolute){
                            imgLogo = ImageIO.read(picFile)
                        }else{
                            imgLogo = ImageIO.read(javaClass.classLoader.getResource(selectedElement.bild))
                        }
                        imgLogo = imgLogo.getScaledInstance(300, 180, Image.SCALE_SMOOTH)
                        lbBild.icon = ImageIcon(imgLogo)
                    } catch (ex: Exception) {
                        println(ex)
                    }
                    lbRezept.text = selectedElement.rezept
                    lbZubereitungszeit.text =
                        ("Zubereitungszeit: ${selectedElement.zubereitungszeit.dauer} ${selectedElement.zubereitungszeit.zeiteinheit}")
                    lbSchwierigkeitsgrad.text = ("Schwierigkeitsgrad: ${selectedElement.schwierigkeitsgrad.name}")
                    lbPortion.text = ("Portionen: ${selectedElement.portionen}")
                    lbKcal.text = ("Kcal: ${selectedElement.kcal}")
                    listModel.clear()
                    for (zutat in selectedElement.zutaten) {
                        listModel.addElement("${zutat.getAnzahl()} ${zutat.getEinheit().name} ${zutat.getName()}")
                    }
                    lbZubereitung.text = selectedElement.zubereitung
                }
            }
        }

        // Gibt erstes Element an
        lbRezept.text = "Rezeptname"
        lbZubereitungszeit.text = "Zubereitungszeit: "
        lbSchwierigkeitsgrad.text = "Schwierigkeitsgrad: "
        lbPortion.text = "Portionen: "
        lbKcal.text = "Kcal: "
        lbZubereitung.text = "Hier stehen die Zubereitungsschritte!"

        // Erstelle das Haupt-Panel
        val panel = JPanel()
        panel.layout = BorderLayout()

        // Erstelle die Such- und Filterleiste
        val pnNavigation = JPanel()
        pnNavigation.layout = BorderLayout(10, 0)
        pnNavigation.border = BorderFactory.createLineBorder(Color.BLACK, 2)

        val pnSearch = JPanel()
        pnSearch.layout = BorderLayout(0, 10)
        pnSearch.border = BorderFactory.createLineBorder(Color.BLACK, 2)

        val pnSearch2 = JPanel()
        pnSearch2.layout = GridLayout(1,2,0,15)
        pnSearch2.border = EmptyBorder(10,10,5,10)
        lbSearch.font = Font("Arial", Font.PLAIN, 20)

        pnSearch2.add(lbSearch)
        pnSearch2.add(tfSearch)
        pnSearch.add(pnSearch2)
        pnSearch.add(btSearch, BorderLayout.EAST)
        pnNavigation.add(pnSearch, BorderLayout.NORTH)

        val pnFilter = JPanel()
        pnFilter.layout = BorderLayout(0, 10)
        pnFilter.border = BorderFactory.createLineBorder(Color.BLACK, 2)

        val pnFilter2 = JPanel()
        pnFilter2.layout = BorderLayout()

        val pnFilterSWG = JPanel()
        pnFilterSWG.layout = GridLayout(1,2,0,15)
        pnFilterSWG.border = EmptyBorder(10, 10, 5, 10)
        lbFilterSWG.font = Font("Arial", Font.PLAIN, 20)

        val pnOptions = JPanel()
        pnOptions.layout = GridLayout(2, 2)
        for (sw in Schwierigkeitsgrad.values()){
            val checkBox = JCheckBox(sw.name)
            checkBox.isSelected = true
            cbFilterSWG.add(checkBox)
            pnOptions.add(checkBox)
        }

        btSearch.preferredSize = Dimension(200,0)
        btSearch.addActionListener {
            val searchText = tfSearch
            val fltGerichte = gerichteRegler.holeAlle()!!.filter { it.rezept.contains(searchText.text, ignoreCase = true) }

            elementList.setListData(fltGerichte.toTypedArray())
            elementList.repaint()
        }

        pnFilterSWG.add(lbFilterSWG)
        pnFilterSWG.add(pnOptions)
        pnFilter2.add(pnFilterSWG, BorderLayout.NORTH)

        val pnFilterKcal = JPanel()
        pnFilterKcal.layout = GridLayout(1,2,0,15)
        pnFilterKcal.border = EmptyBorder(10, 10, 5, 10)
        lbFilterKcal.font = Font("Arial", Font.PLAIN, 20)

        btFilter.preferredSize = Dimension(200,0)
        btFilter.addActionListener {
            val selected = cbFilterSWG.filter { it.isSelected }.map { it.text }
            val schwierigkeitsgrade = mutableListOf<Schwierigkeitsgrad>()
            for (sw in selected){
                schwierigkeitsgrade.add(Schwierigkeitsgrad.valueOf(sw))
            }
            var fltGerichte = gerichteRegler.holeAlle()!!.filter { gericht ->
                schwierigkeitsgrade.contains(gericht.schwierigkeitsgrad)
            }
            if(tfFilterKcal.text.isNotEmpty()) {
                fltGerichte = fltGerichte.filter { it.kcal <= tfFilterKcal.text.toInt() }
            }
            elementList.setListData(fltGerichte.toTypedArray())
            elementList.repaint()
        }

        pnFilterKcal.add(lbFilterKcal)
        pnFilterKcal.add(tfFilterKcal)
        pnFilter2.add(pnFilterKcal)
        pnFilter.add(pnFilter2)
        pnFilter.add(btFilter, BorderLayout.EAST)
        pnNavigation.add(pnFilter)

        // Erstelle das Element-List-Panel
        val listPanel = JPanel()
        listPanel.layout = BoxLayout(listPanel, BoxLayout.Y_AXIS)
        listPanel.preferredSize = Dimension(200, panel.preferredSize.height)

        lbGericht.font = Font("Arial", Font.BOLD, 20)
        lbGericht.preferredSize = Dimension(listPanel.preferredSize.width, 20)

        listPanel.add(lbGericht)
        listPanel.add(JScrollPane(elementList))
        listPanel.border = EmptyBorder(10,10,10,10)
        panel.add(listPanel, BorderLayout.WEST)

        // Erstelle das Detail-Panel
        val detailPanel = JPanel()
        detailPanel.layout = GridLayout(3,1)

        val infoPanel = JPanel()
        infoPanel.layout = BoxLayout(infoPanel, BoxLayout.Y_AXIS)
        infoPanel.border = EmptyBorder(10,10,10,10)

        val testPanel = JPanel()
        testPanel.layout = BorderLayout()

        lbBild.border = EmptyBorder(0,0,0,10)

        val pnZutat = JPanel()
        pnZutat.layout = BorderLayout()
        pnZutat.border = EmptyBorder(10,10,10,10)

        listZutaten.font = Font("Arial", Font.BOLD, 14)

        val pnZubereitung = JPanel()
        pnZubereitung.layout = BoxLayout(pnZubereitung, BoxLayout.Y_AXIS)

        lbRezept.font = Font("Arial", Font.BOLD, 30)
        lbZubereitungszeit.font = Font("Arial", Font.PLAIN, 20)
        lbSchwierigkeitsgrad.font = Font("Arial", Font.PLAIN, 20)
        lbPortion.font = Font("Arial", Font.PLAIN, 20)
        lbKcal.font = Font("Arial", Font.PLAIN, 20)
        lbZutat.font = Font("Arial", Font.PLAIN, 20)

        lbZutat.horizontalAlignment = SwingConstants.LEFT

        lbZubereitung.lineWrap = true
        lbZubereitung.wrapStyleWord = true
        lbZubereitung.isEditable = false
        lbZubereitung.font = Font("Arial", Font.PLAIN, 16)
        lbZubereitung.border = EmptyBorder(10,10,10,10)

        panel.add(pnNavigation, BorderLayout.NORTH)
        infoPanel.add(lbRezept)
        infoPanel.add(lbZubereitungszeit)
        infoPanel.add(lbSchwierigkeitsgrad)
        infoPanel.add(lbPortion)
        infoPanel.add(lbKcal)
        testPanel.add(infoPanel, BorderLayout.WEST)
        testPanel.add(lbBild, BorderLayout.EAST)
        detailPanel.add(testPanel)
        pnZutat.add(lbZutat, BorderLayout.NORTH)
        pnZutat.add(JScrollPane(listZutaten), BorderLayout.CENTER)
        detailPanel.add(pnZutat)
        detailPanel.add(lbZubereitung)
        detailPanel.border = EmptyBorder(10,10,10,10)
        panel.add(detailPanel)

        val pnUD = JPanel()
        pnUD.layout = GridLayout(0, 3, 20, 5)
        pnUD.border = BorderFactory.createLineBorder(Color.BLACK, 1)

        btNeu.addActionListener {
            Formular(this.gerichteRegler)
            this.dispose()
        }

        btUpdate.addActionListener {
            val selectedGericht = elementList.selectedValue
            val gerichtFormular = Formular(this.gerichteRegler)
            gerichtFormular.fillFormular(selectedGericht)
            this.dispose()
        }

        btDelete.addActionListener {
            val selectedGericht = elementList.selectedValue
            val message = "Wollen Sie das Gericht: ${selectedGericht.rezept} löschen?"
            val title = "Warning"
            val optionType = JOptionPane.YES_NO_OPTION
            val messageType = JOptionPane.WARNING_MESSAGE
            val options = arrayOf("Ja", "Nein")

            val result = JOptionPane.showOptionDialog(null, message, title, optionType, messageType, null, options, options[0])
            if (result == JOptionPane.YES_OPTION) {
                gerichteRegler.loescheGericht(selectedGericht.id)
                gerichteRegler.benachrichtige()
            }
        }

        pnUD.add(btNeu)
        pnUD.add(btUpdate)
        pnUD.add(btDelete)
        panel.add(pnUD, BorderLayout.SOUTH)

        this.layout = BorderLayout()
        this.add(panel)
        this.setSize(1000,800)
        this.title = "Gerichte Übersicht"
        this.isVisible = true
    }

    override fun aktualisiere(o: Any?) {
        val alleGerichte = this.gerichteRegler.holeAlle() as ArrayList<Gericht>
        elementList.setListData(alleGerichte.toTypedArray())
        elementList.repaint()
    }
}