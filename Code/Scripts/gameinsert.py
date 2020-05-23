import json
import requests

list_of_games = []

url = "https://api.rawg.io/api/games"

for i in range(100):
	print(url)
	response = requests.get(url)
	response = response.json()
	list_of_games += response["results"]
	url = response["next"]

print(list_of_games)