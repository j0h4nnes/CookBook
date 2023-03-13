open class Entity {
    companion object {
        private var nextId = 0
        private fun getNextId() = nextId++
    }

    var id = getNextId()


    constructor()
    constructor(id: Int){
        this.id = id
    }
}