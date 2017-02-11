package net.namibsun.themes_moe_dl.cli

import net.namibsun.themes_moe_dl.lib.parsing.ListTypes
import net.namibsun.themes_moe_dl.lib.parsing.Seasons
import net.namibsun.themes_moe_dl.lib.parsing.Series
import net.namibsun.themes_moe_dl.lib.parsing.ThemesMoeParser
import org.apache.commons.cli.CommandLine

class Downloader constructor(val options: CommandLine) {

    val parser = ThemesMoeParser(
            includeOp = !options.hasOption("no-ops"),
            includeEd = !options.hasOption("no-eds")
    )

    val mode: String =
            if (this.options.hasOption("mode")) {
                this.options.getOptionValue("mode")
            }
            else {
                this.options.args[0]
            }

    val destination: String =
            if (options.hasOption("destination")) {
                "themes-moe"
            }
            else {
                options.getOptionValue("destination")
            }

    fun download() {

        val results = this.parse()
        for (result in results) {
            result.download(this.destination)
        }

    }

    private fun parse() : List<Series> {
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

}