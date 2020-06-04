import json
import requests
from requests.auth import HTTPBasicAuth
import pymysql
import random
import string
from datetime import datetime, timedelta


def random_string(length):
    return ''.join(random.choice(string.ascii_letters) for m in range(length))


db = pymysql.connect("localhost","admin","admin","market" )

cursor = db.cursor()

grid_url = "http://localhost:8080/grid/"

headers = {'Content-type': 'application/json'}
authentication = HTTPBasicAuth('admin', 'admin')


# login
requests.post(f"{grid_url}login", headers=headers, auth=authentication)


game_counter_sql = "select id from Game"
games = []
try:

   cursor.execute(game_counter_sql)
   games = [i[0] for i in cursor.fetchall()]
   
except:
   print ("Error: unable to fetch data")

"""
for game_id in games:
    data = {
        "comment": random_string(50),
        "author": 1,
        "game": game_id,
        "score": random.randint(0,5)
    }

    status = requests.post(grid_url+"add-game-review",data=json.dumps(data),headers=headers,auth=authentication).status_code

"""

# Insert game keys
"""
for _ in range(10):
    data = {
        "key" : random_string(50),
        "gameId" : 2,
        "retailer" : random_string(10),
        "platform" : random_string(10)         
    }

    status = requests.post(grid_url + "gamekey", data = json.dumps(data), headers=headers, auth = authentication).status_code
    print(status)
"""
keys_sql = "select realKey from GameKey where game_id=2;"
keys = []
try:

   cursor.execute(keys_sql)
   keys = [i[0] for i in cursor.fetchall()]
   
except:
   print ("Error: unable to fetch data")

for key in keys:
    data = {
        "auctioneer" : "admin2",
        "gameKey": key,
        "endDate" : "4/6/2020",
        "price": random.randint(1,20)
    }

    status = requests.post(grid_url + "create-auction", data = json.dumps(data), headers=headers, auth = authentication).status_code
    print(status)
