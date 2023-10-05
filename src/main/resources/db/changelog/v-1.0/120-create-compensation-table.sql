create table compensation (
    id integer not null auto_increment,
    candidate_id integer,
    salary float(53),
    payment_date date,
    comments varchar(255),
    contract_id integer,
    create_date datetime(6),
    update_date datetime(6),
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