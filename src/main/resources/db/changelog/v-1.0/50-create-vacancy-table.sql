create table vacancy (
    id integer not null auto_increment,
    job_title varchar(255),
    description varchar(255),
    salary float(53),
    start_date date,
    end_date date,
    status enum ('CLOSED','OPEN') not null,
    responsible_employee_id integer,
    primary key (id)
) engine=InnoDB
;

alter table vacancy
add constraint FK3uy53cbi4ew8ju3hcx4789ace
foreign key (responsible_employee_id)
references employee (id)
;