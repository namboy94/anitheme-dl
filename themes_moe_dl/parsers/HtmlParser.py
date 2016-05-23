"""
Copyright 2016 Hermann Krumrey

This file is part of themes.moe-dl.

    themes.moe-dl offers functionality to download anime openings
    listed on themes.moe and converting them into audio files

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
"""

# imports
import requests
from bs4 import BeautifulSoup
from typing import List
from themes_moe_dl.objects.AnimeShow import AnimeShow
from themes_moe_dl.objects.AnimeSong import AnimeSong


class HtmlParser(object):
    """
    Parses the themes.moe website's html to get all anime openings/endings listed for a specified
    myanimelist.net account
    """

    user_name = ""
    """
    The myanimelist.net username for which the links should be parsed
    """

    def __init__(self, user_name) -> None:
        """
        Constructor of the HtmlParser class that stores the user name as a class variable

        :param user_name: the user name to be used while parsing
        :return: None
        """
        self.user_name = user_name

    # noinspection PyTypeChecker
    def parse(self) -> List[AnimeShow]:
        """
        Parses the website and returns the result as a list of AnimeShow objects

        :return: the list of anime shows
        """
        post_request = requests.post("http://themes.moe/includes/get_list.php", {'username': self.user_name})
        request_result = BeautifulSoup(post_request.text, "html.parser")
        show_information = request_result.select('.mal-url')
        song_links = request_result.select('.col-md-9')

        show_objects = []

        for show in range(0, len(show_information)):

            info = str(show_information[show])

            mal_link = info.split('<a class=\"mal-url\" href=\"')[1].split("\" target=\"_blank\">")[0]
            show_name = info.split("\" target=\"_blank\">")[1].split("</a>")[0]
            show_object = AnimeShow(show_name, mal_link)

            links = str(song_links[show])
            links = BeautifulSoup(links, "html.parser").select(".vid-popup")

            for link in links:

                file_name = link.text
                file_link = str(link).split("href=\"")[1].split("\"")[0]
                show_object.add_song(AnimeSong(file_name, file_link))

            show_objects.append(show_object)

        return show_objects
