package cookbook.kern

class Zubereitungszeit(val dauer: Int, val zeiteinheit: Zeiteinheit) {
    constructor() : this(0, Zeiteinheit.Minuten)
}