# Grid Marketplace - TQS Project 2

## Idea

The Grid Marketplace serves as a storefront for the quick selling and purchasing of digital video game keys. It offers features such as the ability to create auctions, alongside normal sell listings, review games, review users, purchase multiple games utilizing a shopping cart, amongst others. It also contains real informations on hundereds of games from the RAWG API.
  
## Main roles (Contributors)

* **Team Leader:** [Diogo Silva](https://github.com/HerouFenix)
* **Developer:** [Pedro Oliveira](https://github.com/DrPunpun)
* **DevOps Master and QA Engineer:** [Pedro Escaleira](https://github.com/oEscal)
* **Product Owner:** [Rafael Simões](https://github.com/Rafaelyot)

## Access Links
* **Server IP:** 192.168.160.56
* **Web App:** 192.168.160.56:80
* **Backend:** 192.168.160.56:8080
* **Backend Swagger:** 192.168.160.56:8080/swagger-ui.html
* **Mobile App:** Check the APK on the MobileGrid project

* **Project Drive:** https://drive.google.com/drive/folders/1L-cahcNo5ZVX6p64ijbs-W31lpjKoJRE?usp=sharing (Contains other assets such as meetings, showcase videos and image resources)



## Account Credentials
The following Grid user can be utilized to mess around with and test out our service
* **Username:** admin
* **Password:** admin


## Frontend - Web App
### Install
```
$ npm install
```

### Start
```
$ npm start
```

## Frontend - Mobile App
### Install
```
$ npm install
```

### Start
```
$ expo start
```

### Run Tests
```
$ npm test
```

## Backend
### Install MySQL
[Install MySQL in Ubuntu 18.04](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-18-04)
### Setup MySQL database in development

```sql
 # sudo mysql -u root
 > create database market;
 > create user 'admin'@'localhost' identified by 'admin';
 > grant all on * . * to 'admin'@'localhost';
```

If you're having problems with the password strength, you may use this
```sql
uninstall plugin validate_password;
```
Or reduce the password validation strength with:
```sql
> set global validate_password_policy=0
```

### Server Deploy
 - First, it's necessary to make a pull request to github with the tag `deploy` with the code we want to deploy next to the server. This will trigger the deploy workflow, that will create new images of the code to be deployed.
 - The first time, it's necessary to have all containers pre-created on the server. The files necessary to deploy the database are on the [drive folder](https://drive.google.com/drive/folders/1vFA4bkDRcnIPeB7-68J1umuGZ8dowvKG?usp=sharing). So, on the server terminal, run:
 ```bash
 $ docker-compose up -d                # run the rest container
 $ docker container run --env-file ~/env_vars/frontend.env --publish 80:80 --detach --name web-app docker.pkg.github.com/oescal/tqs_project_2/web-app              # run the web-app container

 $ docker run --env-file ~/env_vars/watchtower.env -d --name watchtower -v /var/run/docker.sock:/var/run/docker.sock -v ~/.docker/config.json:/config.json containrrr/watchtower              # run the watchtower container for continuous deployment
```

## Raspberry Pipeline Monitoring
### Python script for pipeline monitoring
 - For this application to run it is necessary to have a GitHub token, since with that is possible to make much more requests to the GitHub API. This token is necessary to be exported as an environment variable on the execution environment:
 ```bash
 $ export TOKEN=<github token>
 ```

 - Then, to run the application, its necessary to setup a virtual environment and to install all the dependencies:
 ```bash
 $ cd RaspberryPipelineMonitor
 $ virtualenv venv
 $ source venv/bin/activate
 $ pip install -r requirements.txt
 ```

 - To run, the default behavior is to monitor this repository, on the branch master and to check for branch updates every 5 seconds:
 ```
 $ python main.py
 ```

 - To change the current behavior, we can run the script with the following arguments:
 ```bash
 $ python main.py --branch <branch name> --update_seconds <time interval to check for new updates> --full_name_or_id <repository name or id>
 ```

 - You can see the script behavior on the [video](https://drive.google.com/file/d/16Excjh7k0iB3FzppBCLL4JAgOr91Dz42/view?usp=sharing).
