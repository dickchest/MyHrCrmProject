create table interview (
    id integer not null auto_increment,
    date date,
    time time(6) not null,
    location varchar(255),
    comments varchar(255),
    candidate_id integer,
    employee_id integer,
    status enum ('ACCEPTED','CANCELED','COMPLETED','NO_SHOW','PENDING_REVIEW','REJECTED','RESCHEDULED','SCHEDULED') not null,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id)
) engine=InnoDB
;

alter table interview
add constraint FKjod0wwyxvbi7qyx9cmlnt8xq4
foreign key (candidate_id)
references candidate (id)
;

alter table interview
add constraint FKefxxgrsb5h84h5rj06h8ittv9
foreign key (employee_id)
references employee (id)
;