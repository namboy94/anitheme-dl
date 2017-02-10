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

import mu.KotlinLogging
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.FileSystemException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

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

    private val logger = KotlinLogging.logger {}

    /**
     * Downloads the theme song file to the specified target directory using an automatic naming scheme
     *
     * The description is used as the file name, a suffix and prefix may also be supplied
     * The downloader can automatically convert the file into different file types. By default,
     * only the original .webm file is kept
     *
     * This method only generates a filename based off the information provided, then delegates
     * the file download and conversion to the [downloadFile] method
     *
     * @param targetDir The target directory in which the file should be saved
     * @param fileTypes The filetypes to convert the file into. Defaults to only .webm
     * @param prefix An optional prefix for the generated file name
     * @param suffix An optional suffix for the generated file name
     * @throws IOException If an IO error occurs (for example, the URL is invalid)
     * @throws FileSystemException If the directory could not be created
     */
    fun download(
            targetDir: String,
            fileTypes: Array<FileTypes> = arrayOf(FileTypes.WEBM),
            prefix: String = "",
            suffix: String = "") {

        val directory = File(targetDir)

        if (!directory.isDirectory) {
            this.logger.info { "Creating directory $targetDir" }
            val directoryCreationStatus = directory.mkdirs()

            if (directoryCreationStatus) { this.logger.info { "Directory successfully created" } }
            else {
                this.logger.error { "Directory Creation failed" }
                throw FileSystemException("Creation of directory $targetDir failed")
            }
        }

        val filepath = Paths.get(targetDir, "$prefix${this.description}$suffix").toString()
        this.downloadFile(filepath, fileTypes)

    }

    /**
     * Downloads the theme song file to a specified file name.
     *
     * The [fileTypes] parameter specifies which media formats the file should be
     * converted to. By default, only the original .webm file is downloaded and left as-is.
     *
     * @param targetFile The file name of the target file, without any file type associated suffixes like .webm etc.
     * @param fileTypes The types of media files to convert the theme song into once downloaded
     * @throws IOException if something happened during the download
     */
    fun downloadFile(targetFile: String, fileTypes: Array<FileTypes> = arrayOf(FileTypes.WEBM)) {

        val fileInfo = this.url.split(".")
        val target = targetFile + "." + fileInfo[fileInfo.size - 1]

        if (File(target).isFile) {
            logger.info { "$target exists. Skipping download." }
        }
        else if (this.url.startsWith("https://streamable.com")) {
            logger.info { "${this.url} is on streamable.com, which is not supported. Skipping download." }
            return // No conversion at end since file does not exist, hence we return
        }
        else {

            this.logger.info { "Downloading ${this.url} to $target" }

            val url = URL(this.url)
            val httpConnection = url.openConnection()
            httpConnection.addRequestProperty("User-Agent", "Mozilla/4.0")

            try {
                val data = httpConnection.inputStream
                Files.copy(data, Paths.get(target), StandardCopyOption.REPLACE_EXISTING)
                this.logger.info { "Download completed" }
            } catch (e: IOException) {
                this.logger.trace(e) { "Download of file ${this.url} failed" }
                throw e
            }

            fileTypeLoop@ for (fileType in fileTypes) {
                when (fileType) {
                    FileTypes.WEBM -> continue@fileTypeLoop
                    else -> {}
                }
            }
        }

        if (FileTypes.WEBM !in fileTypes) {
            this.logger.info { "Deleting original .webm file" }
            File(targetFile + ".webm").delete()
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
