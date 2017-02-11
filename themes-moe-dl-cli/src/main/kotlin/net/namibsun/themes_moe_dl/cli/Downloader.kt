package net.namibsun.themes_moe_dl.cli

import net.namibsun.themes_moe_dl.lib.parsing.ThemesMoeParser
import net.namibsun.themes_moe_dl.lib.parsing.Series
import net.namibsun.themes_moe_dl.lib.parsing.ListTypes
import net.namibsun.themes_moe_dl.lib.parsing.Seasons
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
            }
            else {
                this.options.args[0]
            }

    /**
     * The target destination directory of the Files.
     */
    val destination: String =
            if (options.hasOption("destination")) {
                options.getOptionValue("destination")
            }
            else {
                "themes-moe"
            }

    /**
     * Fetches the Anime [Series] from [themes.moe](https://themes.moe) and downloads them
     * in accordance with the parsed command line arguments
     */
    fun download() {

        val results = this.parse()
        for (result in results) {
            result.download(this.destination)
        }
    }

    /**
     * Uses the [ThemesMoeParser] to fetch the [Series] containing [Theme]s from
     * [themes.moe](https://themes.moe)
     *
     * @return A list of [Series] objects
     */
    @Suppress("KDocUnresolvedReference")
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