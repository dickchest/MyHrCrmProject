create table candidate (
    id integer not null auto_increment,
    first_name varchar(20),
    last_name varchar(50),
    date_of_birth date,
    address_id integer,
    contact_details_id integer,
    vacancy_id integer,
    status enum ('ACTIVE','BLACK_LISTED','IN_PROCESS','NOT_ACTIVE','REJECTED') not null,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id)
) engine=InnoDB
;

alter table candidate
add constraint UK_t0biwqd373dg20ia4tnub73i6 unique (contact_details_id)
;

alter table candidate
add constraint FKd1s618iem94mvatr7xi9puhia
foreign key (address_id)
references address_details (id)
;

alter table candidate
add constraint FKqjo5bl6w5plcoh1g07klc421o
foreign key (contact_details_id)
references contact_details (id)
;

alter table candidate
add constraint FK6n8vqigjcpdk7ewjbsk4u8jeg
foreign key (vacancy_id)
references vacancy (id)