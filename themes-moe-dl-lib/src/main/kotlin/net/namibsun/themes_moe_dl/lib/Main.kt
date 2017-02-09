package net.namibsun.themes_moe_dl.lib

import net.namibsun.themes_moe_dl.lib.parsing.ListTypes
import net.namibsun.themes_moe_dl.lib.parsing.ThemesMoeParser

/**
 * @param args The
 */
fun main(args: Array<String>) {
    println(ThemesMoeParser().fetchUserList("namboy94", ListTypes.MYANIMELIST))

}
