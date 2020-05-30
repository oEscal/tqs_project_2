import json
import requests
from requests.auth import HTTPBasicAuth
import pymysql
import random
import string


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


for game_id in games:
    data = {
        "comment": random_string(50),
        "author": 1,
        "game": game_id,
        "score": random.randint(0,5)
    }

    status = requests.post(grid_url+"add-game-review",data=json.dumps(data),headers=headers,auth=authentication).status_code
    
