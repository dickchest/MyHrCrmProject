create table client_candidate_relation (
    client_id integer not null,
    candidate_id integer not null
) engine=InnoDB
;

alter table client_candidate_relation
add constraint FKtdh0lnynedqf1s81dmjg7vkmj
foreign key (candidate_id)
references candidate (id)
;

alter table client_candidate_relation
add constraint FK2kmbl3cl47cj487yoqhld90x1
foreign key (client_id)
references client (id)
;