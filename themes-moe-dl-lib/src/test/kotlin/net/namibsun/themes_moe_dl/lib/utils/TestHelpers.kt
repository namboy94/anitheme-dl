package net.namibsun.themes_moe_dl.lib.utils

import java.io.File

/**
 * Deletes all files passed via the targets parameter
 */
fun deleteFilesAndDirectories(targets: Array<String>) {

    targets
            .map(::File)
            .filter(File::exists)
            .forEach { it.deleteRecursively() }

}