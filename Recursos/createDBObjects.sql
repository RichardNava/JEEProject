drop table products;
drop sequence pid_seq restrict;
create table products(
    id  INT PRIMARY KEY,
    name VARCHAR (40) NOT NULL,
    price NUMERIC (6,2) NOT NULL,
    best_before DATE,
    version INT DEFAULT 1);
create sequence pid_seq as int start with 1 increment by 1 no cycle;
insert into products values (next value for pid_seq, 'Cookie', 2.99, cast({ fn timestampadd(SQL_TSI_DAY,5,current_date) } as DATE), 1 );
insert into products values (next value for pid_seq, 'Cake', 3.99, cast({ fn timestampadd(SQL_TSI_DAY,5,current_date) } as DATE), 1 );
insert into products values (next value for pid_seq, 'Tea', 1.99, null, 1);
insert into products values (next value for pid_seq, 'Coffee', 1.99, null, 1);

