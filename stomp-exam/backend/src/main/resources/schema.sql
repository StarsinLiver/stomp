create table chat_user (
                           uid int primary key auto_increment,
                           name varchar(4000) ,
                           room_id varchar(2000)
);

create table chat_room (
                           room_id int primary key auto_increment,
                           room_name varchar(400)
);

create table chat_message (
                              cid int primary key auto_increment,
                              sender int,
                              content varchar(4000),
                              type varchar(200),
                              room_id int,
                              uuid varchar(1000),
                              file_root varchar(1000),
                              file_name varchar(1000),
                              file_data longblob,
                              timestamp varchar(1000)
);