package helper

fun loadResource(resource: String): String {
    return try {
        object {}.javaClass.getResource(resource).readText(Charsets.UTF_8)
    } catch (e: Exception) {
        throw RuntimeException("Failed to load resource=$resource!", e)
    }
}