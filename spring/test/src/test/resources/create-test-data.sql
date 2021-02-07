create table if not exists my_entity
(
  id    int not null,
  value varchar(100),
  primary key (id)
);

insert into my_entity(id, value) VALUES (1, 'aaa');
insert into my_entity(id, value) VALUES (2, 'bbc');
insert into my_entity(id, value) VALUES (3, 'cde');