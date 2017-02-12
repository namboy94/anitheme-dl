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

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import java.util.logging.Logger

/**
 * ThemesMoeParser is a class that parses [themes.moe](https://themes.moe).
 *
 * The parser can be configured in a variety of ways to deliver different results
 * using a variety of optional parameters, which all default to true
 *
 * For example, the class may be called with a simple
 *
 *     ThemesMoeParser()
 *
 *
 * but say one wants to only include Openings and disregard duplicates
 *
 *     ThemesMoeParser(includeOp = true,
 *                     includeEd = false,
 *                     includeDuplicates = true)
 *
 *     or:
 *
 *     ThemesMoeParser(includeEd = false)
 *
 * @param includeOp Specifies if opening themes should be included in the result
 * @param includeEd Specifes if the ending themes should be included in the result
 * @param includeDuplicates Specifies if duplicate series should be fetched
 */
class ThemesMoeParser
    constructor(
            val includeOp: Boolean = true,
            val includeEd: Boolean = true,
            val includeDuplicates: Boolean = true
    ) {

    val logger: Logger = Logger.getLogger(ThemesMoeParser::class.simpleName)

    /**
     * The base URL for the [themes.moe](https://themes.moe) PHP API
     *
     * Used for all POST requests
     */
    val baseApiUrl = "https://themes.moe/includes"

    /**
     * Fetches all series for a user on one of the list services supported by
     * [themes.moe](https://themes.moe).
     *
     * A basic usage example would be:
     *
     *     ThemesMoeParser().fetchUserList("namboy94", MYANIMELIST)
     *
     * This will fetch all series for the user "namboy94" using the myanimelst
     * service of [themes.moe](https://themes.moe)
     *
     * @param username The username for which to retrieve the list for
     * @param listType The type of list to search for. Must be in the [ListTypes] enum
     * @return A List of [Series] objects found while parsing the result from [themes.moe](https://themes.moe)
     */
    fun fetchUserList(username: String, listType: ListTypes) : List<Series> {

        this.logger.info("Fetching ${listType.name} list for user $username")

        val request = Jsoup.connect("${this.baseApiUrl}/get_list.php")
                .data("username", username)
                .data("list", listType.value).post()

        return this.parseTable(request)

    }

    /**
     * Fetches all series in a generated [themes.moe](https://themes.moe) playlist
     *
     * The playlist is identified by the unique id that [themes.moe](https://themes.moe)
     * assigns to the playlist
     *
     * A basic usage example:
     *
     *     ThemesMoeParser().fetchPlayList(15214)  // Fetches all series for the playlist 15214
     *
     * @param playListId The unique Playlist ID
     * @return A List of [Series] objects found while parsing the result from [themes.moe](https://themes.moe)
     */
    fun fetchPlayList(playListId: Int) : List<Series> {

        this.logger.info("Fetching Playlist $playListId.")
        val request = Jsoup.connect("${this.baseApiUrl}/create_playlist.php").data("plist", "$playListId").post()
        return this.parseTable(normalizeTable(request))

    }

    /**
     * Fetches all series for a specified season of anime
     *
     * To do this, both a season and year parameter are required
     * Of course, only seasonal lists that exist on [themes.moe](https://themes.moe)
     * can be fetched.
     *
     * A basic usage example:
     *
     *     ThemesMoeParser().fetchSeasonList(2017, Seasons.WINTER)  // Fetches all series from the 2017 winter season
     *
     * @param year The year for which to fetch the seasonal list
     * @param season The season for which to fetch the seasonal list
     * @return A List of [Series] objects found while parsing the result from [themes.moe](https://themes.moe)
     */
    fun fetchSeasonList(year: Int, season: Seasons) : List<Series> {

        this.logger.info("Fetching Season ${season.name} for year $year")
        val request = Jsoup.connect("${this.baseApiUrl}/specific_list.php")
                .data("y", "$year")
                .data("s", season.value).post()
        return this.parseTable(request)

    }

    /**
     * Fetches the currently popular series from the popular list on [themes.moe](https://themes.moe)
     *
     * This list is of course subject to change whenever the popularity of theme songs
     * on [themes.moe](https://themes.moe) changes.
     *
     * Basic usage example:
     *
     *     ThemesMoeParser().fetchPopularList // Fetches all series from the popular list
     */
    fun fetchPopularList() : List<Series> {

        this.logger.info("Fetching popular series.")
        val request = Jsoup.connect("${this.baseApiUrl}/specific_list.php").data("id", "1").post()
        return this.parseTable(request)

    }

    /**
     * Searches [themes.moe](https://themes.moe) for opening and ending songs
     *
     * This method emulates the search form on [themes.moe](https://themes.moe)
     *
     * A basic usage example:
     *
     *     ThemesMoeParser().search("One Punch Man")  // Fetches all search results for 'One Punch Man'
     *
     * @param query The search term to search for
     * @return A List of [Series] objects found while parsing the result from [themes.moe](https://themes.moe)
     */
    fun search(query: String) : List<Series> {

        this.logger.info("Searching for: $query")

        val request = Jsoup.connect("${this.baseApiUrl}/anime_search.php")
                .data("search", "-1")
                .data("name", query).post()

        return this.parseTable(normalizeTable(request))

    }

    /**
     * Parses a table Element from [themes.moe](https://themes.moe)
     *
     * The list tables from [themes.moe](https://themes.moe) contain 'td' elements,
     * which each contain two 'tr' elements. The first one of these specifies the
     * information about a particular series, like the name and/or [myanimelist.net](https://myanimelist.net)
     * or [hummingbird.me](https://hummingbird.me) URL.
     *
     * The second 'tr' element contains multiple theme song elements, that each have a description and
     * a video file URL.
     *
     * @param request The request to parse, no selects should be called before calling this method
     * @return A List of [Series] objects generated while parsing the table
     */
    private fun parseTable(request: Document) : List<Series> {

        val history = mutableListOf("")

        this.logger.fine("HTML Data to parse:\n$request")

        val series: MutableList<Series> = mutableListOf()
        val table = request.select("tbody").select("tr")

        for (entry in table) {

            val name = entry.select("td")[0].text()
            this.logger.info("Parsing $name")

            if (this.includeDuplicates && name in history) {
                this.logger.info("Skipping Series, already in history and duplicates disabled")
                continue
            }
            else {
                this.logger.info("Adding $name to history")
                history.add(name)
                val themes = parseEntries(entry)

                if (themes.isNotEmpty()) {
                    series.add(Series(name, themes))
                    this.logger.info("Adding series $name with ${themes.size} themes")
                }
                else {
                    this.logger.info("Skipping Series $name. No valid themes found")
                }

            }
        }
        return series
    }

    /**
     * Separately parses the links of a series
     *
     * This is done by parsing the second 'td' element and using the 'a' tags to retrieve the
     * description and video URL for a Series
     *
     * @param entry The 'tr' element of the series
     * @return A List of Theme objects generated by the parser
     */
    private fun parseEntries(entry: Element) : List<Theme> {

        val parts = entry.select("td")[1].select("a")
        val themes: MutableList<Theme> = mutableListOf()

        for (theme in parts) {

            val description = theme.text()
            val url = theme.attr("href")

            if (!this.includeOp && description.toUpperCase().startsWith("OP")) {
                this.logger.info("Skipping $description because Openings are disabled")
                continue
            }
            else if (!this.includeEd && description.toUpperCase().startsWith("ED")) {
                this.logger.info("Skipping $description because Endings are disabled")
                continue
            }
            else {
                this.logger.info("Adding Theme: {$description: $url}")
                themes.add(Theme(description, url))
            }
        }
        return themes
    }

    /**
     * OK, so this is a weird one.
     *
     * The 'create_playlist.php' and 'anime_search.php' API endpoints return some weird form of malformed HTML.
     * It contains '["', and everything after it is Html-entity encoded.
     *
     * This method tries to format the POST request result
     *
     * @param request The request document to normalize
     * @return The normalized request
     */
    private fun normalizeTable(request: Document) : Document {
        val normalized = Parser.unescapeEntities(request.toString(), true)
                .replace("\\/", "/")
                .replace("\\\"", "")
                .replace("[\"", "")
        return Jsoup.parse(normalized)
    }
}
