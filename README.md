# themes.moe Downloader

A tool that downloads all anime openings listed on themes.moe.

## Installation

First, make sure you have python and pip installed. Then run

    # pip install themes_moe_dl
    
as root (GNU/Linux) or as Administrator (Windows)

Alternatively, Windows users can also use an executable installer: [Download](windist/current/themes-moe-dl.exe)

[Older Versions](windist)

## Usage

To start the program, enter the command themes-moe-dl.py in your terminal or command prompt, followed by the
following options:

    -u / --username  USERNAME           Specifies the username equired by themes.moe. Required when --userinterface
                                        is not specified
    -d / --destination  DESTINATION     The target destination for the downloads. Defaults to the current working
                                        directory
    -f / --format FORMAT                Specifies the targt file format of the downloads.
                                        Currently available are: 'mp3', 'ogg', 'webm'
    -k / --keepsource                   If this flag is set, the source .webm files are not deleted on completion
    									
    -i / --userinterface USERINTERFACE  Can be used to specifiy the user interface. Currently available are:
    									'tk', 'gtk3', 'cli'
    									If this option is selected, all other options are ignored.
    									
## Links

This project is mirrored to:

* [github.com](https://github.com/namboy94/themes.moe-dl)
* [gitlab.com](https://gitlab.com/namboy94/themes.moe-dl)
* [bitbucket.org](https://bitbucket.org/namboy94/themes.moe-dl)

[Git Statistics](http://gitlab.namibsun.net/namboy94/themes.moe-dl/wikis/git_stats/general.html)

[//]: # ([Documentation](http://gitlab.namibsun.net/namboy94/themes.moe-dl/wikis/html/index.html))

[Python package index site](https://pypi.python.org/pypi/themes_moe_dl)!
    
