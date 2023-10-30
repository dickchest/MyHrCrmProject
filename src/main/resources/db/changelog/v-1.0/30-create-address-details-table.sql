create table address_details (
    id integer not null auto_increment,
    street varchar(255),
    city varchar(255),
    country varchar(255),
    state varchar(255),
    zip varchar(255),
    primary key (id)
) engine=InnoDB
;


