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
from themes_moe_dl.converters.WebmConverter import WebmConverter
from gfworks.templates.generators.GridTemplateGenerator import GridTemplateGenerator


gui_framework = GridTemplateGenerator.get_grid_templates()[sys.argv[1]]


# noinspection PyAbstractClass
class Gui(gui_framework):
    """
    Class that offers a graphical interface for use with themes.moe-dl
    """

    username_entry = None
    """
    A text entry field for the username
    """

    destination_entry = None
    """
    A text entry field for the destination directory
    """

    destination_browser = None
    """
    A button that allows the user to browse for a directory that gets entered into the destination
    entry widget
    """

    format_selector = None
    """
    a combobox from which the user can select the end format of the files
    """

    format_selector_label = None
    """
    A label next to the format selector combo box that indicates that it is the format selector
    """

    status_label = None
    """
    a label showing the current status of the program
    """

    secondary_status_label = None
    """
    A secondary status label for download percentages
    """

    keep_source_checkbox = None
    """
    a checkbox that allows the user to decide if the source should be deleted after the program completes or not.
    """

    start_button = None
    """
    Button that starts the downloader
    """

    download_thread = None
    """
    The download thread that downloads the openings and endings
    """

    def __init__(self):
        """
        Constructor that calls the super class' constructor
        """
        super().__init__("themes.moe")

    def lay_out(self) -> None:
        """
        Lays out the Gui

        :return: None
        """
        self.username_entry = self.generate_text_entry("")
        self.destination_entry = self.generate_text_entry(os.getcwd())
        self.destination_browser = self.generate_button("browse", self.browse_location)
        self.format_selector = self.generate_string_combo_box(WebmConverter.supported_formats + ["all"])
        self.format_selector_label = self.generate_label("Format")
        self.status_label = self.generate_label("")
        self.secondary_status_label = self.generate_label("")
        self.keep_source_checkbox = self.generate_check_box("Keep Source", True)
        self.start_button = self.generate_button("Start", self.start_download)

        self.position_absolute(self.username_entry, 0, 0, 3, 1)
        self.position_absolute(self.destination_entry, 0, 1, 2, 1)
        self.position_absolute(self.destination_browser, 2, 1, 1, 1)
        self.position_absolute(self.format_selector_label, 0, 2, 1, 1)
        self.position_absolute(self.format_selector, 1, 2, 2, 1)
        self.position_absolute(self.status_label, 0, 3, 3, 1)
        self.position_absolute(self.secondary_status_label, 0, 4, 3, 1)
        self.position_absolute(self.start_button, 1, 5, 2, 1)
        self.position_absolute(self.keep_source_checkbox, 0, 5, 1, 1)

    def start_download(self, widget) -> None:
        """
        Starts the download process

        :param widget: the button that called this method
        :return: None
        """
        str(widget)

        if self.download_thread is not None and self.download_thread.is_alive():
            self.show_message_dialog("Currently Downloading", "Please wait until the current download has finished")

        username = self.get_string_from_text_entry(self.username_entry)
        destination = self.get_string_from_text_entry(self.destination_entry)
        file_format = self.get_string_from_current_selected_combo_box_option(self.format_selector)
        keep_source = self.get_boolean_from_check_box(self.keep_source_checkbox)

        if not username:
            self.show_message_dialog("No Username Specified", "Please specify a username")

        from themes_moe_dl.main import process

        def download() -> None:
            """
            Starts the download process in another thread

            :return: None
            """
            process(username, destination, file_format, keep_source, self.progress_callback)

        self.download_thread = self.run_thread_in_parallel(download, daemon=True)

    def stop(self) -> None:
        super().stop()
        import sys
        sys.exit(0)

    def browse_location(self, widget: object):
        """
        Lets the user browse for a directory

        :param widget: the button that called this method
        :return: None
        """
        str(widget)
        location = self.show_directory_chooser_dialog()
        self.set_text_entry_string(self.destination_entry, location)

    # noinspection PyTypeChecker
    def progress_callback(self, progress_text: str, end: str = "\n") -> None:
        """
        Callback method that updates the progress label

        :param progress_text: The new progress label text
        :param end: dummy
        :return: None
        """
        if progress_text:
            if len(progress_text) > 43:
                progress_text = progress_text[0:40] + "..."
            if end == "" and "\r" in progress_text:
                self.run_thread_safe(self.set_label_string, args=(self.secondary_status_label, progress_text))
            else:
                self.run_thread_safe(self.set_label_string, args=(self.status_label, progress_text))
