package hr.algebra.ark.handler

import android.content.Context
import android.util.Log
import hr.algebra.ark.factory.createFile
import hr.algebra.ark.factory.createGetHttpUrlConnection
import java.io.File
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths

fun downloadAudioAndStore(context: Context, audio: String): String? {
    val filename = audio.substring(audio.lastIndexOf("/") + 1)
    val file: File = createFile(context, filename)

    try {
        val con: HttpURLConnection = createGetHttpUrlConnection(audio)
        Files.copy(con.inputStream, Paths.get(file.toURI()))
        return file.absolutePath
    } catch (e: java.lang.Exception) {
        Log.e("AUDIO_HANLDER", e.toString(), e)
    }

    return null
}