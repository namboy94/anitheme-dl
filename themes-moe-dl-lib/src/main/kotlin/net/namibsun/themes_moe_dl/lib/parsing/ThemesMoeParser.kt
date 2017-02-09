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
import org.jsoup.select.Elements

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
            private val includeCompleted: Boolean = true,
            private val includeCurrentlyWatching: Boolean = true,
            private val includeOnHold: Boolean = true,
            private val includeDropped: Boolean = true,
            private val includeOp: Boolean = true,
            private val includeEd: Boolean = true,
            private val includeDuplicates: Boolean = true
    ) {

    /**
     * The base URL for the [themes.moe](https://themes.moe) API
     */
    private val baseUrl = "https://themes.moe/includes"

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
     * @return A [List] of [Series] objects found while parsing the result from [themes.moe](https://themes.moe)
     */
    fun fetchUserList(username: String, listType: ListTypes) : List<Series> {

        val request = Jsoup.connect("${this.baseUrl}/get_list.php")
                .data("username", username)
                .data("list", listType.value).post()

        val table = request.select("tbody").select("tr")
        return this.parseTable(table)

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
     * @param table The table to parse, must have called '.select("tbody").select("tr")' before passing to this method
     * @return A [List] of [Series] objects generated while parsing the table
     */
    private fun parseTable(table: Elements) : List<Series> {

        val series: MutableList<Series> = mutableListOf()

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
