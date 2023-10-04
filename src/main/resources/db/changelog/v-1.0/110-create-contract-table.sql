create table contract (
    id integer not null auto_increment,
    contract_type varchar(255),
    create_date datetime(6),
    end_date date,
    last_update datetime(6),
    salary float(53),
    start_date date,
    candidate_id integer,
    client_id integer,
    responsible_employee_id integer,
    primary key (id)
) engine=InnoDB
;

alter table contract
add constraint UK_mrht5112houo8jqd5vdouw8b3 unique (candidate_id)
;

alter table contract
add constraint FKcp9q63l07rrrrapl3mlj2sroy
foreign key (candidate_id)
references candidate (id)
;

alter table contract
add constraint FKlhq3p3xl25vvnfvyfc51ica0j
foreign key (client_id)
references client (id)
;

alter table contract
add constraint FKlcfymsqge87ptsn2nne26fe62
foreign key (responsible_employee_id)
references employee (id)
;