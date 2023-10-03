create table user_details (
    id integer not null auto_increment,
    created_date datetime(6),
    password varchar(255),
    updated_date datetime(6),
    user_name varchar(80),
    employee_id integer,
    role_id integer,
    primary key (id)
) engine=InnoDB

GO

alter table user_details
    add constraint UK_7pqjkt6mwigem3tve6e8j2qlp unique (user_name)

GO

alter table user_details
    add constraint UK_snognowm6mtx7jx0ymh7x2xks unique (employee_id)

GO

alter table user_details
    add constraint FKqct1sekkjpbuki9c8jkewydqr
    foreign key (role_id)
    references roles (id)

GO

# alter table user_details
#     add constraint FKn5xxcggtt59ps5jdtdx5g78uh
#     foreign key (employee_id)
#     references employee (id)