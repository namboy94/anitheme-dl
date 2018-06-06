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

/**
 * An enum that defines the four seasons in which anime series typically air.
 *
 * This is used when parsing for the seasonal theme lists
 *
 * @param value The value used by the parser
 */
enum class Seasons (val value: String) {
    SUMMER("Summer"), WINTER("Winter"), FALL("Fall"), SPRING("Spring")
}