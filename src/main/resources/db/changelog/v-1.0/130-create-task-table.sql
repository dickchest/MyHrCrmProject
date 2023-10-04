create table task (
    id integer not null auto_increment,
    create_date datetime(6),
    description varchar(255),
    end_date date,
    last_update datetime(6),
    start_date date,
    status enum ('DONE','IN_PROCESS','OPENED','POSTPONED') not null,
    title varchar(255),
    candidate_id integer,
    responsible_employee_id integer,
    vacancy_id integer,
    primary key (id)
) engine=InnoDB
;

alter table task
add constraint UK_nwt3e3et25nmn9n4i3ot135r4 unique (candidate_id)
;

alter table task
add constraint UK_jil6p0gx77mcngplvkeaw2py5 unique (responsible_employee_id)
;

alter table task
add constraint UK_21eo5bjuxoq965qdqgvpabvl1 unique (vacancy_id)
;

alter table task
add constraint FKhq4cqvtiv1vxter4a6h66mnm7
foreign key (candidate_id)
references candidate (id)
;

alter table task
add constraint FK4sgerl9iy994m336y03m4omq
foreign key (responsible_employee_id)
references employee (id)
;

alter table task
add constraint FK6ll935le9w5ey09wmgs8ryonh
foreign key (vacancy_id)
references vacancy (id)
;