package net.namibsun.themes_moe_dl.cli
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

/**
 * The Main method that starts the CLI
 *
 * First, the arguments are parsed and validated by the [ArgumentParser] class.
 * Afterwards, a [Downloader] interprets the arguments and downloads
 * theme songs accordingly
 *
 * @param args The Command Line Arguments
 */
fun main(args: Array<String>) {

    val argumentParser = ArgumentParser(args)
    val arguments = argumentParser.parse()
    val downloader = Downloader(arguments)
    downloader.download()

}
