package day11


enum class Paint(val printChar: Char, val value: Long) {
    BLACK('.', 0), WHITE('#',1);

    override fun toString(): String{
        return "$printChar"
    }


}