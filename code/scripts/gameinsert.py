import json
import requests
from requests.auth import HTTPBasicAuth

base_url = "https://api.rawg.io/api/"
grid_url = "http://localhost:8080/grid/"

headers = {'Content-type': 'application/json'}
authentication = HTTPBasicAuth('admin', 'admin')

# login
requests.post(f"{grid_url}login", headers=headers, auth=authentication)


url = base_url + "developers"
count = 0
while count < 100 and url:
	print(url)
	response = requests.get(url).json()
	for dev in response["results"]:
		requests.post(grid_url + "developer",
		              data=json.dumps({"name": dev["name"]}),
		              headers=headers,
		              auth=authentication)
		count += 1
	url = response["next"]

## Insert genres
url = base_url + "genres"
count = 0
while count < 50 and url:
	print(url)
	response = requests.get(url).json()
	for genre in response["results"]:
		genre_info = requests.get(base_url + "genres/" + str(genre["id"])).json()
		requests.post(grid_url + "genre",
		              data=json.dumps({"name": genre_info["name"], "description": genre_info["description"][:100]}),
		              headers=headers,
		              auth=authentication)
		count += 1
	url = response["next"]

## Insert publishers
url = base_url + "publishers"
count = 0
while count < 100 and url:
	print(url)
	response = requests.get(url).json()
	for genre in response["results"]:
		pub_info = requests.get(base_url + "publishers/" + str(genre["id"])).json()
		requests.post(grid_url + "publisher",
		              data=json.dumps({"name": pub_info["name"], "description": pub_info["description"][:100]}),
		              headers=headers,
		              auth=authentication)
		count += 1
	url = response["next"]

# Insert games
url = base_url + "games"
count = 0
while count < 300:
	print(url)
	response = requests.get(url).json()
	for genre in response["results"]:
		game_info = requests.get(base_url + "games/" + str(genre["id"])).json()
		try:
			print(f"{grid_url}add-game")
			response = requests.post(f"{grid_url}add-game", data=json.dumps({
				"name": game_info["name"],
				"description": game_info["description"][:100],
				"releaseDate": game_info["released"],
				"coverUrl": game_info["background_image"],
				"developers": [dev["name"] for dev in game_info["developers"]],
				"gameGenres": [genre["name"] for genre in game_info["genres"]],
				"publisher": game_info["publishers"][0]["name"]
			}), headers=headers, auth=authentication)
			count += 1
			print(response)
		except Exception as error:
			print(f"Error: {error}")
	url = response["next"]
