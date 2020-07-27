CREATE DATABASE CONFERENCE;
USE CONFERENCE;

/*==============================================================*/
/* Table: PARTICIPANT                                       */
/*==============================================================*/
create table PARTICIPANT
(
   ID                   int(11) unsigned not null auto_increment  comment '',
   CONFERENCE_ID        int(11) unsigned not null  comment '',
   USER_ID              int(11) unsigned not null  comment '',
   TIME                 datetime not null  comment '',
   primary key (ID)
)
ENGINE = InnoDB
CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

/*==============================================================*/
/* Table: REQUEST                                              */
/*==============================================================*/
create table REQUEST
(
   ID                   int(11) unsigned not null auto_increment  comment '',
   USER_ID              int(11) unsigned  comment '',
   TIME                 datetime not null  comment '',
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
   ID                   int(11) unsigned not null auto_increment comment '',
   ORIGINAL_NAME        varchar(128)  comment '',
   HASH_NAME            varchar(50) not null  comment '',
   CONFERENCE_ID        int(11) unsigned  comment '',
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
   ID                   int(11) unsigned not null auto_increment comment '',
   MAIN_IMAGE           int(11) unsigned  comment '',
   CONFERENCE_NAME      varchar(200) not null  comment '',
   START_TIME           datetime not null  comment '',
   END_TIME             datetime not null  comment '',
   DESCRIPTION          text  comment '',
   TIME  				datetime not null,
   MEMBER_NUMBER        bigint(20) not null  comment '',
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
   ID                   int(11) unsigned not null auto_increment  comment '',
   USERNAME             varchar(50) not null comment '',
   PASSWORD             varchar(255) not null  comment '',
   FULL_NAME            varchar(50) not null comment '',
   PERMISSION           int(11) not null comment '',
   TIME                 datetime not null comment '',
   primary key (ID)
)
ENGINE = InnoDB
CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


alter table REQUEST add constraint FK_REQUEST_REFERENCE_USER foreign key (USER_ID)
      references USER (ID) on delete restrict on update restrict;

alter table IMAGE add constraint FK_IMAGE_REFERENCE_CONFERENCE foreign key (CONFERENCE_ID)
      references CONFERENCE (ID) on delete restrict on update restrict;

alter table PARTICIPANT add constraint FK_PARTICIPANT_REFERENCE_CONFERENCE foreign key (CONFERENCE_ID)
      references CONFERENCE (ID) on delete restrict on update restrict;

alter table PARTICIPANT add constraint FK_PARTICIPANT_REFERENCE_USER foreign key (USER_ID)
      references USER (ID) on delete restrict on update restrict;

COMMIT;
alter table CONFERENCE add fulltext (CONFERENCE_NAME);
COMMIT;

/*==============================================================*/
/* Insert: DATA                                                  */
/*==============================================================*/
