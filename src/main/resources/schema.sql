drop table if exists authors;
create table authors(
    id bigint auto_increment,
    name varchar(255),
    primary key(id)
);

drop table if exists books;
create table books (
    id bigint auto_increment,
    name varchar(255),
    author_id bigint references authors(id),
    primary key(id)
);

drop table if exists genres;
create table genres(
    id bigint auto_increment,
    name varchar(255),
    primary key(id)
);

drop table if exists book_genre;
create table book_genre(
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id),
    primary key (book_id, genre_id)
);

drop table if exists reviews;
create table reviews(
    id bigint auto_increment,
    review varchar(255),
    book_id bigint references books(id),
    primary key(id)
);
