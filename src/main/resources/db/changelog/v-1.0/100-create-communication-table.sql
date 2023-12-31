create table communication (
    id integer not null auto_increment,
    communication_date_time datetime(6),
    communication_type enum ('EMAIL', 'PHONE', 'CHAT', 'SOCIAL_MEDIA', 'MAIL', 'FAX') not null,
    client_id integer,
    candidate_id integer,
    vacancy_id integer,
    responsible_employee_id integer,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id)
) engine=InnoDB
;

alter table communication
add constraint FK4bkq2uu08p1atlj299dj65mh6
foreign key (candidate_id)
references candidate (id)
;

alter table communication
add constraint FKtk92s4h62otwq7bvve6k4xueb
foreign key (client_id)
references client (id)
;

alter table communication
add constraint FKicovkwob7axbvsrluny4ysg5k
foreign key (responsible_employee_id)
references employee (id)
;

alter table communication
add constraint FKkufkbu7gx6f9hysfq6xmxge4l
foreign key (vacancy_id)
references vacancy (id)
;