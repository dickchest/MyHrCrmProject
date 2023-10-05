create table compensation (
    id integer not null auto_increment,
    comments varchar(255),
    create_date datetime(6),
    update_date datetime(6),
    payment_date date,
    salary float(53),
    candidate_id integer,
    contract_id integer,
    primary key (id)
) engine=InnoDB
;

alter table compensation
add constraint FKk2w7vr9qt4yen1w0fkfe4rhqx
foreign key (candidate_id)
references candidate (id)
;

alter table compensation
add constraint FK7903v7tnlhoa9idtiv01qkh8c
foreign key (contract_id)
references contract (id)
;