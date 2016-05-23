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
from subprocess import Popen, PIPE


class WebmConverter(object):
    """
    A class offering various methods to convert a Webm Video to a different file format
    """

    supported_formats = ["mp3", "ogg", "webm"]
    """
    A list of supported formats
    """

    @staticmethod
    def convert(webm_file: str, destination_file: str, conversion_type: str):
        """
        Converts a Webm file to a specified file format

        :param webm_file: the webm file to convert
        :param destination_file: the destination file
        :param conversion_type: the destination file type
        :return: None
        """
        if conversion_type == "mp3":
            WebmConverter.convert_to_mp3(webm_file, destination_file)
        elif conversion_type == "ogg":
            WebmConverter.convert_to_ogg(webm_file, destination_file)

    @staticmethod
    def convert_to_mp3(webm_file: str, mp3_file: str) -> None:
        """
        Converts a Webm file to an mp3 audio file

        :param webm_file: the file to be converted
        :param mp3_file: The destination .mp3 file
        :return: None
        """
        if not mp3_file.endswith(".mp3"):
            mp3_file += ".mp3"
        Popen(["ffmpeg", "-i", webm_file, "-vn", "-acodec", "libmp3lame", "-aq", "4", mp3_file], stderr=PIPE).wait()

    @staticmethod
    def convert_to_ogg(webm_file: str, ogg_file: str) -> None:
        """
        Converts a Webm file to an ogg audio file

        :param webm_file: the file to be converted
        :param ogg_file: The destination .ogg file
        :return: None
        """
        if not ogg_file.endswith(".ogg"):
            ogg_file += ".ogg"
        Popen(["ffmpeg", "-i", webm_file, "-vn", "-acodec", "copy", ogg_file], stderr=PIPE).wait()

    @staticmethod
    def convert_to_webm(webm_file: str, destination_file: str) -> None:
        """
        'Converts' a webm file to a webm file, i.e. does nothing
        :param webm_file: the webm file source
        :param destination_file: the webm file source
        :return: None
        """
        pass
