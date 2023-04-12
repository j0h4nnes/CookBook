import cookbook.adapter.regler.GerichtRegler
import cookbook.applikation.EntityController
import cookbook.applikation.GerichteVerwalter
import cookbook.kern.*
import cookbook.plugin.Formular
import org.easymock.EasyMock
import org.easymock.EasyMock.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FormularTest {

    private lateinit var verwalter: EntityController
    private lateinit var gerichteVerwalter: GerichteVerwalter
    private lateinit var regler: GerichtRegler
    private lateinit var formular: Formular
    private lateinit var testGericht: Gericht
    private var zutatenListe: MutableList<Zutat> = mutableListOf()

    @BeforeEach
    @Throws(Exception::class)
    fun vorbereitung(){
        verwalter = mock(EntityController::class.java)
        gerichteVerwalter = createMockBuilder<GerichteVerwalter>(GerichteVerwalter::class.java)
            .withConstructor(EntityController::class.java).withArgs(verwalter).createMock()
        regler = createMockBuilder<GerichtRegler>(GerichtRegler::class.java)
            .withConstructor(GerichteVerwalter::class.java).withArgs(gerichteVerwalter).createMock()
        formular = Formular(regler)

        zutatenListe.add(Zutat("TestZutat", Einheit.Kilogramm, 1.2))
        testGericht = Gericht("TestGericht", Zubereitungszeit(60, Zeiteinheit.Minuten),
            Schwierigkeitsgrad.Einfach, 4, 450, zutatenListe, "Das wird so zubereitet.", "/test.png")
    }

    @Test
    fun fillFormularTest(){
        formular.fillFormular(testGericht)

        Assertions.assertEquals(formular.tfRezeptname.text, testGericht.rezept)
        Assertions.assertEquals(formular.tfZubereitungszeit.text, testGericht.zubereitungszeit.dauer.toString())
        Assertions.assertEquals(formular.tfZeiteinheit.selectedItem, testGericht.zubereitungszeit.zeiteinheit)
        Assertions.assertEquals(formular.tfSchwierigkeitsgrad.selectedItem, testGericht.schwierigkeitsgrad)
        Assertions.assertEquals(formular.tfPortion.text, testGericht.portionen.toString())
        Assertions.assertEquals(formular.tfKcal.text, testGericht.kcal.toString())
        Assertions.assertEquals(formular.lsZutaten, testGericht.zutaten)
        Assertions.assertEquals(formular.taZubereitung.text, testGericht.zubereitung)
        Assertions.assertEquals(formular.bildPfad, testGericht.bildPfad)
    }

    @Test
    fun erstelleGericht(){
        formular.tfRezeptname.text = testGericht.rezept
        formular.tfZubereitungszeit.text = testGericht.zubereitungszeit.dauer.toString()
        formular.tfZeiteinheit.selectedItem = testGericht.zubereitungszeit.zeiteinheit
        formular.tfSchwierigkeitsgrad.selectedItem = testGericht.schwierigkeitsgrad
        formular.tfPortion.text = testGericht.portionen.toString()
        formular.tfKcal.text = testGericht.kcal.toString()
        formular.lsZutaten = testGericht.zutaten
        formular.taZubereitung.text = testGericht.zubereitung
        formular.bildPfad = testGericht.bildPfad

        EasyMock.expect(verwalter.erstelle(testGericht)).andReturn(testGericht)
        EasyMock.replay(verwalter)

        val erstelltesGericht = formular.erstelleGerichtJSON(testGericht)

        if (erstelltesGericht != null) {
            Assertions.assertEquals(erstelltesGericht.id, testGericht.id)
            Assertions.assertEquals(erstelltesGericht.rezept, testGericht.rezept)
            Assertions.assertEquals(erstelltesGericht.zubereitungszeit.dauer, testGericht.zubereitungszeit.dauer)
            Assertions.assertEquals(erstelltesGericht.zubereitungszeit.zeiteinheit, testGericht.zubereitungszeit.zeiteinheit)
            Assertions.assertEquals(erstelltesGericht.schwierigkeitsgrad, testGericht.schwierigkeitsgrad)
            Assertions.assertEquals(erstelltesGericht.portionen, testGericht.portionen)
            Assertions.assertEquals(erstelltesGericht.kcal, testGericht.kcal)
            Assertions.assertEquals(erstelltesGericht.zutaten, testGericht.zutaten)
            Assertions.assertEquals(erstelltesGericht.zubereitung, testGericht.zubereitung)
            Assertions.assertEquals(erstelltesGericht.bildPfad, testGericht.bildPfad)
        }
    }

    @Test
    fun pruefeEingabenZuGerichtErfolgreich(){

        val erfolgreich = formular.pruefeEingabenZuGericht(
            testGericht.rezept,testGericht.zubereitungszeit.dauer.toString(), testGericht.zubereitungszeit.zeiteinheit.ordinal,
            testGericht.schwierigkeitsgrad.ordinal, testGericht.portionen.toString(), testGericht.kcal.toString(), testGericht.zutaten, testGericht.zubereitung)

        assertTrue(erfolgreich)
    }

    @Test
    fun pruefeEingabenZuGerichtNichtErfolgreichEingabeFehlt(){

        val erfolgreich = formular.pruefeEingabenZuGericht(
            testGericht.rezept,testGericht.zubereitungszeit.dauer.toString(), testGericht.zubereitungszeit.zeiteinheit.ordinal,
            testGericht.schwierigkeitsgrad.ordinal, "", testGericht.kcal.toString(), testGericht.zutaten, testGericht.zubereitung)

        assertFalse(erfolgreich)
    }

    @Test
    fun pruefeEingabenZuGerichtNichtErfolgreichEingabeFalsch(){

        val erfolgreich = formular.pruefeEingabenZuGericht(
            testGericht.rezept,testGericht.zubereitungszeit.dauer.toString(), testGericht.zubereitungszeit.zeiteinheit.ordinal,
            testGericht.schwierigkeitsgrad.ordinal, "TestFehler", testGericht.kcal.toString(), testGericht.zutaten, testGericht.zubereitung)

        assertFalse(erfolgreich)
    }

    @Test
    fun pruefeEingabenZuGerichtNichtErfolgreichZutatenListeLeer(){

        val erfolgreich = formular.pruefeEingabenZuGericht(
            testGericht.rezept,testGericht.zubereitungszeit.dauer.toString(), testGericht.zubereitungszeit.zeiteinheit.ordinal,
            testGericht.schwierigkeitsgrad.ordinal, testGericht.portionen.toString(), testGericht.kcal.toString(), emptyList(), testGericht.zubereitung)

        assertFalse(erfolgreich)
    }
}