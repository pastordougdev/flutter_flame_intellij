package pastordougdev.flutterflame.misc

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.LangDataKeys

fun camelToSnakeCase(text: String) : String {
    val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
    return camelRegex.replace(text) {
        "_${it.value}"
    }.toLowerCase()
}

fun getDirName(
    dataContext: DataContext
): String {
    val view = LangDataKeys.IDE_VIEW.getData(dataContext);
    val dir = view?.orChooseDirectory;
    return dir?.name ?: ""
}