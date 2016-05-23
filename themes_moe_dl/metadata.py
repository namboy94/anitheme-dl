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

"""
The metadata is stored here. It can be used by any other module in this project this way, most
notably by the setup.py file
"""

project_name = "themes_moe_dl"
"""
The name of the project
"""

project_description = "A tool that allows downloading anime openings listed on themes.moe"
"""
A short description of the project
"""

version_number = "0.2"
"""
The current version of the program.
"""

development_status = "Development Status :: 3 - Alpha"
"""
The current development status of the program
"""

audience = "Intended Audience :: End Users/Desktop"
"""
The intended audience of this software
"""

environment = "Environment :: Other Environment"
"""
The intended environment in which the program will be used
"""

programming_language = 'Programming Language :: Python :: 3'
"""
The programming language used in this project
"""

topic = "Topic :: Multimedia :: Video :: Display"
"""
The broad subject/topic of the project
"""

language = "Natural Language :: English"
"""
The (default) language of this project
"""

compatible_os = "Operating System :: OS Independent"
"""
Th eOperating Systems on which the program can run
"""

project_url = "http://gitlab.namibsun.net/namboy94/themes.moe-dl"
"""
A URL linking to the home page of the project, in this case a
self-hosted Gitlab page
"""

download_url = "http://gitlab.namibsun.net/namboy94/themes.moe-dl/repository/archive.zip?ref=master"
"""
A URL linking to the current source zip file.
"""

author_name = "Hermann Krumrey"
"""
The name(s) of the project author(s)
"""

author_email = "hermann@krumreyh.com"
"""
The email address(es) of the project author(s)
"""

license_type = "GNU GPL3"
"""
The project's license type
"""

license_identifier = "License :: OSI Approved :: GNU General Public License v3 (GPLv3)"
"""
The license used for this project
"""

pypi_requirements = ["gfworks"]
"""
List of requirements available on pypi.
"""

other_requirements = ["ffmpeg", 'requests', 'beautifulsoup4']
"""
List of requirements not available on pypi
"""

scripts = ["bin/themes-moe-dl"]
"""
List of script files to be installed during installation
"""
