# ATM Interface
Build an ATM Interface which first starts a a console application prompted the user to enter user id and pin. On entering the details successfully, the ATM functionalities are unlocked.
Using Javafx, The project allows to perform the follow operations:

* Transaction History
* Withdraw
* Deposit
* Transfer
* Quit



## Requirements: 

You need have MySQL running on your system



## Setting Up The Schema:

Below is some MySQL code snippet  that will help you set up your MySQL Database in other to work the App.



### Create Database

With this one you can give your Database any desirable name, or you can name your Database the same with your project which **ATM** 

Your clone of these project needs some editing for it to be functional. First you need change the name of the Database in the **UserDB** class if your have it different and your password which is a most

#### Create Database

```mysql
create database ATM;
```



#### Switch to AMT

```mysql
use AMT;
```



#### User Table:

Start with the User table

```mysql
create table users
(
    user_id char(3) not null,
    user_pin char(6) not null,
    fname varchar(25) not null,
    lname varchar(25) not null,
    primary key(user_id)
);
```

##### Insert Some Dummy Data Into Users:

```mysql
insert into users
    (user_id, user_pin, fname, lname)
values('123', 'code12', 'ebrima', 'touray');
insert into users
    (user_id, user_pin, fname, lname)
values('113', 'code10', 'alasana', 'camara');
insert into users
    (user_id, user_pin, fname, lname)
values('133', 'code14', 'ismail', 'turner');
insert into users
    (user_id,user_pin, fname, lname)
values('132', 'code12', 'haddy', 'jobe');
insert into users
	(user_id,user_pin, fname, lname)
		values('134', 'code14', 'binta', 'cham');
```



#### Account Table:

```mysql
create table account
(
    acc_num char(6) not null,
    acc_name varchar(15) not null,
    current_balance decimal(7,2) default 0,
    date_created timestamp default current_timestamp,
    acc_holder char(3) NOT NULL,
    primary key(acc_num),
    foreign key(acc_holder) references users(user_id)
);
```

##### Insert Some Dummy Data In To Account:

```mysql
insert into account(acc_num, acc_name, acc_holder)
values('acc001', 'saving', '123');

insert into account(acc_num, acc_name, acc_holder)
values('acc002', 'saving', '122');

insert into account(acc_num, acc_name, acc_holder)
values('acc003', 'saving', '133');

insert into account(acc_num, acc_name, acc_holder)
values('acc004', 'saving', '132');

insert into account(acc_num, acc_name, acc_holder)
values('acc005', 'saving', '142');

insert into account(acc_num, acc_name, acc_holder)
values('acc008', 'saving', '134');
```



#### Transactions Table

```mysql
create table transactions
(
    trans_date timestamp default current_timestamp,
    trans_type varchar(12) not null,
    trans_amount decimal(7,2),
    account char(6)NOT NULL,
    primary key(trans_date),
    foreign key(account) references account(acc_num)
);
```



The values of this table are not going to be insert manually rather they are to obtain dynamically which are also called drive attributes(MySQL term). After every transaction(Deposit and Withdrawal) this table will updated.



#### Transfer Transaction Table

```mysql
create table transfer_trans
(
    transfer_date timestamp default current_timestamp,
    sender char(6) not null,
    recipient char(6) not null,
    amount decimal(7,2) not null,
    primary key(transfer_date, sender),
    foreign key(recipient) references account(acc_num)
);
```



The values of this table are not going to be insert manually rather they are to obtain dynamically which are also called drive attributes(MySQL term). After every transaction(Transfer) this table will updated.