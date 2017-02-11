package net.namibsun.themes_moe_dl.cli

import net.namibsun.themes_moe_dl.lib.parsing.Seasons
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.GnuParser
import org.apache.commons.cli.Options
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.UnrecognizedOptionException
import kotlin.system.exitProcess

class ArgumentParser constructor(val args: Array<String>) {

    fun parse() : CommandLine {
        val options = this.intializeOptions()

        try {
            val results = GnuParser().parse(options, this.args)
            this.handleHelpMessage(results, options)
            this.validateArguments(results)
            return results
        } catch (e: UnrecognizedOptionException) {
            println(e.message!!)
            exitProcess(1)
        }
    }

    private fun intializeOptions() : Options {

        val options = Options()
        options.addOption("h", "help", false, "Prints this help message")
        options.addOption("o", "no-ops", false, "Disables Openings")
        options.addOption("e", "no-eds", false, "Disables Endings")
        options.addOption("m", "mode", true, "The mode to run the downloader in. Can also be used as a positional" +
                "argument.\nCurrently available:\n" +
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

        return options

    }

    private fun handleHelpMessage(results: CommandLine, options: Options) {
        if (results.hasOption("help")) {
            val formatter = HelpFormatter()
            formatter.printHelp("themes.moe Downloader CLI", options)
            exitProcess(0)
        }
    }

    private fun validateArguments(results: CommandLine) {

        if (!results.hasOption("mode") && results.args.isEmpty()) {
            println("Option 'mode' is required. Use --help for more information")
            exitProcess(0)
        }

        val mode = if (results.hasOption("mode")) results.getOptionValue("mode") else results.args[0]

        when (mode) {
            "popular" -> {}
            "mal-userlist" -> { validateMalUserListArguments(results) }
            "seasonal" -> { validateSeasonalArguments(results) }
            "search" -> { validateSearchArguments(results) }
            "playlist" -> { validatePlaylistArguments(results) }
            else -> { this.systemExit("No valid mode passed. See --help for more information") }
        }
    }

    private fun validateMalUserListArguments(results: CommandLine) {

        if (!results.hasOption("mal-username")) {
            this.systemExit("The myanimelist.net username is required. Pass it with the --mal-username option")
        }
    }

    private fun validateSeasonalArguments(results: CommandLine) {

        if (!results.hasOption("season") || !results.hasOption("year")) {
            this.systemExit("Seasonal lists require a season and a year passed using --year and --season")
        }
        else {
            try {
                results.getOptionValue("year").toInt()
                Seasons.valueOf(results.getOptionValue("season").toUpperCase())
            } catch (e: NumberFormatException) {
                this.systemExit("The year must be a whole number")
            } catch (e: IllegalArgumentException) {
                this.systemExit("${results.getOptionValue("season")} is not a valid season")
            }
        }
    }

    private fun validateSearchArguments(results: CommandLine) {

        if (!results.hasOption("term")) {
            this.systemExit("The search term must be provided using --term")
        }

    }

    private fun validatePlaylistArguments(results: CommandLine) {
        if (!results.hasOption("playlist-id")) {
            this.systemExit("A playlist ID must be specified with --playlist-id")
        }
        else {
            try {
                results.getOptionValue("playlist-id").toInt()
            } catch (e: NumberFormatException) {
                this.systemExit("The playlist ID must be an Integer value")
            }
        }
    }

    private fun systemExit(message: String) {
        println(message)
        exitProcess(1)
    }

}