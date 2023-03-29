import cookbook.applikation.EntityController
import cookbook.applikation.GerichteVerwalter
import cookbook.kern.*

import org.easymock.EasyMock
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach

import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GerichteVerwalterTest {

    private lateinit var verwalter: EntityController
    private lateinit var gerichteVerwalter: GerichteVerwalter
    private lateinit var gerichtN1: Gericht
    private lateinit var gerichtN2: Gericht
    private lateinit var gerichtN3: Gericht
    private var alleGerichte: MutableList<Gericht> = mutableListOf()
    private var zutatenListe: MutableList<Zutat> = mutableListOf()

    @BeforeEach
    @Throws(Exception::class)
    fun testEinrichten() {
        verwalter = EasyMock.createMock(EntityController::class.java)
        gerichteVerwalter = GerichteVerwalter(verwalter)
        zutatenListe.add(Zutat("TestZutat", Einheit.Kilogramm, 1.2))
        gerichtN1 = Gericht("TestGerichtNr1", Zubereitungszeit(60,Zeiteinheit.Minuten),
            Schwierigkeitsgrad.Einfach, 4, 450, zutatenListe, "Das wird so zubereitet.", "/test.png")
        gerichtN2 = Gericht("TestGerichtNr2", Zubereitungszeit(60,Zeiteinheit.Minuten),
            Schwierigkeitsgrad.Einfach, 4, 450, zutatenListe, "Das wird so zubereitet.", "/test.png")
        gerichtN3 = Gericht("TestGerichtNr3", Zubereitungszeit(60,Zeiteinheit.Minuten),
            Schwierigkeitsgrad.Einfach, 4, 450, zutatenListe, "Das wird so zubereitet.", "/test.png")
        alleGerichte.add(gerichtN1)
        alleGerichte.add(gerichtN2)
        alleGerichte.add(gerichtN3)
    }

    //Test:
    // hole() - Erfolgreich/Misserfolgreich
    // holeAlle() - Erfolgreich/Misserfolgreich
    // erstelle() - Erfolgreich/Misserfolgreich
    // loesche() - Erfolgreich/Misserfolgreich


    @Test
    fun holeGericht(){
        EasyMock.expect(verwalter.hole(1)).andReturn(gerichtN1)
        EasyMock.replay(verwalter)

        val geholtesGericht = this.verwalter.hole(1)
        Assertions.assertEquals(geholtesGericht.id, gerichtN1.id)
        Assertions.assertEquals(geholtesGericht.rezept, gerichtN1.rezept)
        Assertions.assertEquals(geholtesGericht.zubereitungszeit, gerichtN1.zubereitungszeit)
        Assertions.assertEquals(geholtesGericht.schwierigkeitsgrad, gerichtN1.schwierigkeitsgrad)
        Assertions.assertEquals(geholtesGericht.portionen, gerichtN1.portionen)
        Assertions.assertEquals(geholtesGericht.kcal, gerichtN1.kcal)
        Assertions.assertEquals(geholtesGericht.zutaten, gerichtN1.zutaten)
        Assertions.assertEquals(geholtesGericht.zubereitung, gerichtN1.zubereitung)
        Assertions.assertEquals(geholtesGericht.bildPfad, gerichtN1.bildPfad)
        EasyMock.verify(verwalter)
    }

    @Test
    fun holeAlleGerichte(){
        EasyMock.expect(verwalter.holeAlle()).andReturn(alleGerichte)
        EasyMock.replay(verwalter)

        val geholteAlleGerichte = this.verwalter.holeAlle()
        for(geholtesGericht in geholteAlleGerichte){
            val gesuchtesGericht = alleGerichte.first { it.id == geholtesGericht.id }
            Assertions.assertEquals(geholtesGericht.id,  gesuchtesGericht.id)
            Assertions.assertEquals(geholtesGericht.rezept, gesuchtesGericht.rezept)
            Assertions.assertEquals(geholtesGericht.zubereitungszeit, gesuchtesGericht.zubereitungszeit)
            Assertions.assertEquals(geholtesGericht.schwierigkeitsgrad, gesuchtesGericht.schwierigkeitsgrad)
            Assertions.assertEquals(geholtesGericht.portionen, gesuchtesGericht.portionen)
            Assertions.assertEquals(geholtesGericht.kcal, gesuchtesGericht.kcal)
            Assertions.assertEquals(geholtesGericht.zutaten, gesuchtesGericht.zutaten)
            Assertions.assertEquals(geholtesGericht.zubereitung, gesuchtesGericht.zubereitung)
            Assertions.assertEquals(geholtesGericht.bildPfad, gesuchtesGericht.bildPfad)
        }
        EasyMock.verify(verwalter)
    }


    @Test
    fun erstelleGericht(){
        EasyMock.expect(verwalter.erstelle(gerichtN1)).andReturn(gerichtN1)
        EasyMock.replay(verwalter)

        val erstelltesGericht = gerichteVerwalter.erstelleGericht(gerichtN1)
        Assertions.assertEquals(erstelltesGericht.id, gerichtN1.id)
        Assertions.assertEquals(erstelltesGericht.rezept, "TestGerichtNr1")
        Assertions.assertEquals(erstelltesGericht.zubereitungszeit.dauer, 60)
        Assertions.assertEquals(erstelltesGericht.zubereitungszeit.zeiteinheit, Zeiteinheit.Minuten)
        Assertions.assertEquals(erstelltesGericht.schwierigkeitsgrad, Schwierigkeitsgrad.Einfach)
        Assertions.assertEquals(erstelltesGericht.portionen, 4)
        Assertions.assertEquals(erstelltesGericht.kcal, 450)
        Assertions.assertEquals(erstelltesGericht.zutaten, zutatenListe)
        Assertions.assertEquals(erstelltesGericht.zubereitung, "Das wird so zubereitet.")
        Assertions.assertEquals(erstelltesGericht.bildPfad, "/test.png")
        EasyMock.verify(verwalter)
    }

    @Test
    fun aendereGericht(){
        EasyMock.expect(verwalter.aendere(gerichtN2, gerichtN1.id)).andReturn(gerichtN2)
        EasyMock.replay(verwalter)

        val geaendertesGericht = gerichteVerwalter.aendereGericht(gerichtN2, gerichtN1.id)
        Assertions.assertEquals(geaendertesGericht.id, gerichtN2.id)
        Assertions.assertEquals(geaendertesGericht.rezept, gerichtN2.rezept)
        Assertions.assertEquals(geaendertesGericht.zubereitungszeit, gerichtN2.zubereitungszeit)
        Assertions.assertEquals(geaendertesGericht.schwierigkeitsgrad, gerichtN2.schwierigkeitsgrad)
        Assertions.assertEquals(geaendertesGericht.portionen, gerichtN2.portionen)
        Assertions.assertEquals(geaendertesGericht.kcal, gerichtN2.kcal)
        Assertions.assertEquals(geaendertesGericht.zutaten, gerichtN2.zutaten)
        Assertions.assertEquals(geaendertesGericht.zubereitung, gerichtN2.zubereitung)
        Assertions.assertEquals(geaendertesGericht.bildPfad, gerichtN2.bildPfad)
        EasyMock.verify(verwalter)
    }


    @Test
    fun loescheGerichtErfolgreich(){
        EasyMock.expect(verwalter.loesche(gerichtN1.id)).andReturn(true)
        EasyMock.replay(verwalter)

        val erfolgreich = gerichteVerwalter.loescheGericht(gerichtN1.id)
        assertTrue(erfolgreich)
        EasyMock.verify(verwalter)
    }

    @Test
    fun loescheGerichtMisserfolg(){
        EasyMock.expect(verwalter.loesche(gerichtN1.id)).andReturn(false)
        EasyMock.replay(verwalter)

        val misserfolg = gerichteVerwalter.loescheGericht(gerichtN1.id)
        assertFalse(misserfolg)
        EasyMock.verify(verwalter)
    }
}