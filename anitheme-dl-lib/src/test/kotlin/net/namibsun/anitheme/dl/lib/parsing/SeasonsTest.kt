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
 * Unit testing class that tests the [Seasons] enum
 */
class SeasonsTest {

    /**
     * Tests the enum's [Seasons.valueOf] method with all possible file types
     * Also tests the .name property of the enums
     */
    @Test
    fun testValueOf() {
        assertEquals(Seasons.valueOf("SUMMER"), Seasons.SUMMER)
        assertEquals(Seasons.SUMMER.name, "SUMMER")
        assertEquals(Seasons.valueOf("WINTER"), Seasons.WINTER)
        assertEquals(Seasons.WINTER.name, "WINTER")
        assertEquals(Seasons.valueOf("FALL"), Seasons.FALL)
        assertEquals(Seasons.FALL.name, "FALL")
        assertEquals(Seasons.valueOf("SPRING"), Seasons.SPRING)
        assertEquals(Seasons.SPRING.name, "SPRING")
    }

    /**
     * Tests the enum's [Seasons.values] method
     */
    @Test
    fun testValues() {
        val values = Seasons.values()
        assertArrayEquals(values, arrayOf(Seasons.SUMMER, Seasons.WINTER, Seasons.FALL, Seasons.SPRING))
    }

    /**
     * Tests the enum's additional parameters
     */
    @Test
    fun testEnumParameters() {
        assertEquals(Seasons.SUMMER.value, "Summer")
        assertEquals(Seasons.WINTER.value, "Winter")
        assertEquals(Seasons.FALL.value, "Fall")
        assertEquals(Seasons.SPRING.value, "Spring")
    }
}