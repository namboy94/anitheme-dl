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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

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
 * but say one wants to only include series that are currently being watched:
 *
 *     ThemesMoeParser(includeCompleted = false,
 *                     includeOnHold = false,
 *                     includeDropped = false,
 *                     includeOp = false,
 *                     includeEd = false,
 *                     includeDuplicates = false)
 *
 * or say one wants to fetch all series but exclude the Ending themes, which would result in the call
 *
 *     ThemesMoeParser(includeEd = false)
 *
 * @param includeCompleted Specifies if completed series are fetched, if applicable
 * @param includeCurrentlyWatching Specifies if series currently being watched is fetched, if applicable
 * @param includeDropped Specifies if dropped series are fetched, if applicable
 * @param includeOnHold Specified if series that are on hold should be fetched, if applicable
 * @param includeOp Specifies if opening themes should be included in the result
 * @param includeEd Speciifes if the ending themes should be included in the result
 */
class ThemesMoeParser
    constructor(
            val includeCompleted: Boolean = true,
            val includeCurrentlyWatching: Boolean = true,
            val includeOnHold: Boolean = true,
            val includeDropped: Boolean = true,
            val includeOp: Boolean = true,
            val includeEd: Boolean = true,
            val includeDuplicates: Boolean = true
    ) {

    val logger = KotlinLogging.logger {}

    /**
     * The base URL for the [themes.moe](https://themes.moe) API
     */
    val baseUrl = "https://themes.moe/includes"

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

        this.logger.info { "Fetching ${listType.name} list for user $username" }

        val request = Jsoup.connect("${this.baseUrl}/get_list.php")
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

        this.logger.info { "Fetching Playlist $playListId." }
        val request = Jsoup.connect("${this.baseUrl}/?plist=$playListId").get()
        return this.parseTable(request)

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

        this.logger.info { "Fetching Season ${season.name} for year $year" }
        val request = Jsoup.connect("${this.baseUrl}/?s=${season.value}&y=$year").get()
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

        this.logger.info { "Fetching popular series." }
        val request = Jsoup.connect("${this.baseUrl}/?cl=1").get()
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

        this.logger.info { "Searching for: $query" }

        val request = Jsoup.connect("${this.baseUrl}/anime_search.php")
                .data("search", "-1")
                .data("name", query).post()

        return this.parseTable(request)

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

        this.logger.debug("HTML Data to parse:\n$request")

        val series: MutableList<Series> = mutableListOf()
        val table = request.select("tbody").select("tr")

        for (entry in table) {

            val name = entry.select("td")[0].text()
            val parts = entry.select("td")[1].select("a")

            val themes: MutableList<Theme> = mutableListOf()
            for (theme in parts) {
                val description = theme.text()
                val url = theme.attr("href")

                themes.add(Theme(description, url))
            }
            series.add(Series(name, themes))
        }
        return series
    }
}
