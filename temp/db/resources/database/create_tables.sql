CREATE TABLE IF NOT EXISTS user_details
(
    id integer not null auto_increment,
    user_name varchar(80),
    password varchar(255),
    employee_id integer,
    role_id integer,
    created_date datetime(6),
    updated_date datetime(6),
    primary key (id)
)