package info.petrsabata.advent2020

import java.io.File
import java.lang.IllegalArgumentException
import java.net.URI
import java.net.URL

class InputHelper(private val resourceFileName: String) {

    fun readIntLines(): List<Int> {
        return getFile().useLines {
            it.map { entry -> entry.toInt() }.toList()
        }
    }

    private fun getFile(): File {
        return File(getUri())
    }

    private fun getUri(): URI {
        val resource = javaClass.classLoader.getResource(resourceFileName)

        if (resource == null) {
            throw IllegalArgumentException("No resource file found for $resourceFileName.")
        }

        return resource.toURI()
    }


}