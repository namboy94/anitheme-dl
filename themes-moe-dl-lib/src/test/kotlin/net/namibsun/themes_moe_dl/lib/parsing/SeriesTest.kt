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
import org.junit.Assert.assertEquals

/**
 * Unit testing class that tests the Series class
 */
class SeriesTest {

    val emptyTestSeries = Series("Testseries", listOf())

    /**
     * Tests the Series String generating method
     */
    @Test
    fun testToString() {
        assertEquals(this.emptyTestSeries.toString(), "Testseries: []")
    }

}