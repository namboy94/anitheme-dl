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
from themes_moe_dl.objects.AnimeSong import AnimeSong


class AnimeShow(object):
    """
    Class that handles the information of a show from themes.moe-dl, including the myanimelist link
    as well as the links to the openings and endings
    """

    show_name = ""
    """
    The name of this show
    """

    mal_link = ""
    """
    The myanimelist.net link of the show
    """

    songs = []
    """
    The openings and endings of this show
    """

    def __init__(self, show_name: str, mal_link: str) -> None:
        """
        Initializes the class while defining the show name and myanimelist.net link

        :param show_name: The show name
        :param mal_link: the myanimelist.net link
        :return: None
        """
        self.show_name = show_name
        self.mal_link = mal_link

    def add_song(self, song: AnimeSong) -> None:
        """
        Adds an opening or ending to the show

        :param song: the song to add
        :return: None
        """
        self.songs.append(song)