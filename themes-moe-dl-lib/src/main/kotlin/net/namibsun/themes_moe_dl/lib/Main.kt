package net.namibsun.themes_moe_dl.lib

import net.namibsun.themes_moe_dl.lib.parsing.ListTypes
import net.namibsun.themes_moe_dl.lib.parsing.ThemesMoeParser
/**
 * @param args The
 */
fun main(args: Array<String>) {

    val userSeries = ThemesMoeParser().fetchUserList(args[0], ListTypes.MYANIMELIST)
    for (series in userSeries) {
        series.download(args[1])
    }
}
