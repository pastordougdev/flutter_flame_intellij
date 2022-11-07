package pastordougdev.flutterflame.misc

fun camelToSnakeCase(text: String) : String {
    val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
    return camelRegex.replace(text) {
        "_${it.value}"
    }.toLowerCase()
}

