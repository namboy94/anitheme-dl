package net.namibsun.themes_moe_dl.lib.parsing
/*
Copyright Hermann Krumrey<hermann@krumreyh.com>, 2017

This file is part of themes.moe-dl.

themes.moe-dl is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

themes.moe-dl is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with themes.moe-dl.  If not, see <http://www.gnu.org/licenses/>.
*/

import net.namibsun.themes_moe_dl.lib.utils.createDirectoryIfNotExists
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.logging.Logger
import javax.net.ssl.SSLException
import kotlin.concurrent.thread

/**
 * The Theme class models an anime theme song indexed on [themes.moe](https://themes.moe).
 *
 * It consists of a short description as displayed on [themes.moe](https://themes.moe),
 * as well as a URL to the video file of the theme song
 *
 * @param description The description of the theme song
 * @param url The URL to the theme song file
 */
class Theme constructor(val description: String, val url: String) {

    val logger: Logger = Logger.getLogger(Theme::class.qualifiedName)

    /**
     * Instance variable that is set to true whenever a download is progressing.
     */
    var downloading = false

    /**
     * Downloads the theme song file to the specified target directory using an automatic naming scheme
     *
     * The description is used as the file name, a suffix and prefix may also be supplied
     * The downloader can automatically convert the file into different file types. By default,
     * only the original .webm file is kept
     *
     * This method only generates a filename based off the information provided, then delegates
     * the file download and conversion to the [handleDownload] method
     *
     * @param targetDir The target directory in which the file should be saved
     * @param fileTypes The filetypes to convert the file into. Defaults to only .webm
     * @param prefix An optional prefix for the generated file name
     * @param suffix An optional suffix for the generated file name
     * @param retriesAllowed The amount of times the download should be retried before giving up.
     * @throws IOException If an IO error occurs (for example, the URL is invalid)
     * @throws FileSystemException If the directory could not be created
     */
    fun download(
            targetDir: String,
            fileTypes: List<FileTypes> = listOf(FileTypes.WEBM),
            prefix: String = "",
            suffix: String = "",
            retriesAllowed: Int = 0) {

        createDirectoryIfNotExists(targetDir)
        val filepath = Paths.get(targetDir, "$prefix${this.description}$suffix").toString()
        this.handleDownload(filepath, fileTypes, retriesAllowed)

    }

    /**
     * Handles the different cases of the existing file structure's state and delegates
     * downloading and converting.
     *
     * The [fileTypes] parameter specifies which media formats the file should be
     * converted to. By default, only the original .webm file is downloaded and left as-is.
     *
     * @param targetFile The file name of the target file, without any file type associated suffixes like .webm etc.
     * @param fileTypes The types of media files to convert the theme song into once downloaded
     * @param retriesAllowed The amount of times the download should be retried before giving up.
     * @throws IOException if something happened during the download
     */
    fun handleDownload(targetFile: String,
                       fileTypes: List<FileTypes> = listOf(FileTypes.WEBM),
                       retriesAllowed: Int = 0) {

        val extension = this.url.split(".").last()
        val target = "$targetFile.$extension"

        if (File(target).isFile) {
            logger.info { "$target exists. Skipping download." }
        }
        else if (this.url.startsWith("https://streamable.com")) {
            logger.info { "${this.url} is on streamable.com, which is not supported. Skipping download." }
            return // No conversion at end since file does not exist, hence we return
        }
        else {
            this.logger.info { "Downloading ${this.url} to $target" }
            this.downloadFile(target, retriesAllowed)
        }
        this.handleConversion(targetFile, target, fileTypes)
    }

