package net.namibsun.themes_moe_dl.lib

import org.jsoup.Jsoup

/**
 * @param args The
 */
fun main(args: Array<String>) {
    println("Hello World")

    val result = Jsoup.connect("https://themes.moe/includes/get_list.php").data("username", "namboy94").data("list", "mal").post()
    print(result)
}
