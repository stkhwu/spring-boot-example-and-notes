drop table students;

CREATE TABLE students (
  id integer auto_increment,
  name varchar(30) not null,
  height numeric(5,2) not null, 
  constraint pk_students primary key(id)
);