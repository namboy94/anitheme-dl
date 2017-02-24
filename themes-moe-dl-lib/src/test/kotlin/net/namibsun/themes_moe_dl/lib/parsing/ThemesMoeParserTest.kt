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
import org.junit.Assert.assertEquals

/**
 * Unit Testing class that tests the [ThemesMoeParser] class
 */
class ThemesMoeParserTest {
    val standardParser = ThemesMoeParser()
    val noResultParser = ThemesMoeParser(includeOp = false, includeEd = false)
    val noOpParser = ThemesMoeParser(includeOp = false)
    val noEdParser = ThemesMoeParser(includeEd = false)

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

    /**
     * Tests if the parser correctly fetches the seasonal anime lists
     *
     * While doing so, the anime list of Winter 2017 is parsed and checked for the inclusion of a couple of anime
     * series, while two further tests are conducted to make sure that the parser does not fail on
     * seasons that are too far in the past or in the future, but rather return an empty list
     */
    @Test
    fun testFetchingSeasonalList() {

        val results = this.standardParser.fetchSeasonList(2017, Seasons.WINTER)
        validateResults(results, arrayOf("Youjo Senki", "Gintama.", "Little Witch Academia"))

        assertTrue(this.standardParser.fetchSeasonList(1900, Seasons.SUMMER).isEmpty()) // Season too early in past
        assertTrue(this.standardParser.fetchSeasonList(3000, Seasons.FALL).isEmpty()) // Season in future
    }

    /**
     * Tests fetching the list of popular anime.
     */
    @Test
    fun testFetchingPopularList() {
        val results = this.standardParser.fetchPopularList()
        validateResults(results, arrayOf("Sword Art Online", "Steins;Gate", "Shingeki no Kyojin"))
    }

    /**
     * Tests fetching results from the search method. One valid request and one that does not deliver any results
     */
    @Test
    fun testFetchingSearchResult() {
        val results = this.standardParser.search("d-frag")
        validateResults(results, arrayOf("D-Frag!"))
        assertTrue(this.standardParser.search("fwkjadijsadjsadsadljsajdalsdd").isEmpty())
    }

    /**
     * Tests fetching a specific playlist.
     * Also tests it on a playlist that is unlikely to return any results (negative ID)
     */
    @Test
    fun testFetchingPlaylist() {
        val results = this.standardParser.fetchPlayList(327031)
        validateResults(results, arrayOf("Sword Art Online", "Steins;Gate", "Shingeki no Kyojin"))
        assertTrue(this.standardParser.fetchPlayList(-1000).isEmpty())
    }

    /**
     * Test the parser with all filters set to 'false'
     */
    @Test
    fun testNoOpNoEdNoDuplicates() {
        assertTrue(this.noResultParser.fetchPopularList().isEmpty())
    }

    /**
     * Tests the parser with Openings disabled
     */
    @Test
    fun testNoOps() {
        validateLimitedResultsWithOneTheme(this.noOpParser.search("d-frag"), "D-Frag!", "ED2")
    }

    /**
     * Tests the parser with Endings Disabled
     */
    @Test
    fun testNoEds() {
        validateLimitedResultsWithOneTheme(this.noEdParser.search("d-frag"), "D-Frag!", "OP")
    }

    /**
     * Used by [testNoEds] and [testNoOps] to check the results (reduces code duplication)
     */
    fun validateLimitedResultsWithOneTheme(results: List<Series>, series: String, theme: String) {
        assertEquals(1, results.size)
        assertEquals(results[0].name, series)
        assertEquals(results[0].themes.size, 1)
        assertEquals(results[0].themes[0].description, theme)
    }

    /**
     * Checks if a parse result is not empty and contains series with specified names
     *
     * @param results The parse result
     * @param seriesNames The names that should be in the result
     */
    fun validateResults(results: List<Series>, seriesNames: Array<String>) {
        assertTrue(results.isNotEmpty())
        for (seriesName in seriesNames) {
            assertTrue(results.any { it.name == seriesName })
        }
    }
}