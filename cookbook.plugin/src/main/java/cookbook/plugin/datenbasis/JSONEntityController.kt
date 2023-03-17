package cookbook.plugin.datenbasis

import com.fasterxml.jackson.databind.ObjectMapper
import cookbook.applikation.EntityController
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.deleteIfExists
import cookbook.kern.Gericht

// Zugriff auf die JSON Files in eigener Klasse (fun hole, fun schreibe, fun lösche)
class JSONEntityController : EntityController {
    private val objectMapper = ObjectMapper()
    private var listeMitAllenGerichten: MutableList<Gericht> = ArrayList()
    private val dir: File = File("cookbook.plugin/src/main/resources/Gerichte")

    init {
        holeAlle()
    }

    override fun holeAlle(): MutableList<Gericht> {
        val fileType = ".json" // Replace with the file type you want to filter by
        val files = dir.listFiles { dir, name -> name.endsWith(fileType) } // Filter files by file type
        listeMitAllenGerichten = mutableListOf()
        if (files != null) {
            for (file in files) {
                if (file.isFile) {
                    val gericht = objectMapper.readValue(file, Gericht::class.java)
                    listeMitAllenGerichten.add(gericht)
                }
            }
        }
        return listeMitAllenGerichten
    }
    override fun erstelle(gericht: Gericht):Gericht {
        val writer = objectMapper.writerWithDefaultPrettyPrinter()
        val json = writer.writeValueAsString(gericht)
        val jsonWithoutId = json.replace(Regex("\r\n  \"id\" : ${gericht.id},"), "")
        val file = File("$dir/${gericht.rezept.lowercase()}.json")
        file.writeText(jsonWithoutId)

        listeMitAllenGerichten.add(gericht)
        return gericht
    }

    override fun loesche(id: Int): Boolean {
        try{
            val gericht = listeMitAllenGerichten.first { it.id == id }
            val gerichtJSON = Files.list(Paths.get(dir.toURI()))
                .filter { it.fileName.toString().contains(gericht.rezept, ignoreCase = true) }
                .findFirst()
                .orElse(null)
            gerichtJSON.deleteIfExists()
        }catch (ex:Exception){
            return false
        }
        return false
    }

    override fun aendere(gericht: Gericht, id: Int): Gericht {
        return gericht
    }

    override fun hole(id: Int): Gericht {
        return listeMitAllenGerichten.first{ it.id == id}
    }

}