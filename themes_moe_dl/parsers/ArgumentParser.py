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
import os
import argparse


class ArgumentParser(object):
    """
    Class that parses command line arguments
    """

    @staticmethod
    def parse() -> argparse.Namespace:

        parser = argparse.ArgumentParser(description="Download Anime Openings and Endings")

        parser.add_argument("-u", "--username", type=str, default="", help="The username to be used")
        parser.add_argument("-f", "--format", type=str, default="webm", help="Specifies a destination"
                                                                             "format for the downloaded files")
        parser.add_argument("-d", "--destination", type=str, default=os.getcwd(), help="Specifies the target download"
                                                                                       "directory for the downloaded"
                                                                                       "files")
        parser.add_argument("-k", "--keepsource", action="store_true", help="Keeps the downloaded video source"
                                                                            "if requested")
        parser.add_argument("-i", "--userinterface", type=str, default="", help="Can be used to specify the user"
                                                                                "interface.")

        return parser.parse_args()
