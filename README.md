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
