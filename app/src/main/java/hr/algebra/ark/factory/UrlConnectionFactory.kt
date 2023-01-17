package hr.algebra.ark.factory

import android.content.Context
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

private const val TIMEOUT = 10000
private const val METHOD_GET = "GET"
private const val USER_AGENT = "User-Agent"
private const val MOZILLA = "Mozilla/5.0"

fun createGetHttpUrlConnection(urlImage: String): HttpURLConnection {
    val _url = URL(urlImage)
    return (_url.openConnection() as HttpURLConnection).apply {
        connectTimeout = TIMEOUT
        readTimeout = TIMEOUT
        requestMethod = METHOD_GET
        addRequestProperty(USER_AGENT, MOZILLA)
    }
}

fun createFile(context: Context, filename: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, filename)
    if (file.exists()) file.delete()
    return file
}