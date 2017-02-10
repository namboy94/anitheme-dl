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

import net.namibsun.themes_moe_dl.lib.utils.deleteFilesAndDirectories
import org.junit.Test
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import java.io.File
import java.io.IOException
import java.nio.file.Paths

/**
 * Unit testing class that tests the [Theme] class
 */
class ThemeTest {

    val testTheme = Theme("Testtheme", "Url")
    val validTarget = Theme("Test", "https://namibsun.net/resources/images/small.webm")
    val invalidTarget = Theme("Invalid", "https://namibsun.net/resources/images/__null.png__")
    val streamableTarget = Theme("Streamable", "https://streamable.com/something.webm")

    /**
     * Cleans up any files created by the unit tests
     */
    @After
    fun tearDown() {
        deleteFilesAndDirectories(arrayOf("test", "testfile.webm"))
    }

    /**
     * Tests the Theme String generating method
     */
    @Test
    fun testToString() {
        assertEquals(this.testTheme.toString(), "Testtheme: Url")
    }

    /**
     * Tests if downloading from an invalid target throws an IOException
     */
    @Test
    fun testInvalidDownloadTarget() {
        try {
            this.invalidTarget.download("test")
            assertTrue(false)
        } catch (e: IOException) {
            assertTrue(true)
        }
    }

    /**
     * Makes sure that files from streamable.com are ignored
     */
    @Test
    fun testSkippingStreamable() {
        this.streamableTarget.download("test")
        assertFalse(File(Paths.get("test", "Streamable.webm").toString()).isFile)
    }

    /**
     * Tests downloading a file to a directory that already exists
     *
     * Makes use of [testDownloadingToDirectory] to avoid testing duplicates
     */
    @Test
    fun testDownloadOnExistingDirectory() {
        this.testDownloadingToDirectory()
        this.validTarget.download("test")
        assertTrue(File(Paths.get("test", "Test.webm").toString()).isFile)
    }

    /**
     * Tests the download method that takes a directory as parameter
     * Suffix and prefix addition is also tested
     */
    fun testDownloadingToDirectory() {
        this.validTarget.download("test", prefix = "pre-", suffix = "-suf")
        assertTrue(File(Paths.get("test", "pre-Test-suf.webm").toString()).isFile)
    }

    /**
     * Executes a series of downloading tests.
     *
     * First, [testDownloadingToFile] is called by [testDownloadExisitingFile],
     * which subsequently gets called by this method. This is done to reduce redundancy
     * and increase test runtimes
     *
     * This test checks if the original webm file is deleted if no filetype is specified
     */
    @Test
    fun testDeleteWebmAfterwards() {
        this.testDownloadExisitingFile()
        this.validTarget.downloadFile("testfile", arrayOf())
        assertFalse(File("testfile.webm").isFile)
    }

    /**
     * Tests downloading a file again even when it has already been downloaded
     *
     * The original file should be kept, no new file should be created
     */
    fun testDownloadExisitingFile() {
        this.testDownloadingToFile()
        this.validTarget.downloadFile("testfile")
        assertTrue(File("testfile.webm").isFile)
    }

    /**
     * Tests downloading a file using the direct file downloading methods
     */
    fun testDownloadingToFile() {
        this.validTarget.downloadFile("testfile")
        assertTrue(File("testfile.webm").isFile)
    }

}