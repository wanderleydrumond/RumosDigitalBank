CREATE TABLE rumos_digital_bank.customers
(
    id         INT SERIAL DEFAULT VALUE AUTO_INCREMENT NOT NULL,
    nif        varchar(9)                              NOT NULL,
    name       varchar(50)                             NOT NULL,
    password   varchar(16)                             NOT NULL,
    phone      varchar(9)                                  NULL,
    mobile     varchar(9)                                  NULL,
    email      varchar(50)                             NOT NULL,
    profession varchar(50)                                 NULL,
    birthdate date                                     NOT NULL,

    CONSTRAINT customers_pk
        PRIMARY KEY (id),
    CONSTRAINT customers_unique
        UNIQUE (nif, email, mobile)
);

CREATE TABLE rumos_digital_bank.accounts
(
    id           INT SERIAL DEFAULT VALUE AUTO_INCREMENT NOT NULL,
    code         INT                                         NULL,
    balance      DOUBLE PRECISION(5,2)                   NOT NULL,
    customers_id INT                                     NOT NULL,
    CONSTRAINT accounts_pk
        PRIMARY KEY (id),
    CONSTRAINT accounts_customers_id_fk
        FOREIGN KEY (customers_id) REFERENCES rumos_digital_bank.customers (id)
);

CREATE TABLE rumos_digital_bank.customers_accounts
(
    customers_id INT NOT NULL,
    accounts_id  INT NOT NULL,
    CONSTRAINT customers_accounts_pk
        PRIMARY KEY (customers_id, accounts_id),
    CONSTRAINT customers_accounts_accounts_id_fk
        FOREIGN KEY (accounts_id) REFERENCES rumos_digital_bank.accounts (id),
    CONSTRAINT customers_accounts_customers_id_fk
        FOREIGN KEY (customers_id) REFERENCES rumos_digital_bank.customers (id)
);

CREATE TABLE rumos_digital_bank.movements
(
    id          INT SERIAL DEFAULT VALUE AUTO_INCREMENT NOT NULL,
    type        VARCHAR(12)                             NOT NULL,
    date        DATE                                    NOT NULL,
    value       DOUBLE PRECISION(5,2)                   NOT NULL,
    accounts_id INT                                     NOT NULL,
    CONSTRAINT movements_pk
        PRIMARY KEY (id),
    CONSTRAINT movements_accounts_id_fk
        FOREIGN KEY (accounts_id) REFERENCES rumos_digital_bank.accounts (id)
);

CREATE TABLE rumos_digital_bank.cards
(
    id              INT SERIAL DEFAULT VALUE AUTO_INCREMENT NOT NULL,
    serial_number   VARCHAR(5)                              NOT NULL,
    pin             INT                                     NOT NULL,
    is_virgin       BOOLEAN                                 NOT NULL,
    monthly_plafond DOUBLE PRECISION(5,2)                   NOT NULL,
    plafond_balance DOUBLE PRECISION(5,2)                   NOT NULL,
    customers_id    INT                                     NOT NULL,
    accounts_id     INT                                     NOT NULL,
    CONSTRAINT cards_pk
        PRIMARY KEY (id),
    CONSTRAINT cards_unique
        UNIQUE (serial_number),
    CONSTRAINT cards_accounts_id_fk
        FOREIGN KEY (accounts_id) REFERENCES rumos_digital_bank.accounts (id),
    CONSTRAINT cards_customers_id_fk
        FOREIGN KEY (customers_id) REFERENCES rumos_digital_bank.customers (id)
);

DROP TABLE cards;
DROP TABLE movements;
DROP TABLE customers_accounts;
DROP TABLE accounts;
DROP TABLE customers;

DESCRIBE accounts;

DELETE FROM customers;
DELETE FROM accounts;
DELETE FROM movements;

SELECT * FROM customers;
SELECT * FROM customers_accounts;
SELECT * FROM accounts;
SELECT * FROM movements;

INSERT INTO customers (id, nif, name, password, phone, mobile, email, profession, birthdate) VALUES(3, 333333333, 'kljnbg', 'luihbgd', '214785963', '987456321', 'ljhnbd@linjsb', 'linbd', '1911-11-11');
INSERT INTO accounts(id, code, balance, customers_id) VALUES(1, 100, 51.24, 1);
INSERT INTO accounts(id, code, balance, customers_id) VALUES(2, 101, 51.24, 1);
INSERT INTO accounts(id, code, balance, customers_id) VALUES(3, 102, 51.24, 1);
INSERT INTO customers_accounts (customers_id, accounts_id) VALUES (5, 6);
INSERT INTO customers_accounts (customers_id, accounts_id) VALUES (6, 5);


SHOW TRIGGERS;





# ******************

SELECT version();

SELECT @@GLOBAL.sql_mode;
SET GLOBAL SQL_MODE = '';

SELECT last_insert_id();

SELECT MAX(id) FROM accounts;

# Este trigger gera codes automaticamentemas tem um bug:
# Se eu já tiver inserido vários registros e depois deletar todos,
# o registro seguinte virá com o code igual a 100, os demais estarão corretos
CREATE TRIGGER
    account_number_generator
    BEFORE INSERT
ON accounts FOR EACH ROW BEGIN
    SET NEW.code = (SELECT IFNULL(MAX(id), 0) + 100 FROM accounts);
    END;

DROP TRIGGER IF EXISTS account_number_generator;

INSERT INTO accounts (balance, customers_id) VALUES (50, 1);
SELECT code FROM accounts WHERE id = last_insert_id();
ALTER TABLE accounts MODIFY code int null;

DELETE FROM accounts WHERE id > 1;
DELETE FROM customers WHERE id > 1;





# Tentativa de dar a volta ao problema do trigger. Ainda não sei oque por na clausula WHERE
INSERT accounts (id, code, balance, customers_id)
VALUES (8, LAST_INSERT_ID((
    SELECT IFNULL(MAX(id), 0) + 100
    FROM accounts
    WHERE id = accounts.id
)), 50., 7);


# Outra tentativa de dar a volta ao problema do trigger. Não soube botar essa função para funcionar mas a lógica está correta
DELIMITER $$
CREATE FUNCTION generateAccountCode()
    RETURNS INT(10)
BEGIN
    DECLARE getCount INT(10);
    SET getCount = (SELECT COUNT(id) FROM accounts) + 100;
    RETURN getCount;
END$$
DELIMITER ;

DROP FUNCTION IF EXISTS generateAccountCode;