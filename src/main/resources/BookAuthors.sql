#-----------start of the script-------------------------------------------
create database if not exists bookstore;
use bookstore;
set global time_zone = '+3:00';

drop table if exists book;

drop table if exists author;

drop table if exists bookauthor;

create table if not exists book(id int not null auto_increment primary key, title varchar(50));

create table if not exists author(id int not null auto_increment primary key, name varchar(50));

create table if not exists bookauthor(book_id int not null, author_id int not null);

alter table bookauthor add constraint UK_bookauthor unique (book_id, author_id);

alter table bookauthor add constraint FK_book foreign key (book_id) references book (id);

alter table bookauthor add constraint FK_author foreign key (author_id) references author (id);

insert into book values(1,"Book 1");
insert into book values(2,"Book 2");
insert into book values(3,"Book 3");
insert into book values(4,"Book 4");

insert into author values(1,"Author 1");
insert into author values(2,"Author 2");
insert into author values(3,"Author 3");
insert into author values(4,"Author 4");

insert into bookauthor values(1,1);
insert into bookauthor values(1,2);
insert into bookauthor values(1,3);

insert into bookauthor values(2,1);
insert into bookauthor values(2,2);

insert into bookauthor values(3,2);
insert into bookauthor values(3,3);
insert into bookauthor values(3,4);

insert into bookauthor values(4,4);

commit;

select ba.book_id, b.title
  from bookauthor ba left outer join book b on ba.book_id = b.id
group by book_id
having count(*) >= 3; 
#-----------end of the script-------------------------------------------