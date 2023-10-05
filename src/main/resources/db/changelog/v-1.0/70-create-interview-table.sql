create table interview (
    id integer not null auto_increment,
    comments varchar(255),
    date date,
    location varchar(255),
    status enum ('ACCEPTED','CANCELED','COMPLETED','NO_SHOW','PENDING_REVIEW','REJECTED','RESCHEDULED','SCHEDULED') not null,
    time time(6),
    candidate_id integer,
    employee_id integer,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id)
) engine=InnoDB
;

alter table interview
add constraint UK_pe0kf6sjqo34dsqis5gsraeoe unique (employee_id)
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