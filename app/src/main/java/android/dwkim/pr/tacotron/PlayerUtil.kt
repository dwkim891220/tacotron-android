package android.dwkim.pr.tacotron

import android.content.Context
import android.dwkim.pr.tacotron.network.ApiClient
import android.dwkim.pr.tacotron.network.CustomCallback
import android.media.MediaPlayer
import android.net.Uri
import okhttp3.ResponseBody
import java.io.*

class PlayerUtil {
    fun getSoundFileAndPlay(context: Context, contents: String){
        ApiClient.API_CLIENT?.generate(
            0,
            contents
        )?.enqueue(
            CustomCallback<ResponseBody>().apply {
                successResponse = { response ->
                    response?.run {
                        writeResponseBodyToDisk(context, this)
                    }
                }
            }
        )
    }

    private fun writeResponseBodyToDisk(context: Context, body: ResponseBody) {
        val fileUri = context.getExternalFilesDir(null).toString() + File.separator + "test.wmv"

        val wmvFile = File(fileUri)

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val fileReader = ByteArray(4096)

            var fileSizeDownloaded: Long = 0

            inputStream = body.byteStream()
            outputStream = FileOutputStream(wmvFile)

            while (true) {
                val read = inputStream?.read(fileReader)

                if (read == null || read == -1) {
                    break
                }

                outputStream.write(fileReader, 0, read)

                fileSizeDownloaded += read.toLong()
            }

            outputStream.flush()

            MediaPlayer.create(
                context,
                Uri.parse(fileUri)
            ).apply {
                start()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }
}