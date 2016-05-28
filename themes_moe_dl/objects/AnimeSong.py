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


class AnimeSong(object):
    """
    A class that stores the information of an anime opening or ending
    """

    song_name = ""
    """
    The name of the opening or ending
    """

    song_link = ""
    """
    The URL to the song's file
    """

    def __init__(self, song_name: str, song_link: str) -> None:
        """
        Initializes the class by storing the song name and song link into the class variables

        :param song_name: The song name
        :param song_link: The link to the song's file
        :return: None
        """
        self.song_link = song_link

        for illegal_character in ":\\/*?\"<>|":
            song_name = song_name.replace(illegal_character, "")

        self.song_name = song_name
