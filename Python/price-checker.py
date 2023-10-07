import requests
from bs4 import BeautifulSoup

url = "https://www.amazon.de/Gateway-Router-Ubiquiti-Machine-UDM-SE/dp/B09ZLBDZTV/"
headers = {
    "User-Agent": 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.55'
}

page = requests.get(url, headers=headers)

htmlcode = BeautifulSoup(page.content, 'html.parser')
price = htmlcode.find("span", {"class": "a-offscreen"}).text
print(price)
