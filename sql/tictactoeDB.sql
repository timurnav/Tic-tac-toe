CREATE DATABASE tictactoe;

use tictactoe;

create table users (
id int auto_increment primary key,
name varchar(25),
wins int(9) DEFAULT 0,
loses int(9) DEFAULT 0,
games int(9) DEFAULT 0,
busy BIT(1) DEFAULT 0
);

create table games(
id int auto_increment primary key,
x int,
y int,
field int(9),
status varchar(10),
current varchar(1),
foreign key (x) references users(id),
foreign key (y) references users(id));