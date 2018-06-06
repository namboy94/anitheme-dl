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

package net.namibsun.anitheme.dl.lib.utils

import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.nio.file.FileSystemException
import java.nio.file.Paths

/**
 * Class that contains unit tests for the FileOperations module
 */
class FileOperationsTest {

    /**
     * Deletes all generated files and directories
     */
    @After
    fun tearDown() {
        deleteFilesAndDirectories(arrayOf("testfile"))
    }

    /**
     * Tests that a FileSystemException is thrown when trying to create a directory
     * without the necessary access rights
     */
    @Test
    fun testDirectoryCreationPermissionError() {
        try {
            createDirectoryIfNotExists(Paths.get("/", "usr", "bin", "directory_needs_permissions").toString())
            Assert.assertTrue(false)
        } catch (e: FileSystemException) {
            Assert.assertTrue(true)
        }
    }

    /**
     * Tests creating a directory as a child of an existing file
     *
     * This should of course not be possible
     */
    @Test
    fun testDirectoryCreationLogicalError() {
        try {
            File("testfile").createNewFile()
            createDirectoryIfNotExists(Paths.get("testfile", "testdir").toString())
            Assert.assertTrue(false)
        } catch (e: FileSystemException) {
            Assert.assertTrue(true)
        }
    }
}