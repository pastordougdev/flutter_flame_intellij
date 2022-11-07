package pastordougdev.flutterflame.models

data class FlameComponent (
    val description: String,
    val generics: Int,
    val flamePackage: Int,
        ){

    fun getClassDeclaration(className: String) : String {
        val classDeclaration = StringBuilder()
        classDeclaration.append("class $className extends $description")

        return classDeclaration.toString()
    }

    fun getClassDeclaration(className: String, genericTypes: List<String>) : String {
        val classDeclaration = StringBuilder()
        val gens = genericTypes.joinToString(prefix = "<", postfix = ">", separator = ", ")
        classDeclaration.append("class $className extends $description")
        classDeclaration.append(gens)
        return classDeclaration.toString()
    }
}