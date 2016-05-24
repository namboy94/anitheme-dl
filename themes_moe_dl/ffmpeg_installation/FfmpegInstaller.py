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


class FfmpegInstaller(object):
    """
    Class that handles the installation of ffmpeg
    """

    @staticmethod
    def is_installed() -> bool:
        """
        """
        paths = os.getenv("PATH")
        is_installed = False
        for path in paths.split(";"):
            if os.path.isfile(os.path.join(path, "ffmpeg")):
                is_installed = True
                break
        return is_installed

    @staticmethod
    def install_on_windows():
        """
        Installs ffmpeg on Windows

        :return: None
        """
        installed_directory = "C:\\Program Files (x86)\\themes.moe-dl\\"
        frozen_ffmpeg_directory = "C:\\Program Files (x86)\\themes.moe-dl\\ffmpeg\\bin"
        if os.path.isdir(installed_directory):
            sys.path.append(frozen_ffmpeg_directory)
            os.system("setx path \"%path%;" + frozen_ffmpeg_directory + "\"")
        else:
            print("Please install ffmpeg")
            sys.exit(1)

    @staticmethod
    def install_on_linux() -> None:
        """
        Tells the user to install ffmpeg using the package manager

        :return: None
        """
        print("Please install ffmpeg using your package manager")
        print("Example:\n\n sudo pacman -S ffmpeg\nor\nsudo apt-get install ffmpeg")
        sys.exit(1)

    @staticmethod
    def install() -> None:
        """
        Install ffmpeg on the user's system

        :return: None
        """
        if os.name == "nt":
            FfmpegInstaller.install_on_windows()
        else:
            FfmpegInstaller.install_on_linux()