    /**
     * Downloads the file from the URL to a local file.
     * Starts a separate thread that prints the current progress to the terminal
     * Handles retries of downloads
     * @param target The target file of the download in the local file system
     * @param retriesAllowed The amount of retries that are allowed
     */
    fun downloadFile(target: String, retriesAllowed: Int = 0) {

        var url = URL(this.url)
        var httpConnection = url.openConnection()
        httpConnection.addRequestProperty("User-Agent", "Mozilla/4.0")

        this.downloading = true
        var retryCount = 0

        this.printDownloadProgress(target, httpConnection.contentLength)

        while (this.downloading) {
            try {
                val data = httpConnection.inputStream
                Files.copy(data, Paths.get(target), StandardCopyOption.REPLACE_EXISTING)
                this.logger.info { "Download completed" }
                this.downloading = false

            } catch (e: Exception) {
                this.handleExceptionInDownload(e, target, retryCount++, retriesAllowed)
                // Re-establish connection
                url = URL(this.url)
                httpConnection = url.openConnection()
                httpConnection.addRequestProperty("User-Agent", "Mozilla/4.0")
            }
        }
    }

    /**
     * Handles an exception caught in the [downloadFile] method
     * Allows for retries, if the maximum amount of retries is exceeded, throws the exception again, after
     * aborting the download.
     * @param exception The caught exception
     * @param target The target file to which is being downloaded
     * @param retries The amount of retries already attempted
     * @param maxRetries The maximum amount of retries allowed
     */
    fun handleExceptionInDownload(exception: Exception, target: String, retries: Int, maxRetries: Int) {

        this.logger.severe { "Download of file ${this.url} failed" }

        when (exception) {
            is IOException,
            is SSLException -> {
                if (retries > maxRetries) {
                    this.logger.severe("Maximum number of retries attempted.")
                    this.abortDownload(target)
                    throw exception
                }
                else {
                    this.logger.info("Retrying download...")
                }
            }
            else -> {
                this.logger.severe("Unknown error occurred.")
                this.logger.fine(exception.toString())
                this.abortDownload(target)
                throw exception
            }
        }
    }

    /**
     * Aborts the download. Resets the [downloading] flag and deletes the target file
     * @param target The target file to delete on abort
     */
    fun abortDownload(target: String) {
        this.downloading = false
        this.logger.severe("Aborted Download of $target")
        if (File(target).exists()) {
            File(target).delete()
        }
    }

    /**
     * Starts a new thread which continuously prints the current progress to the terminal
     * @param target The target file. Used to check the current size
     * @param size The total size of the download target in bytes
     */
    fun printDownloadProgress(target: String, size: Int) {
        thread(start=true) {
            val downloadFile = File(target)

            while (this.downloading) {
                print("Progress: ${downloadFile.length() / 1000} KB / ${size / 1000} KB\r")
                Thread.sleep(500)
            }
        }
    }

    /**
     * Handles the conversion of the WEBM files to the various specified formats
     * @param name The name of the downloaded file without any extension
     * @param webmFile the path to the original webm file
     * @param fileTypes The file types to which to convert to
     */
    fun handleConversion(name: String, webmFile: String, fileTypes: List<FileTypes>) {

        fileTypeLoop@ for (fileType in fileTypes) {
            when (fileType) {
                FileTypes.WEBM -> continue@fileTypeLoop
                FileTypes.MP3 -> convertToMP3(name, webmFile)
            }
        }

        if (FileTypes.WEBM !in fileTypes && File(webmFile).exists()) {
            this.logger.info { "Deleting original .webm file" }
            File(webmFile).delete()
        }
    }

    /**
     * Converts a .webm file to mp3 using ffmpeg
     * @param name: The filename of the resulting mp3 file
     * @param webm: The path to the source .webm file
     */
    private fun convertToMP3(name: String, webm: String) {
        if (!File(name + ".mp3").exists()) {
            logger.info { "Converting $name to MP3." }
            Runtime.getRuntime().exec(arrayOf("ffmpeg", "-i", webm, "-acodec", "libmp3lame", "-aq", "4", name + ".mp3"))
                    .waitFor()
        }
        else {
            logger.fine("MP3 File exists, skipping: ${name + ".mp3"}")
        }
    }

    /**
     * Formats the [Theme] object for printing to the console
     *
     * This is done in the following format:
     *
     *     Description: URL
     *
     * @return The formatted String
     */
    override fun toString(): String {
        return "${this.description}: ${this.url}"
    }
}
