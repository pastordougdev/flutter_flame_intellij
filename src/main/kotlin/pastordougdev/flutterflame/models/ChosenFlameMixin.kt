package pastordougdev.flutterflame.models

class ChosenFlameMixin(
    val name: String,
    val genericString: String,
    val flamePackage: Int,
) {
    private val genericTypes = mutableListOf<String>()

    init {
        if(genericString.isNotEmpty()) {
            val gens = genericString.split(",")
            for (gen in gens) {
                genericTypes.add(gen.trim())
            }
        }
    }

    fun getMixin() : String {
        val mixinBuffer = StringBuffer()
        mixinBuffer.append(name)
        mixinBuffer.append(getTypes())
        return mixinBuffer.toString()
    }

    fun getTypes() : String {
        if(genericTypes.isEmpty()) return ""
        return genericTypes.joinToString(", ", "<",">")
    }
}