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
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.io.File
import java.nio.file.Paths

/**
 * Unit testing class that tests the Theme class
 */
class ThemeTest {

    val testTheme = Theme("Testtheme", "Url")
    val validTarget = Theme("Namibia", "https://namibsun.net/resources/images/namibia.png")

    /**
     * Cleans up any files created by the unit tests
     */
    @After
    fun tearDown() {

        val cleanupTargets = arrayOf("test", "testimage.png")

        cleanupTargets
                .map(::File)
                .filter(File::exists)
                .forEach { it.deleteRecursively() }
    }

    /**
     * Tests the Theme String generating method
     */
    @Test
    fun testToString() {
        assertEquals(this.testTheme.toString(), "Testtheme: Url")
    }

    /**
     * Tests the download method that takes a directory as parameter
     * Suffix and prefix addition is also tested
     */
    @Test
    fun testDownloadingToDirectory() {
        this.validTarget.download("test", prefix = "pre", suffix = "suf")
        assertTrue(File(Paths.get("test", "preNamibiasuf.png").toString()).isFile)
    }

    /**
     * Tests downloading a file using the direct file downloading methos
     */
    @Test
    fun testDownloadingToFile() {
        this.validTarget.downloadFile("testimage")
        assertTrue(File("testimage.png").isFile)
    }

}