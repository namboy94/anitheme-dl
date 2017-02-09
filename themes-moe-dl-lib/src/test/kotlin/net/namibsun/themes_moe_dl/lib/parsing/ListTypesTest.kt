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
import org.junit.Assert.assertArrayEquals

/**
 * Unit testing class that tests the [ListTypes] enum
 */
class ListTypesTest {

    /**
     * Tests the enum's [ListTypes.valueOf] method with all possible file types
     * Also tests the .name property of the enums
     */
    @Test
    fun testValueOf() {
        assertEquals(ListTypes.valueOf("MYANIMELIST"), ListTypes.MYANIMELIST)
        assertEquals(ListTypes.MYANIMELIST.name, "MYANIMELIST")
    }

    /**
     * Tests the enum's [ListTypes.values] method
     */
    @Test
    fun testValues() {
        val values = ListTypes.values()
        assertArrayEquals(values, arrayOf(ListTypes.MYANIMELIST))
    }

    /**
     * Tests the enum's additional parameters
     */
    @Test
    fun testEnumParameters() {
        assertEquals(ListTypes.MYANIMELIST.value, "mal")
    }

}