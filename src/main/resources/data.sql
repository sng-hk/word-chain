create database if not exists word_chain;
use word_chain;

create table if not exists users (
    email varchar(255) not null,
    password varchar(255) not null,
    primary key(email)
);

drop table users;