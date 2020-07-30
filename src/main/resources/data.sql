CREATE DATABASE CONFERENCE;
USE CONFERENCE;

/*==============================================================*/
/* Table: PARTICIPANT                                       */
/*==============================================================*/
create table PARTICIPANT
(
    ID            int(11) unsigned not null auto_increment comment '',
    CONFERENCE_ID int(11) unsigned not null comment '',
    USER_ID       int(11) unsigned not null comment '',
    STATE         int(11)          not null comment '',
    TIME          datetime         not null comment '',
    primary key (ID)
)
    ENGINE = InnoDB
    CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

/*==============================================================*/
/* Table: IMAGE                                                 */
/*==============================================================*/
create table IMAGE
(
    ID            int(11) unsigned not null auto_increment comment '',
    ORIGINAL_NAME varchar(128) comment '',
    HASH_NAME     varchar(50)      not null comment '',
    TIME          datetime         not null comment '',
    primary key (ID)
)
    ENGINE = InnoDB
    CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;


/*==============================================================*/
/* Table: CONFERENCE                                               */
/*==============================================================*/
create table CONFERENCE
(
    ID              int(11) unsigned not null auto_increment comment '',
    MAIN_IMAGE      int(11) unsigned comment '',
    CONFERENCE_NAME varchar(200)     not null comment '',
    START_TIME      datetime         not null comment '',
    END_TIME        datetime         not null comment '',
    DESCRIPTION     text comment '',
    TIME            datetime         not null,
    MEMBER_NUMBER   bigint(20)       not null comment '',
    primary key (ID)
)
    ENGINE = InnoDB
    CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
    ID         int(11) unsigned not null auto_increment comment '',
    AVATAR     int(11) unsigned comment '',
    USERNAME   varchar(50)      not null comment '',
    PASSWORD   varchar(255)     not null comment '',
    EMAIL      varchar(50)      not null comment '',
    FULL_NAME  varchar(50)      not null comment '',
    PERMISSION int(11)          not null comment '',
    TIME       datetime         not null comment '',
    primary key (ID)
)
    ENGINE = InnoDB
    CHARACTER SET = utf8
    COLLATE = utf8_unicode_ci;

alter table PARTICIPANT
    add constraint FK_PARTICIPANT_REFERENCE_CONFERENCE foreign key (CONFERENCE_ID)
        references CONFERENCE (ID) on delete restrict on update restrict;

alter table PARTICIPANT
    add constraint FK_PARTICIPANT_REFERENCE_USER foreign key (USER_ID)
        references USER (ID) on delete restrict on update restrict;

COMMIT;
alter table CONFERENCE
    add fulltext (CONFERENCE_NAME);
COMMIT;

COMMIT;
alter table CONFERENCE
    add ADDRESS varchar(200) not null comment '';
COMMIT;
/*==============================================================*/
/* Insert: DATA                                                 */
/*==============================================================*/

# USER
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'admin', 'ISMvKXpXpadDiUoOSoAfww==', 'admin@gmail.com', 'admin', 1, '2020-07-29 18:40:54');

insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username1', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username1@gmail.com', 'username1', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username2', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username2@gmail.com', 'username2', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username3', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username3@gmail.com', 'username3', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username4', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username4@gmail.com', 'username4', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username5', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username5@gmail.com', 'username5', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username6', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username6@gmail.com', 'username6', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username7', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username7@gmail.com', 'username7', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username8', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username8@gmail.com', 'username8', 0, '2020-07-29 18:40:54');
insert into USER (AVATAR, USERNAME, PASSWORD, EMAIL, FULL_NAME, PERMISSION, TIME)
values (null, 'username9', 'Xa+mzrL8FwzAlx15QuWb+Q==', 'username9@gmail.com', 'username9', 0, '2020-07-29 18:40:54');

# IMAGE
insert into IMAGE (ORIGINAL_NAME, HASH_NAME, TIME)
values ('student.jpeg', 'vl2QhzdhkGFglfDUK7vIjw==', '2020-07-30 08:32:40');

insert into IMAGE (ORIGINAL_NAME, HASH_NAME, TIME)
values ('teacher.jpeg', '2racYzTvla2vvqpWyJEWBg==', '2020-07-30 10:13:19');

insert into IMAGE (ORIGINAL_NAME, HASH_NAME, TIME)
values ('business.jpeg', 'gxD3STu+VpxI7KXcd5PFTw==', '2020-07-30 14:07:43');

# CONFERENCE
insert into CONFERENCE (MAIN_IMAGE, CONFERENCE_NAME, START_TIME, END_TIME, DESCRIPTION, TIME, MEMBER_NUMBER, ADDRESS)
values (1, 'Student Conference', '2020-08-08 08:30:00', '2020-08-08 10:30:00',
        'Hoi nghi danh cho sinh vien, hoc sinh. Co su gop mat cua cac khach moi noi tieng!', '2020-07-30 10:13:19', 5,
        'Ho Chi Minh city');

insert into CONFERENCE (MAIN_IMAGE, CONFERENCE_NAME, START_TIME, END_TIME, DESCRIPTION, TIME, MEMBER_NUMBER, ADDRESS)
values (2, 'Teacher Conference', '2020-08-08 16:30:00', '2020-08-08 18:30:00',
        'Hoi nghi danh cho giao vien. Co su gop mat cua cac khach moi noi tieng!', '2020-07-30 10:13:19', 10,
        'Ho Chi Minh city');

insert into CONFERENCE (MAIN_IMAGE, CONFERENCE_NAME, START_TIME, END_TIME, DESCRIPTION, TIME, MEMBER_NUMBER, ADDRESS)
values (3, 'Business Conference', '2020-08-10 07:30:00', '2020-08-10 11:30:00',
        'Hoi nghi danh cho doanh nghiep. Co su gop mat cua cac khach moi noi tieng!', '2020-07-30 10:13:19', 2,
        'Ho Chi Minh city');

/*==============================================================*/
/* Delete: DATA                                                 */
/*==============================================================*/
# drop database CONFERENCE;

