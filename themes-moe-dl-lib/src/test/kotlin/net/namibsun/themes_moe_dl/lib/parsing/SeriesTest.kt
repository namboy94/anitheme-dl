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
import org.junit.After
import java.io.File
import java.nio.file.Paths

/**
 * Unit testing class that tests the [Series] class
 */
class SeriesTest {

    val emptyTestSeries = Series("TestSeries", listOf())
    val testSeries = Series("TestSeries", listOf(
            Theme("TestThemeOne", "https://namibsun.net/resources/images/small.webm"),
            Theme("TestThemeTwo", "https://namibsun.net/resources/images/small2.webm")))

    /**
     * Deletes all files and directories generated during the Unit tests
     */
    @After
    fun tearDown() {
        val cleanupTargets = arrayOf("test")

        cleanupTargets
                .map(::File)
                .filter(File::exists)
                .forEach { it.deleteRecursively() }
    }

    /**
     * Tests downloading the same series twice.
     *
     * This is to test the behaviour of the directory creation on existing directories, as well as
     * dealing with duplicates
     */
    @Test
    fun testDownloadingRepeatedly() {
        testDownloading()
        testDownloading()
    }

    /**
     * Tests the downloading of a series
     */
    fun testDownloading() {
        testSeries.download("test")
        assertTrue(File(Paths.get("test", "TestSeries", "TestThemeOne.webm").toString()).isFile)
        assertTrue(File(Paths.get("test", "TestSeries", "TestThemeTwo.webm").toString()).isFile)
    }


    /**
     * Tests the Series String generating method
     */
    @Test
    fun testToString() {
        assertEquals(this.emptyTestSeries.toString(), "TestSeries: []")
    }

}