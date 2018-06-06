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

package net.namibsun.anitheme.dl.cli

import net.namibsun.anitheme.dl.lib.parsing.FileTypes
import net.namibsun.anitheme.dl.lib.parsing.Series
import net.namibsun.anitheme.dl.lib.parsing.ListTypes
import net.namibsun.anitheme.dl.lib.parsing.Seasons
import net.namibsun.anitheme.dl.lib.parsing.ThemesMoeParser
import org.apache.commons.cli.CommandLine

/**
 * The downloader class downloads anime themes from [themes.moe](https://themes.moe) using the
 * parsed arguments from [ArgumentParser].
 *
 * @param options The parsed command line arguments
 */
class Downloader constructor(val options: CommandLine) {

    /**
     * The parser to use. It can be specified if openings or endings are excluded using
     * the command line arguments
     */
    val parser = ThemesMoeParser(
            includeOp = !options.hasOption("no-ops"),
            includeEd = !options.hasOption("no-eds")
    )

    /**
     * The mode of the downloader, as parsed by the [ArgumentParser]
     */
    val mode: String =
            if (this.options.hasOption("mode")) {
                this.options.getOptionValue("mode")
            } else {
                this.options.args[0]
            }

    /**
     * The target destination directory of the Files.
     */
    val destination: String =
            if (options.hasOption("destination")) {
                options.getOptionValue("destination")
            } else {
                "themes-moe"
            }

    /**
     * Fetches the Anime [Series] from [themes.moe](https://themes.moe) and downloads them
     * in accordance with the parsed command line arguments
     */
    fun download() {

        val formats = this.determineFormats()

        println("Fetching Series info...")
        val results = this.parse()
        println("Done. Starting Download to ${this.destination}...")
        for (result in results) {

            try {
                println("Downloading ${result.name}...")
                result.download(this.destination, formats, 3)
                println("\nDone.")
            } catch (e: Exception) {
                println("Download of ${result.name} failed.")
            }
        }
    }

    /**
     * Uses the [ThemesMoeParser] to fetch the [Series] containing [Theme]s from
     * [themes.moe](https://themes.moe)
     *
     * @return A list of [Series] objects
     */
    @Suppress("KDocUnresolvedReference")
    private fun parse(): List<Series> {
        var results = listOf<Series>()
        when (this.mode) {
            "popular" -> {
                results = parser.fetchPopularList()
            }
            "mal-userlist" -> {
                results = parser.fetchUserList(
                        this.options.getOptionValue("mal-username"),
                        ListTypes.MYANIMELIST)
            }
            "seasonal" -> {
                results = parser.fetchSeasonList(
                        this.options.getOptionValue("year").toInt(),
                        Seasons.valueOf(this.options.getOptionValue("season").toUpperCase())) }
            "search" -> {
                results = parser.search(this.options.getOptionValue("term"))
            }
            "playlist" -> {
                results = parser.fetchPlayList(this.options.getOptionValue("playlist-id").toInt())
            }
        }
        return results
    }

    /**
     * Determines the File formats in which the downloaded files should be converted to.
     * @return A list of file types that are applicable
     */
    private fun determineFormats(): List<FileTypes> {

        val formats = if (this.options.hasOption("formats")) this.options.getOptionValue("formats") else "WEBM"
        val splitFormats = formats.split(",")
        val formatList: MutableList<FileTypes> = mutableListOf()

        for (format in splitFormats) {
            try {
                formatList.add(FileTypes.valueOf(format.toUpperCase().trim()))
            } catch (e: IllegalArgumentException) {
                println("Unsupported file format: $format")
            }
        }
        formatList.map { format -> println("Format '${format.name}' specified") }
        return formatList
    }
}