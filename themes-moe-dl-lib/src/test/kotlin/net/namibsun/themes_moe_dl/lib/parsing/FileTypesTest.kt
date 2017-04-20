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
 * Unit testing class that tests the [FileTypes] enum
 */
class FileTypesTest {

    /**
     * Tests the enum's [FileTypes.valueOf] method with all possible file types
     * Also tests the .name property of the enums
     */
    @Test
    fun testValueOf() {
        assertEquals(FileTypes.valueOf("WEBM"), FileTypes.WEBM)
        assertEquals(FileTypes.WEBM.name, "WEBM")
        assertEquals(FileTypes.valueOf("MP3"), FileTypes.MP3)
        assertEquals(FileTypes.MP3.name, "MP3")
    }

    /**
     * Tests the enum's [FileTypes.values] method
     */
    @Test
    fun testValues() {
        val values = FileTypes.values()
        assertArrayEquals(values, arrayOf(FileTypes.WEBM, FileTypes.MP3))
    }

}