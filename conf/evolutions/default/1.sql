# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table board (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  owner_id                      bigint,
  constraint pk_board primary key (id)
);

create table board_list (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  sort_position                 bigint not null,
  board_id                      bigint,
  constraint pk_board_list primary key (id)
);

create table card (
  id                            bigint auto_increment not null,
  name                          varchar(255) not null,
  sort_position                 bigint not null,
  list_id                       bigint,
  card_member_id                bigint,
  constraint pk_card primary key (id)
);

create table user (
  id                            bigint auto_increment not null,
  username                      varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_user primary key (id)
);

create index ix_board_owner_id on board (owner_id);
alter table board add constraint fk_board_owner_id foreign key (owner_id) references user (id) on delete restrict on update restrict;

create index ix_board_list_board_id on board_list (board_id);
alter table board_list add constraint fk_board_list_board_id foreign key (board_id) references board (id) on delete restrict on update restrict;

create index ix_card_list_id on card (list_id);
alter table card add constraint fk_card_list_id foreign key (list_id) references board_list (id) on delete restrict on update restrict;

create index ix_card_card_member_id on card (card_member_id);
alter table card add constraint fk_card_card_member_id foreign key (card_member_id) references user (id) on delete restrict on update restrict;


# --- !Downs

alter table board drop constraint if exists fk_board_owner_id;
drop index if exists ix_board_owner_id;

alter table board_list drop constraint if exists fk_board_list_board_id;
drop index if exists ix_board_list_board_id;

alter table card drop constraint if exists fk_card_list_id;
drop index if exists ix_card_list_id;

alter table card drop constraint if exists fk_card_card_member_id;
drop index if exists ix_card_card_member_id;

drop table if exists board;

drop table if exists board_list;

drop table if exists card;

drop table if exists user;

