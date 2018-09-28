package es.unizar.webeng.hello

import java.io.File
import java.util.Base64

class Qr (var path : String = "") {
    var phrase : String = ""
    var hash : Int = 0

    fun getbase64() : String {
        val imgBytes = File(path.toString()).readBytes()
        val base64img = Base64.getEncoder().encodeToString(imgBytes)

        return base64img
    }

}