package net.namibsun.themes_moe_dl.lib.parsing

import java.nio.file.Paths

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

    /**
     * Downloads the theme song file to the specified target directory using an automatic naming scheme
     *
     * The description is used as the file name, a suffix and prefix may also be supplied
     * The downloader can automatically convert the file into different file types. By default,
     * only the original .webm file is kept
     *
     * This method only generates a filename based off the information provided, then delegates
     * the file download and conversion to the other [download] method
     *
     * @param targetDir The target directory in which the file should be saved
     * @param fileTypes The filetypes to convert the file into. Defaults to only .webm
     * @param prefix An optional prefix for the generated file name
     * @param suffix An optional suffix for the generated file name
     */
    fun download(
            targetDir: String,
            fileTypes: Array<FileTypes> = arrayOf(FileTypes.WEBM),
            prefix: String = "",
            suffix: String = "") {

        val filepath = Paths.get(targetDir, "$prefix${this.description}$suffix").toString()
        this.download(filepath, fileTypes)
    }

    /**
     * Downloads the theme song file to a specified file name.
     *
     * The [fileTypes] parameter specifies which media formats the file should be
     * converted to. By default, only the original .webm file is downloaded and left as-is.
     *
     * @param targetFile: The file name of the target file, without any file type associated suffixes like .webm etc.
     * @param fileTypes: The types of media files to convert the theme song into once downloaded
     */
    fun download(targetFile: String, fileTypes: Array<FileTypes> = arrayOf(FileTypes.WEBM)) {
        println(targetFile)
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