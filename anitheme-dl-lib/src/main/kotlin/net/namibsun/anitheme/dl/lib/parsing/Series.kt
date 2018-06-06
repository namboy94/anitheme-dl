/*
Copyright 2016 Hermann Krumrey <hermann@krumreyh.com>

This file is part of anitheme-dl.

anitheme-dl is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

anitheme-dl is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with anitheme-dl.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.anitheme.dl.lib.parsing

import net.namibsun.anitheme.dl.lib.utils.createDirectoryIfNotExists
import java.nio.file.Paths
import java.util.logging.Logger

/**
 * Series is a class that models an anime Series on [themes.moe](https://themes.moe-dl)
 *
 * It consists of a name, which is displayed on [themes.moe](https://themes.moe-dl),
 * and a [List] of [Theme] objects attributed to this series.
 *
 * @param name The name of the series
 * @param themes The [List] of [Theme] objects attributed to this series
 */
class Series constructor(val name: String, val themes: List<Theme>) {

    val logger: Logger = Logger.getLogger("Series")

    /**
     * Downloads all theme songs of this series to a specified directory
     * Optionally converts the file into different formats
     *
     * @param target The target directory in which to save the files
     * @param fileTypes The fileTypes to convert the file into. Defaults to only .webm
     * @param retriesAllowed The amount of times the program may re-attempt a download
     */
    fun download(target: String, fileTypes: List<FileTypes> = listOf(FileTypes.WEBM), retriesAllowed: Int = 0) {

        val path = Paths.get(target, this.name).toString()
        createDirectoryIfNotExists(path)

        logger.info("Starting download of series ${this.name}")
        for (theme in this.themes) {
            theme.download(path, fileTypes, retriesAllowed = retriesAllowed)
        }
    }

    /**
     * Converts the [Series] object into a string for printing to the console
     *
     * The string is formatted like this:
     *
     *     Name: [Theme1, Theme2, Theme3, ...]
     *
     * @return the formatted [String]
     */
    override fun toString(): String {
        return "${this.name}: ${this.themes}"
    }
}