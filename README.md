# Grid Marketplace - TQS Project 2

# Frontend - Web App
## Install
```
$ npm install
$ npm i serialize-javascript
$ npm i react-select
$ npm i @material-ui/util
$ npm i @material-ui/lab
$ npm i react-lottie
$ npm i react-fade-in
$ npm i react-toastify
```

## Start
```
$ npm start
```

# Backend
## Install MySQL
[Install MySQL in Ubuntu 18.04](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-18-04)
## Setup MySQL database in development

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

## Server Deploy
 - First, it's necessary to make a pull request to github with the tag `deploy` with the code we want to deploy next to the server. This will trigger the deploy workflow, that will create new images of the code to be deployed.
 - The first time, it's necessary to have all containers pre-created on the server. So, on the server terminal, run:
 ```bash
 $ docker container run --env-file ~/env_vars/rest_api.env --publish 8080:8080 --detach --name rest docker.pkg.github.com/oescal/tqs_project_2/api                # run the rest container
 $ docker container run --env-file ~/env_vars/frontend.env --publish 80:80 --detach --name web-app docker.pkg.github.com/oescal/tqs_project_2/web-app              # run the web-app container
