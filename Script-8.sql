drop table ers_reimbursement_status;
drop table ers_reimbursement_type;
drop table ers_user_roles;
drop table ers_users;
drop table ers_reimbursement;



create table ers_reimbursement_status(
reimb_status_id numeric primary key, 
reimb_status varchar(10));


create table ers_reimbursement_type(
reimb_type_id numeric primary key,
reimb_type varchar(10));


create table ers_user_roles(
ers_user_role_id numeric primary key,
user_role varchar(25));


create table ers_users(
ers_users_id numeric primary key,
ers_username varchar(50) unique,
ers_password varchar(500),
user_first_name varchar(100),
user_last_name varchar(100),
user_email varchar(150) unique,
user_role_id numeric REFERENCES ers_user_roles(ers_user_role_id) not NULL);


create table ers_reimbursement(
reimb_id serial primary key, 
reimb_amount numeric not null, 
reimb_submitted timestamp not null, 
reimb_resolved timestamp,
reimb_description varchar(250) not null, 
reimb_receipt bytea,
reimb_author numeric REFERENCES ers_users(ers_users_id) not NULL,
reimb_resolver numeric REFERENCES ers_users(ers_users_id),
reimb_status_id numeric REFERENCES ers_reimbursement_status(reimb_status_id) not NULL,
reimb_type_id numeric REFERENCES ers_reimbursement_type(reimb_type_id) not NULL);

insert into ers_reimbursement_status values 
('1','Pending'),
('2', 'Denied'),
('3', 'Approved');


insert into ers_reimbursement_type values 
('1','Food'),
('2', 'Lodging'),
('3', 'Travel'),
('4', 'Other');


insert into ers_user_roles (ers_user_role_id, user_role) values 
('1','Employee'),
('2', 'Finance Manager');


insert into ers_users (ers_users_id, ers_username, ers_password, user_first_name, user_last_name,user_email, user_role_id) values 
('500','RegEmpA', '654c8177d2c47270ccc085d32d964da60afbcafa0425981dfb94eb13d7106be60540b97f5077565d4c9457709a9347858833ae19af0199f458a8c51079662c50', 'Edward', 'Jones', 'EdwardJones@revature.com', '1'),
('600','FinManA', '654c8177d2c47270ccc085d32d964da60afbcafa0425981dfb94eb13d7106be60540b97f5077565d4c9457709a9347858833ae19af0199f458a8c51079662c50', 'Frank', 'Johnson', 'FrankJohnson@revature.com', '2'),
('700','FinManB', '654c8177d2c47270ccc085d32d964da60afbcafa0425981dfb94eb13d7106be60540b97f5077565d4c9457709a9347858833ae19af0199f458a8c51079662c50', 'Forest', 'White', 'ForestWhite@revature.com', '2');


insert into ers_reimbursement (reimb_author, reimb_amount, reimb_submitted, reimb_description, reimb_type_id, reimb_status_id) values
(500, '29.99', '2018-12-20', 'Big Fat Expensive Pizza', 1, 1),
(500, '428.58', '2019-01-31', 'Sciurus vulgaris plus Tax', 4, 1),
(500, '1337', '2019-01-15', '30 Day Trip to Heaven', 2, 1),
(600, '7.50', '2019-08-23', 'Garlic Mushroom Expense',  1, 1),
(600, '250.00', '2019-09-05', 'Revature Airplane Ticket', 3, 1),
(600, '799.99', '2019-09-13', 'Lasiodora parahybana', 4, 1),
(700, '306.66', '2019-09-07', 'Naja haje', 1, 1),
(700, '400.01', '2019-04-14', 'Aegypius tracheliotus Hotel', 2, 1),
(700, '311.11', '2018-11-14', 'Tetracerus quadricornis 3 Day Journey', 3, 1);


--see if user exist
select * from ers_users where ers_username = 'RegEmpA'


-- select information from user with provided login information
select * from ers_users where ers_username = 'RegEmpA' and ers_password = 'password';


-- select ticket
SELECT * FROM ers_reimbursement WHERE reimb_author = '500';

SELECT * FROM ers_reimbursement left  join ers_users 
on ers_reimbursement.reimb_id = ers_users.ers_users_id 
where ers_users.ers_username='RegEmpA'

-- create ticket
insert into ers_reimbursement 
(reimb_amount, reimb_submitted,  reimb_description,
reimb_receipt, reimb_author, reimb_status_id, reimb_type_id) values 
('100.00',current_date,  'First Day Pizza Meeting', 'receipt-placeholder', '500' , '1', '1');



-- view current & past tickets by ID
select reimb_id,reimb_amount, reimb_submitted, reimb_resolved, reimb_description,
reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status, ers_reimbursement_type.reimb_type
from ers_reimbursement 
LEFT JOIN ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id 
LEFT JOIN ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id 
where ers_reimbursement.reimb_author = '500';


-- view pending/denied/completed tickets by Status
select reimb_id,reimb_amount, reimb_submitted, reimb_resolved, reimb_description,
reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status, ers_reimbursement_type.reimb_type
from ers_reimbursement 
LEFT JOIN ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id 
LEFT JOIN ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id 
where ers_reimbursement_status.reimb_status_id = '3';


--update tickets
select * from ers_reimbursement;
update ers_reimbursement set reimb_status_id = '2', reimb_resolver = '600', reimb_resolved = current_date where reimb_id = 1


select * from ers_reimbursement natural join ers_reimbursement_status natural join ers_reimbursement_type where reimb_id = 1



insert into ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) 
values('234.44',current_date, 'testing'  , '500', '1', '3' ) 
RETURNING reimb_id 


insert into ers_reimbursement(reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) \r\n" + 
					"values(?,current_date, ?  , ?, '1', ? ) \r\n" + 
					"RETURNING reimb_id