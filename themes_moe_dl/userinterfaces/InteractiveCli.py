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
from themes_moe_dl.converters.WebmConverter import WebmConverter


class InteractiveCli(object):
    """
    Class that implements an interactive CLI for the program
    """

    @staticmethod
    def run() -> None:
        """
        Runs the CLI

        :return: None
        """
        username = InteractiveCli.prompt("Please enter your username")

        file_format = ""
        while True:
            available_formats = WebmConverter.supported_formats
            file_format = InteractiveCli.prompt("Which format should be downloaded?\n" + str(available_formats))
            if file_format in available_formats:
                break
            else:
                print("This format is not supported")

        destination = InteractiveCli.prompt("Please specify the target location of the downloads", os.getcwd())
        keep_source = InteractiveCli.ask_yes_no("Do you want to keep the downloaded sources?")

        from themes_moe_dl.main import process
        process(username, destination, file_format, keep_source, print)

    @staticmethod
    def prompt(question: str, default_answer: str = "") -> str:
        """
        Prompts the user for an answer

        :param question: the question prompt text
        :param default_answer: a default answer for the prompt
        :return: the user's response
        """

        if default_answer:
            answer = input(question + " (" + default_answer + ")\n")
        else:
            answer = input(question + "\n")
        answer = default_answer if answer == "" and default_answer != "" else answer
        return answer

    @staticmethod
    def ask_yes_no(question: str) -> bool:
        """
        Asks the user a yes/no question

        :param question: The question
        :return: the response of the user, True for y, False for n
        """

        while True:
            answer = input(question + " (y/n)\n")
            if answer == "y":
                return True
            elif answer == "n":
                return False
            else:
                print("Please enter y or n")
