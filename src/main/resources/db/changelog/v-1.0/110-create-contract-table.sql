create table contract (
    id integer not null auto_increment,
    start_date date,
    end_date date,
    salary float(53),
    contract_type enum ('FULL_TIME', 'PART_TIME', 'INTERNSHIP', 'SEASONAL', 'CONTRACT_FOR_SERVICE') not null,
    candidate_id integer,
    responsible_employee_id integer,
    client_id integer,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id)
) engine=InnoDB
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