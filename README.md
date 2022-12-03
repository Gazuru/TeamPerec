# TeamPerec
## Setup
sudo apt install mysql-server

sudo systemctl start mysql.service

sudo mysql

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456;

exit

mysql -u root -p

123456

create database testdb;

use testdb;

exit;

//inside db manager eg intellij

roles table add:

id:1 name:ROLE_USER

id:2 name:ROLE_MODERATOR

id:3 name:ROLE_ADMIN

//angular befor ng serve

export NODE_OPTIONS=--openssl-legacy-provider
