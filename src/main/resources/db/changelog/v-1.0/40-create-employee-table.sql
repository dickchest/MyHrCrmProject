create table employee (
    id integer not null auto_increment,
    first_name varchar(20),
    last_name varchar(50),
    position varchar(255),
    contact_details_id integer,
    primary key (id)
) engine=InnoDB
;

alter table employee
    add constraint UK_i3ecosebkifjg6dvox06p1u5w unique (contact_details_id)
;

alter table employee
add constraint FKs3utm4v4xa7gmmav9pg5b0m0q
foreign key (contact_details_id)
references contact_details (id)
;

# alter table user_details
# add constraint FKn5xxcggtt59ps5jdtdx5g78uh
# foreign key (employee_id)
# references employee (id)
;