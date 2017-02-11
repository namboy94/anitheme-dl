package net.namibsun.themes_moe_dl.cli
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

import net.namibsun.themes_moe_dl.lib.parsing.ListTypes
import net.namibsun.themes_moe_dl.lib.parsing.Seasons
import net.namibsun.themes_moe_dl.lib.parsing.Series
import net.namibsun.themes_moe_dl.lib.parsing.ThemesMoeParser
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.GnuParser
import org.apache.commons.cli.Options
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.UnrecognizedOptionException
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    val options = parseArgs(args)
    val themesMoeParser = generateThemesMoeparserFromOptions(options)
    executeThemesMoeParser(options, themesMoeParser)
}

fun parseArgs(args: Array<String>): CommandLine {

    val options = Options()
    options.addOption("h", "help", false, "Prints this help message")
    options.addOption("o", "no-ops", false, "Disables Openings")
    options.addOption("e", "no-eds", false, "Disables Endings")
    options.addOption("m", "mode", true, "The mode to run the downloader in. Currently available:\n" +
            "- popular:      Downloads all themes from the popular list on themes.moe\n" +
            "- mal-userlist: Downloads all themes associated with a user's MyAnimeList account\n" +
            "- seasonal:     Downloads all themes in a specific season\n" +
            "- search:       Downloads all results of a search query\n" +
            "- playlist:     Downloads all themes from a specific playlist")
    options.addOption("d", "destination", true, "Specifies a destination directory for the downloaded files")
    options.addOption("u", "mal-username", true, "Specifies the myanimelist.net user in the 'mal-userlist' mode ")
    options.addOption("y", "year", true, "Specifies the year in the 'seasonal' mode")
    options.addOption("s", "season", true, "Specifies the season in the 'seasonal' mode.\n" +
            "Must be one of the following:\n" +
            "- SUMMER\n- WINTER\n- FALL\n- SPRING")
    options.addOption("i", "playlist-id", true, "specifies the playlist ID in the 'playlist' mode")
    options.addOption("t", "term", true, "specifies the search term in the 'search' mode")

    val parser = GnuParser()

    try {

        val results = parser.parse(options, args)

        if (results.hasOption("help")) {
            val formatter = HelpFormatter()
            formatter.printHelp("themes.moe Downloader CLI", options)
        }
        else if (!results.hasOption("mode")) {
            println("Option 'mode' is required. Use --help for more information")
            exitProcess(0)
        }

        return results

    } catch (e: UnrecognizedOptionException) {
        println(e.message)
        exitProcess(0)
    }
}

fun generateThemesMoeparserFromOptions(options: CommandLine): ThemesMoeParser {
    return ThemesMoeParser(includeOp = !options.hasOption("no-ops"), includeEd = !options.hasOption("no-eds"))
}

fun executeThemesMoeParser(options: CommandLine, themesMoeParser: ThemesMoeParser) {

    var results: List<Series> = listOf()

    when (options.getOptionValue("mode")) {
        "popular" -> results = themesMoeParser.fetchPopularList()
        "mal-userlist" -> {
            if (!options.hasOption("mal-username")) {
                println("The myanimelist.net username is required. Pass it with the --mal-username option")
            }
            else {
                results = themesMoeParser.fetchUserList(options.getOptionValue("mal-username"), ListTypes.MYANIMELIST)
            }
        }
        "seasonal" -> {
            if (!options.hasOption("season") || !options.hasOption("year")) {
                println("Seasonal lists require a season and a year passed using --year and --season")
            }
            else {
                try {
                    val year = options.getOptionValue("year").toInt()
                    val season = Seasons.valueOf(options.getOptionValue("season").toUpperCase())
                    results = themesMoeParser.fetchSeasonList(year, season)
                } catch (e: NumberFormatException) {
                    println("The year must be a whole number")
                } catch (e: IllegalArgumentException) {
                    println("${options.getOptionValue("season")} is not a valid season")
                }
            }
        }
        "playlist" -> {
            if (!options.hasOption("playlist-id")) {
                println("A playlist ID must be specified with --playlist-id")
            }
            else {
                try {
                    val playlist = options.getOptionValue("playlist-id").toInt()
                    results = themesMoeParser.fetchPlayList(playlist)
                } catch (e: NumberFormatException) {
                    println("The playlist ID must be an Integer value")
                }
            }
        }
        "search" -> {
            if (!options.hasOption("term")) {
                println("The search term must be provided using --term")
            }
            else {
                results = themesMoeParser.search(options.getOptionValue("term"))
            }
        }
        else -> {
            println("No valid mode passed. See --help for more information")
        }
    }

    for (result in results) {
        if (options.hasOption("destination")) {
            result.download(options.getOptionValue("destination"))
        }
        else {
            result.download("themes-moe")
        }
    }
}