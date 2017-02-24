# themes.moe Downloader

The themes.moe Downloader is a program and library for downloading and converting
anime theme songs indexed on [themes.moe](https://themes.moe).

The program is written in Kotlin, but started out as a small python script, then eventually
evolved into a desktop application running on both Windows and Linux.

## Features

**Downloading**

The program features downloading the raw ```.webm``` files which are indexed on 
[themes.moe](https://themes.moe). The files that should be downloaded can be selected using
a variety of options:

* All files that are associated with the titles on a user's
  [myanimelist](https://myanimelist.net) account
* All files in the [*popular*](https://themes.moe/?cl=1) list of
  [themes.moe](https://themes.moe)
* All file that come up when using the *search* feature on [themes.moe](https://themes.moe)
* All files from shows in a given season, as long as that season is indexed
  on [themes.moe](https://themes.moe)
* All files from a user-created [themes.moe](https://themes.moe) playlist

**Converting**

The Program will offer converting to various other formats besides ```.webm```.

The highest priority is ```.mp3```.

**Limitations**

Currently, files from [streamable](https://streamable.com) can not be downloaded.

## Usage

You can download the packaged ```.jar``` archives from the
[Github releases page](https://github.com/namboy94/themes.moe-dl/releases), or
compile the project manually using ```gradle build```

**CLI**

The Command Line application provides a multitude of options. However, not all combinations
of options are valid. If in doubt, call the program using the ```--help``` flag.

The CLI supports all modes of selection as detailed in the *Features* section. The mode is
passed as the sole positional argument. Configuration options are then mode-specific and
best explained by the ```--help``` text. In case those instructions are not clear enough,
the program will tell exactly which arguments are missing for the program to work in the
selected mode.

There are however also some settings that can be applied to all modes:

* ```--no-eds``` This tells the program to disregard any Ending Theme Songs it may find
* ```--no-ops``` This tells the program to disregard any Opening Theme Songs it may find
* ```--verbose``` Prints extra information to the console while the program is running
* ```--debug``` Even more verbose output, used for debugging purposes
* ```--destination``` Specifies a destination directory for the downloaded files. By default
  the program creates a *themes-moe* directory in the current working directory

**Library**

The library can be used by using the ```ThemesMoeParser``` class. The class takes optional
arguments that specify if openings or endings should be included. Besides that, the
class offers methods for downloading using the different modes highlighted in the
*Features* Section. Those methods will return a list of ```Series``` objects, which have a
```download(directory: String)``` method that downloads all files to the specified directory.
Each series will create its own subdirectory while doing so.

For more information have a look at the documentation linked further below.


## Links

* [Changelog](https://gitlab.namibsun.net/namboy94/themes.moe-dl/raw/master/CHANGELOG)
* [Gitlab](https://gitlab.namibsun.net/namboy94/themes.moe-dl)
* [Github](https://github.com/namboy94/themes.moe-dl)
* [Documentation(Library)](https://docs.namibsun.net/html_docs/themes-moe-dl-lib/index.html)
* [Documentation(CLI)](https://docs.namibsun.net/html_docs/themes-moe-dl-cli/index.html)
* [Git Statistics (gitstats)](https://gitstats.namibsun.net/gitstats/themes-moe-dl/index.html)
* [Git Statistics (git_stats)](https://gitstats.namibsun.net/git_stats/themes-moe-dl/index.html)
* [Test Coverage(Library)](https://coverage.namibsun.net/themes-moe-dl-lib/index.html)
