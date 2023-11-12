create table if not exists roles(
    id integer not null auto_increment,
    name varchar(255),
    primary key (id)
);

create table if not exists user_details (
    id integer not null auto_increment,
    user_name varchar(80) unique,
    password varchar(255),
    employee_id integer unique,
    role_id integer,
    created_date datetime(6),
    updated_date datetime(6),
    primary key (id),
    foreign key (role_id) references roles (id)
);

create table if not exists contact_details (
    id integer not null auto_increment,
    email varchar(255) unique,
    home_phone varchar(255),
    mobile_phone varchar(255),
    primary key (id)
);

create table if not exists address_details (
    id integer not null auto_increment,
    street varchar(255),
    city varchar(255),
    country varchar(255),
    state varchar(255),
    zip varchar(255),
    primary key (id)
);

create table if not exists employee (
    id integer not null auto_increment,
    first_name varchar(20),
    last_name varchar(50),
    position varchar(255),
    contact_details_id integer unique,
    primary key (id),
    foreign key (contact_details_id) references contact_details (id)
);

alter table user_details
    add constraint FKn5xxcggtt59ps5jdtdx5g78uh
        foreign key (employee_id)
        references employee (id);

create table if not exists vacancy (
    id integer not null auto_increment,
    job_title varchar(255),
    description varchar(255),
    salary float(53),
    start_date date,
    end_date date,
    status enum ('CLOSED','OPEN') not null,
    responsible_employee_id integer,
    primary key (id),
    foreign key (responsible_employee_id) references employee (id)
);

create table if not exists candidate (
    id integer not null auto_increment,
    first_name varchar(20),
    last_name varchar(50),
    date_of_birth date,
    address_id integer,
    contact_details_id integer unique,
    vacancy_id integer,
    status enum ('ACTIVE','BLACK_LISTED','IN_PROCESS','NOT_ACTIVE','REJECTED') not null,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id),
    foreign key (address_id) references address_details (id),
    foreign key (contact_details_id) references contact_details (id),
    foreign key (vacancy_id) references vacancy (id)
);

create table interview (
    id integer not null auto_increment,
    date date,
    time time(6) not null,
    location varchar(255),
    comments varchar(255),
    candidate_id integer,
    employee_id integer,
    status enum ('ACCEPTED','CANCELED','COMPLETED','NO_SHOW','PENDING_REVIEW','REJECTED','RESCHEDULED','SCHEDULED') not null,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id),
    foreign key (candidate_id) references candidate (id),
    foreign key (employee_id) references employee (id)
);

create table client (
    id integer not null auto_increment,
    company_name varchar(255),
    description varchar(255),
    primary key (id)
);

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
    primary key (id),
    foreign key (candidate_id) references candidate (id),
    foreign key (client_id) references client (id),
    foreign key (responsible_employee_id) references employee (id),
    foreign key (vacancy_id) references vacancy (id)
);

create table contract (
    id integer not null auto_increment,
    start_date date,
    end_date date,
    salary float(53),
    contract_type enum ('FULL_TIME', 'PART_TIME', 'INTERNSHIP', 'SEASONAL', 'CONTRACT_FOR_SERVICE') not null,
    candidate_id integer,
    responsible_employee_id integer,
    client_id integer,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id),
    foreign key (candidate_id) references candidate (id),
    foreign key (client_id) references client (id),
    foreign key (responsible_employee_id) references employee (id)
);

create table compensation (
    id integer not null auto_increment,
    candidate_id integer,
    salary float(53),
    payment_date date,
    comments varchar(255),
    contract_id integer,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id),
    foreign key (candidate_id) references candidate (id),
    foreign key (contract_id) references contract (id)
);

create table task (
    id integer not null auto_increment,
    title varchar(255),
    description varchar(255),
    start_date date,
    end_date date,
    status enum ('DONE','IN_PROCESS','OPENED','POSTPONED') not null,
    responsible_employee_id integer,
    candidate_id integer,
    vacancy_id integer,
    create_date datetime(6),
    update_date datetime(6),
    primary key (id),
    foreign key (candidate_id) references candidate (id),
    foreign key (responsible_employee_id) references employee (id),
    foreign key (vacancy_id) references vacancy (id)
);
