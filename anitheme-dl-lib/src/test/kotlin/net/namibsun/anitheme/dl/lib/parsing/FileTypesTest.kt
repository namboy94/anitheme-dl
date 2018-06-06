/*
Copyright 2016 Hermann Krumrey <hermann@krumreyh.com>

This file is part of anitheme-dl.

anitheme-dl is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

anitheme-dl is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with anitheme-dl.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.anitheme.dl.lib.parsing

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