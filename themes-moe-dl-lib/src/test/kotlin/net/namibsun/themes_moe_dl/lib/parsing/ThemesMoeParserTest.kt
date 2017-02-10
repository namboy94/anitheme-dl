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

import org.junit.Test
import org.junit.Assert.assertTrue

/**
 * Unit Testing class that tests the [ThemesMoeParser] class
 */
class ThemesMoeParserTest {
    val standardParser = ThemesMoeParser()

    /**
     * Tests if the parser fetches the list of series for a specific
     * [myanimelist.net](https://myanimelist.net) user
     *
     * This is done by first fetching all results for the user, then additionally checking if the
     * series "91 days" is present in the resulting list
     */
    @Test
    fun testFetchingMalUserList() {
        val results = this.standardParser.fetchUserList("namboy94", ListTypes.MYANIMELIST)
        this.validateResults(results, arrayOf("91 Days"))
    }

    /*
    @Test
    fun testFetchingSeasonalList() {
        val results = this.standardParser.fetchSeasonList(2016, Seasons.WINTER)
        validateResults(results, arrayOf())
    }

    @Test
    fun testFetchingSearchResult() {
        val results = this.standardParser.search("d-frag")
        validateResults(results, arrayOf("D-Frag!"))
    }

    @Test
    fun testFetchingPopularList() {
        val results = this.standardParser.fetchPopularList()
        validateResults(results, arrayOf())
    }

    @Test
    fun testFetchingPlaylist() {
        val results = this.standardParser.fetchPlayList(12345)
        validateResults(results, arrayOf())
    }
    */

    fun validateResults(results: List<Series>, seriesNames: Array<String>) {
        assertTrue(results.isNotEmpty())
        for (seriesName in seriesNames) {
            assertTrue(results.any { it.name == seriesName })
        }
    }
}