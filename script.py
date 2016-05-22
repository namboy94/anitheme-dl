import os
import requests
import urllib.request
from bs4 import BeautifulSoup

x = requests.post("http://themes.moe/includes/get_list.php", {'username': 'namboy94'})
y = BeautifulSoup(x.text, "html.parser")
t = y.select('.mal-url')
r = y.select('.col-md-9')

all_info = []

for x in range(0, len(t)):

    info = str(t[x])
    links = r[x]

    mal_link = info.split('<a class=\"mal-url\" href=\"')[1].split("\" target=\"_blank\">")[0]
    name = info.split("\" target=\"_blank\">")[1].split("</a>")[0]

    soup2 = BeautifulSoup(str(links))
    a = soup2.select(".vid-popup")
    
    
    videos = []
    
    for b in range(0, len(a)):
    
        filename = a[b].text
    
        link = str(a[b]).split("href=\"")[1].split("\"")[0]

        videos.append({"filename": filename + ".webm", "link": link})
        
    
    all_info.append({"name": name, "mal": mal_link, "files": videos})
    
if not os.path.isdir("anime-dl"):
    os.makedirs("anime-dl")
    
for show in all_info:

    if not os.path.isdir("anime-dl/" + show["name"]):
        os.makedirs("anime-dl/" + show["name"])
    
    mal = open("anime-dl/" + show["name"] + "/mal.link", "w")
    mal.write(show["mal"])
    mal.close()
    
    for download in show["files"]:
    
        link = download["link"]
        local_file = "anime-dl/" + show["name"] + "/" + download["filename"]
        
        if not os.path.isfile(local_file):
        
            print("Downloading " + link)
        
            urllib.request.urlretrieve(link, local_file)

            from subprocess import Popen

            Popen(["ffmpeg", "-i", local_file, "-vn", "-acodec", "copy", local_file + ".ogg"]).wait()
            Popen(["ffmpeg", "-i", local_file, "-vn", "-acodec", "libmp3lame", "-aq", "4", local_file + ".mp3"]).wait()
