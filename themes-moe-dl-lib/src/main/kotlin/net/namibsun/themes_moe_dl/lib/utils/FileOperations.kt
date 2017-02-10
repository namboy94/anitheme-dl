package net.namibsun.themes_moe_dl.lib.utils
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

import mu.KotlinLogging
import java.io.File
import java.nio.file.FileSystemException

private val logger = KotlinLogging.logger {}

/**
 * Creates a directory if it does not yet exist
 *
 * First, the method checks if the directory exists. If not, the method will try to create the directory.
 * If the creation of the directory fails, a FileSystemException is thrown
 *
 * @param path The path of the directory to create
 * @throws FileSystemException if the directory creation failed
 * @return The directory's File object
 */
fun createDirectoryIfNotExists(path: String) : File {

    val directory = File(path)

    if (!directory.isDirectory) {

        logger.info { "Creating Directory $path" }

        val result = directory.mkdirs()

        if (result) {
            logger.info { "Directory successfully created" }
        } else {
            logger.error { "Directory could not be created. Throwing FileSystemException" }
            throw FileSystemException("Directory $path could not be created")
        }
    }
    return directory
}