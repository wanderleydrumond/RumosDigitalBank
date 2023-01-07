create table rumos_digital_bank.customers
(
    id         int SERIAL DEFAULT VALUE auto_increment not null,
    nif        varchar(9)                              not null,
    name       varchar(50)                             not null,
    password   varchar(16)                             not null,
    phone      varchar(9)                              null,
    mobile     varchar(9)                              null,
    email      varchar(50)                             not null,
    profession varchar(50)                             null,
    birthdate date                                     not null,

    constraint customers_pk
        primary key (id),
    constraint customers_unique
        unique (nif, email, mobile)
);

create table rumos_digital_bank.accounts
(
    id          int SERIAL DEFAULT VALUE auto_increment,
    code        int          not null,
    balance     double       not null,
    accounts_id int          null,
    constraint accounts_pk
        primary key (id),
    constraint accounts_customers_id_fk
        foreign key (accounts_id) references rumos_digital_bank.customers (id)
);

create table rumos_digital_bank.customers_accounts
(
    customers_id int not null,
    accounts_id  int not null,
    constraint customers_accounts_pk
        primary key (customers_id, accounts_id),
    constraint customers_accounts_accounts_id_fk
        foreign key (accounts_id) references rumos_digital_bank.accounts (id),
    constraint customers_accounts_customers_id_fk
        foreign key (customers_id) references rumos_digital_bank.customers (id)
);

create table rumos_digital_bank.movements
(
    id          int SERIAL DEFAULT VALUE auto_increment,
    type        varchar(12) not null,
    date        date        not null,
    value       double      not null,
    accounts_id int         not null,
    constraint movements_pk
        primary key (id),
    constraint movements_accounts_id_fk
        foreign key (accounts_id) references rumos_digital_bank.accounts (id)
);

create table rumos_digital_bank.cards
(
    id              int SERIAL DEFAULT VALUE auto_increment,
    serial_number   varchar(5)  not null,
    pin             int         not null,
    is_virgin       boolean     not null,
    monthly_plafond double      not null,
    plafond_balance double      not null,
    customers_id    int         not null,
    accounts_id     int         not null,
    constraint cards_pk
        primary key (id),
    constraint cards_unique
        unique (serial_number),
    constraint cards_accounts_id_fk
        foreign key (accounts_id) references rumos_digital_bank.accounts (id),
    constraint cards_customers_id_fk
        foreign key (customers_id) references rumos_digital_bank.customers (id)
);

DROP TABLE cards;
DROP TABLE movements;
DROP TABLE customers_accounts;
DROP TABLE accounts;
DROP TABLE customers;

SELECT * FROM customers;