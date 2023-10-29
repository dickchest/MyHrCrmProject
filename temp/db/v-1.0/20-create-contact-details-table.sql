create table contact_details (
    id integer not null auto_increment,
    email varchar(255),
    home_phone varchar(255),
    mobile_phone varchar(255),
    primary key (id)
) engine=InnoDB
;

alter table contact_details
add constraint UK_1a28vet8t25bte6gjqhwrjlsc unique (email)
;


