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

/**
 * Unit Testing class that tests the [ThemesMoeParser] class
 */
class ThemesMoeParserTest {
    val standardParser = ThemesMoeParser()

    /**
     * Tests if the parser fetches the list of series for a specific
     * [myanimelist.net](https://myanimelist.net) user
     */
    @Test
    fun testFetchingMalUserList() {
        //val results = standardParser.fetchUserList("namboy94", MYANIMELIST)
    }

    /**
     * Tests if the parser fetches the list of series for a specific
     * [hummingbird.me](https://hummingbird.me) user
     */
    @Test
    fun testFetchingHummingbirdUserList() {
        //val results = standardParser.fetchUserList("namboy94", HUMMINGBIRD)
    }

}