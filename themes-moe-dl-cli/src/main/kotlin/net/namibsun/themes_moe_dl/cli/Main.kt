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

import net.namibsun.themes_moe_dl.lib.parsing.Series
import net.namibsun.themes_moe_dl.lib.parsing.ThemesMoeParser
import org.apache.commons.cli.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {

    val options = parseArgs(args)
    val themesMoeParser = generateThemesMoeparserFromOptions(options)
    executeThemesMoeParser(options, themesMoeParser)
}

fun parseArgs(args: Array<String>): CommandLine {

    val options = Options()
    options.addOption("h", "help", false, "Prints this help message")
    options.addOption("u", "user", false, "Specifies the myanimelist.net user when downloading the user's theme song list ")
    options.addOption("o", "no-ops", false, "Disables Openings")
    options.addOption("e", "no-eds", false, "Disables Endings")
    options.addOption("m", "mode", true, "The mode to run the downloader in. Currently available: popular")

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

    val results: List<Series>
    if(options.getOptionValue("mode") == "popular") {
        results = themesMoeParser.fetchPopularList()
    }
    else {
        println("No valid mode passed. See --help for more information")
        exitProcess(0)
    }

    for (result in results) {
        result.download("here")
    }


}