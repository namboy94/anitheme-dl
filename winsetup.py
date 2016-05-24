# coding=utf-8
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
import themes_moe_dl.metadata as metadata
from setuptools import setup, find_packages
# noinspection PyPackageRequirements
import py2exe
str(py2exe)


def readme() -> str:
    """
    Reads the readme file and converts it from markdown to restructured text

    :return: the readme file as a string
    """
    try:
        # noinspection PyPackageRequirements
        import pypandoc
        with open('README.md') as f:
            # Convert markdown file to rst
            markdown = f.read()
            rst = pypandoc.convert(markdown, 'rst', format='md')
            return rst
    except (OSError, ImportError):
        # If pandoc is not installed, just return the raw markdown text
        with open('README.md') as f:
            return f.read()


setup(console=[],
      windows=[],
      zipfile=None,
      name=metadata.project_name,
      version=metadata.version_number,
      description=metadata.project_description,
      long_description=readme(),
      classifiers=[metadata.development_status,
                   metadata.license_identifier,
                   metadata.audience,
                   metadata.programming_language,
                   metadata.topic,
                   metadata.language,
                   metadata.compatible_os,
                   metadata.environment],
      url=metadata.project_url,
      author=metadata.author_name,
      author_email=metadata.author_email,
      license=metadata.license_type,
      packages=find_packages(),
      install_requires=metadata.pypi_requirements,
      requires=metadata.other_requirements,
      scripts=metadata.scripts,
      test_suite='nose.collector',
      tests_require=['nose'],
      zip_safe=False)

# How to upload to pypi:
# python setup.py register sdist upload
