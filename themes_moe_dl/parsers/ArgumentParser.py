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
import sys
import argparse
from gfworks.templates.generators.GridTemplateGenerator import GridTemplateGenerator


class ArgumentParser(object):
    """
    Class that parses command line arguments
    """

    @staticmethod
    def parse() -> argparse.Namespace:

        parser = argparse.ArgumentParser(description="Download Anime Openings and Endings")

        parser.add_argument("-u", "--username", type=str, help="The username to be used")
        parser.add_argument("-f", "--format", type=str, default="webm",
                            help="Specifies a destination format for the downloaded files")
        parser.add_argument("-d", "--destination", type=str, default=os.path.expanduser("~"),
                            help="Specifies the target download directory for the downloaded files")
        parser.add_argument("-k", "--keepsource", action="store_true",
                            help="Keeps the downloaded video source if requested")
        parser.add_argument("-i", "--userinterface", type=str, default="",
                            help="Can be used to specify the user interface.")

        args = parser.parse_args()

        if args.userinterface:
            try:
                GridTemplateGenerator.get_grid_templates()[args.userinterface]
            except KeyError:
                if args.userinterface != "cli":
                    print("This user interface does not exist or is not available on your platform")
                    sys.exit(1)
            sys.argv[1] = args.userinterface
        else:
            if args.username is None:
                print("A username is required. Use the -u parameter to specify your myanimelist.net username")
                sys.exit(1)

        return args
