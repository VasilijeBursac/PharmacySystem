
insert into authority(id,name) values (1,'ROLE_PATIENT');
insert into authority(id,name) values (2, 'ROLE_SYSTEM_ADMIN');
insert into authority(id,name) values (3, 'ROLE_PHARMACY_ADMIN');
insert into authority(id,name) values (4, 'ROLE_DERMATOLOGIST');
insert into authority(id,name) values (5, 'ROLE_PHARMACIST');
insert into authority(id,name) values (6, 'ROLE_SUPPLIER');

insert into contraindications( id, name) values (1, 'Preosetljivost na metronidazol');
insert into contraindications( id, name) values (2, 'Abdominalni bol');
insert into contraindications( id, name) values (3, 'Pogoršanje astme');
insert into contraindications( id, name) values (4, 'Deca mlađa od 2 godine');

/* --- DERMATOLOGISTS --- */

/* pera@gmail.com, pera */
insert into dermatologist(id,address, city, country, email, name, password, phone_number, surname, price, confirmed, activated) 
values (1,'Jovana Ducica 22','Beograd','Srbija','pera@gmail.com','Pera','$2y$10$4pqzt42GblUIbx6vrwSGhObU5wtpGd1EaCXSLqnXh0dIeouX5P98e',
		'231432123','Peric', 1500, true, true);
		
/* zika@gmail.com, zika */
insert into dermatologist(id,address, city, country, email, name, password, phone_number, surname, price, confirmed, activated) 
values (2,'Janka Cmelika 9','Novi Sad','Srbija','vasilijebursac@gmail.com','Zika','$2y$10$MN616XcMIPO4vqY2bjNEQO97f9mmT6fbFuinbLLylKr0QrJoIJSWm',
		'0617788996','Zikic', 2000, false, true);
		
/* marko@gmail.com, marko */
insert into dermatologist(id,address, city, country, email, name, password, phone_number, surname, price, confirmed, activated) 
values (3,'Bulevar Oslobodjenja 49','Nis','Srbija','marko@gmail.com','Marko','$2y$10$zsoFKgzcVBAUFgjNLNPrGeIK1Utd7ZBEik9GlhDNYZXA.0yVltuaq',
		'021879554','Markovic', 1700, true, true);
		
/* petar@gmail.com, petar */
insert into dermatologist(id,address, city, country, email, name, password, phone_number, surname, price, confirmed, activated) 
values (4,'Trg Slobode 4','Subotica','Srbija','petar@gmail.com','Petar','$2y$10$8a2ynifL9RQtjOOVJA3RfO.LcNzmievmarf54iJTM.fg8LgTtGwbu',
		'06987445','Petrovic', 1400, true, true);
		
/* --- PATIENTS --- */
		
		
/* filip@gmail.com, filip */
insert into patient(id,address, city, confirmed, country, email, name, password, phone_number, surname, loyalty_points, penalty_points, activated) 
values (5,'Bulevar Mihajla Pupina 2','Beograd', true,'Srbija','filip@gmail.com','Filip','$2y$10$Y8ssB0gdD8hGqvTmAeh6n.ZD7lpKoQkNYlKzUJrCCsjsTy73tGVJS',
		'061985622','Filipovic', 120, 1, true);
		
/* mihajlo@gmail.com, mihajlo */
insert into patient(id,address, city, confirmed, country, email, name, password, phone_number, surname, loyalty_points, penalty_points, activated) 
values (6,'Ruzveltova 34','Beograd', true,'Srbija','mihajlo@gmail.com','Mihajlo','$2y$10$RsVLRg9b4gIw8IegArZGi.FGP3IkbG8/97D/TyYjSyslbEDSIzq2.',
		'01147884','Mihajlovic', 10, 0, true);
		
/* ana@gmail.com, ana */
insert into patient(id,address, city, confirmed, country, email, name, password, phone_number, surname, loyalty_points, penalty_points, activated) 
values (7,'Bulevar Oslobodjenja 56','Novi Sad', true,'Srbija','ana@gmail.com','Ana','$2y$10$Ot3AbKBsBoZSRign2IFkI.vuY6iuUbKxGhtWJY39D9oH6OGJWbUFq',
		'061985622','Anic', 0, 1, true);
		
/* kristina@gmail.com, kristina */
insert into patient(id,address, city, confirmed, country, email, name, password, phone_number, surname, loyalty_points, penalty_points, activated) 
values (8,'Somborski bulevar 97','Zajecar', true,'Srbija','kristina@gmail.com','Kristina','$2y$10$IZLi/JPnH2LrQ9TxyVVhZOpLCY.Z3ZJGp1szResXjLUOFCTb/4vum',
		'061985622','Krstic', 27, 2, true);


/* --- PHARMACIES --- */
		
insert into pharmacy(id, name, address, city, country, pharmacist_price, description) values (1,'Apoteka Novi Sad', 'Narodnog Fronta 12','Novi Sad', 'Srbija', 1100, 'Sjajno snabdevena apoteka, sa vrlo povoljnim cenama. Ljubazno osoblje će Vam pomoći da pronađete sve što Vam treba.');
insert into pharmacy(id, name, address, city, country, pharmacist_price, description) values (2,'Apoteka Sirmijum', 'Save Tekelije 14','Zrenjanin ', 'Srbija', 900, 'Apoteka u kojoj je zaposlen tim vrhunskih stručnjaka. Pregledi i savetovanja po najpovoljnijim cenama. Veliki asortiman lekova.');



insert into subscribed_pharmacies(patient_id, pharmacy_id) values (5, 1);
insert into subscribed_pharmacies(patient_id, pharmacy_id) values (6, 2);
insert into subscribed_pharmacies(patient_id, pharmacy_id) values (6, 1);

/* --- PHARMACISTS --- */
		
/* dimitrije@gmail.com, dimi */
insert into pharmacist(id,address, city, country, email, name, password, phone_number, surname, end_date, start_date, pharmacy_id, confirmed, activated) 
values (9,'Bulevar Mihajla Pupina 2','Zitiste','Srbija','vasilijebursac@gmail.com','Dimitrije','$2y$10$3vrKs8fQ4UL9h93TCSfOPOGsGYks8rKmvkUPHgwonIfv.8Jl1yQhi',
		'061985022','Bulaja','2021-03-15','2021-03-01', 1, false, true);
		
/* mihajlo@gmail.com, mika */
insert into pharmacist(id,address, city, country, email, name, password, phone_number, surname, end_date, start_date, pharmacy_id, confirmed, activated) 
values (10,'Ruzveltova 21','Zrenjanin','Srbija','mihajlo@gmail.com','Mihajlo','$2y$10$JIe9RowUDmlOW7vKSDiBnuZup7wo/I0y1l052uWHNaIuWMYPJkB7m',
		'01147881','Omaljev','2021-05-29','2021-01-11', 2, false, true);

/* mario@gmail.com, kima */		
insert into pharmacist(id,address, city, country, email, name, password, phone_number, surname, end_date, start_date, pharmacy_id, confirmed, activated) 
values (17,'Bulevar Mihajla Pupina 11','Zrenjanin','Srbija','vasilijebursac@gmail.com','Mario','$2y$10$zbIaMV.WAa0D4vj7kS274O8awW6OJoC1KIUBiJ3NpgdAjBGtefcy2',
		'061985022','Petromanjanc','2021-09-11','2021-02-11', 1, false, true);
		
/* ivica@gmail.com, ivica */
insert into pharmacist(id,address, city, country, email, name, password, phone_number, surname, end_date,start_date,pharmacy_id,confirmed, activated) 
values (18,'Ruzveltova 21','Zrenjanin','Srbija','mihajlo@gmail.com','Ivica','$2y$10$cavm.nwcllSXz6W8aPjtKuYrkafL9nSPaKDz45LVaHWmRMWJBZXp2',
		'01147881','Novakov','2021-06-29','2021-01-21', 2, false, true);
		
/* brankica@gmail.com, brankica */
insert into pharmacist(id,address, city, country, email, name, password, phone_number, surname, end_date,start_date,pharmacy_id,confirmed, activated) 
values (21,'Gagarinova 12','Temerin','Srbija','brankica@gmail.com','Brankica','$2a$10$syfyVc6Xz1H7STXNR6YIDuVF54lpVU70co6LR9xaH/zk2rLP6tknO',
		'01155555','Brankovic','2021-06-29','2021-01-21', 1, true, true);

		
/* --- SYSTEM_ADMINISTRATORS --- */
		
/* dejan@gmail.com, deki */
insert into system_administrator(id,address, city, confirmed, country, email, name, password, phone_number, surname, activated) 
values (11,'Bulevar Oslobodjenja 9','Novi Sad', true, 'Srbija','dejan@gmail.com','Dejan','$2y$10$c.hDMq1zCr96WdxerDedN.LwjiWQHUB/1nrpP6XGfLY.gninxZUqG',
		'061385622', 'Nedeljkovic', true);
		
/* nemanja@gmail.com, neca */
insert into system_administrator(id,address, city, confirmed,country, email, name, password, phone_number, surname, activated) 
values (12,'Somborski bulevar 97','Zvornik', true,'Srbija','nemanja@gmail.com','Nemanja','$2y$10$bRNOF2hrIy9CROZ8ZHcreumOotmoKt6JCxgb3PoigVz0p.VxATlBC',
		'061981622','Jevtic', true);

/* --- PHARMACY_ADMINISTRATORS --- */
		
/* milena@gmail.com, milence */
insert into pharmacy_administrator(id,address, city, country, email, name, password, phone_number, surname, pharmacy_id, confirmed, activated) 
values (13,'Balzakova 1','Novi Sad','Srbija','milena@gmail.com','Milena','$2y$10$7u1AWYA7wW3UkcB2PchaTOnenZwVo0TTSbL9w2I68feFLt/u9vI8G',
		'061385600','Kovacevic', 1, false, true);
		
/* dragisa@gmail.com, dragisa */
insert into pharmacy_administrator(id,address, city, country, email, name, password, phone_number, surname, pharmacy_id, confirmed, activated) 
values (20,'Ćirpanova 12','Novi Sad','Srbija','dragisa@gmail.com','Dragiša','$2a$10$ltTDdNAKxp0Rwxfv2koKfeVOi00FYjnKLZrDg7hMzqTbxnVWZhCOG',
		'061333633','Dragić', 1, true, true);
		
/* denis@gmail.com, denis */
insert into pharmacy_administrator(id,address, city, country, email, name, password, phone_number, surname, pharmacy_id, confirmed, activated) 
values (14,'Somborski bulevar 90','Zrenjanin','Srbija','denis@gmail.com','Denis','$2y$10$ihpttwUywTQ2Iw2FBXYfGueEACL239/xdGRguelnU/3GsS4sPxQja',
		'062981622','Fruza', 2, false, true);


/* --- SUPPLIER --- */
		
/* jovan@gmail.com, jovan */
insert into supplier(id,address, city, confirmed, country, email, name, password, phone_number, surname, activated) 
values (15,'Balzakova 2','Novi Sad', true,'Srbija','vasilijebursac@gmail.com','Jovan','$2y$10$GnY077C6faJvHeE8nfEr.e3rFBxQfaY7mz62ChyKiRp/Nchpglw4m',
		'061385600','Cvijovic', true);
		
/* danijel@gmail.com, danijel */
insert into supplier(id,address, city,confirmed, country, email, name, password, phone_number, surname, activated) 
values (16,'Somborski bulevar 91','Zrenjanin', true,'Srbija','vasilijebursac@gmail.com','Danijel','$2y$10$tmCL0qXq3J797yMJtd9xmuaaeOVYolsFPZfSPbhm/1rcoJL7DMDZO',
		'062981622','Novakovic', true);
		
/* natasa@gmail.com, nata */
insert into supplier(id,address, city, confirmed, country, email, name, password, phone_number, surname, activated) 
values (19,'Somborski bulevar 99','Zrenjanin', true,'Srbija','vasilijebursac@gmail.com','Natasa','$2y$10$SDsigowv761RRb9KIuiYwedVzSWvI05uIiR1o7MTp9QcaXS3fgS/.',
		'062941622','Subotic', true);

		
insert into user_authority( user_id, authority_id) values (1,4);
insert into user_authority( user_id, authority_id) values (2,4);
insert into user_authority( user_id, authority_id) values (3,4);
insert into user_authority( user_id, authority_id) values (4,4);
insert into user_authority( user_id, authority_id) values (5,1);
insert into user_authority( user_id, authority_id) values (6,1);
insert into user_authority( user_id, authority_id) values (7,1);
insert into user_authority( user_id, authority_id) values (8,1);
insert into user_authority( user_id, authority_id) values (9,5);
insert into user_authority( user_id, authority_id) values (10,5);
insert into user_authority( user_id, authority_id) values (9,5);
insert into user_authority( user_id, authority_id) values (17,5);
insert into user_authority( user_id, authority_id) values (18,5);
insert into user_authority( user_id, authority_id) values (10,5);
insert into user_authority( user_id, authority_id) values (11,2);
insert into user_authority( user_id, authority_id) values (12,2);
insert into user_authority( user_id, authority_id) values (13,3);
insert into user_authority( user_id, authority_id) values (14,3);
insert into user_authority( user_id, authority_id) values (15,6);
insert into user_authority( user_id, authority_id) values (16,6);
insert into user_authority( user_id, authority_id) values (19,6);
insert into user_authority( user_id, authority_id) values (20,3);
insert into user_authority( user_id, authority_id) values (21,5);

insert into drug_specification(id, suggested_dose) values (1, '20mg');
insert into drug_specification(id, suggested_dose) values (2, '300mg');
insert into drug_specification(id, suggested_dose) values (3, '1g');
insert into drug_specification(id, suggested_dose) values (4, '10mg');
insert into drug_specification(id, suggested_dose) values (5, '5mg');
		
insert into drug(id, name, code, type, shape, manifacturer, additional_info, loyalty_points, drug_specification_id) values (1, 'Aspirin','231432', 'antibiotik', 'prasak', 'Jugoremedija', 'Uzimati dva puta dnevno', 5, 1);
insert into drug(id, name, code, type, shape, manifacturer, additional_info, loyalty_points, drug_specification_id) values (2, 'Bensedin','125697','antibiotik', 'kapsula', 'Hemofarm', 'Uzimati jednom dnevno', 3, 2);
insert into drug(id, name, code, type, shape, manifacturer, additional_info, loyalty_points, drug_specification_id) values (3, 'Bisoprolol','365895', 'anestetik', 'mast', 'Jugoremedija', 'Uzimati tri puta dnevno', 4, 3);
insert into drug(id, name, code, type, shape, manifacturer, additional_info, loyalty_points, drug_specification_id) values (4, 'Brufen','023547', 'anetetik', 'gel', 'Hemofarm', 'Cuvati na hladnom', 5, 4);
insert into drug(id, name, code, type, shape, manifacturer, additional_Info, loyalty_points, drug_specification_id) values (5, 'Bulardi','785493', 'probiotik', 'sirup', 'Jugoremedija', 'Uzimati jendom denvo', 5, 5);

insert into substitute_drugs(substitute_drug_id,main_drug_id) values(2,1);
insert into substitute_drugs(substitute_drug_id,main_drug_id) values(3,1);
insert into substitute_drugs(substitute_drug_id,main_drug_id) values(4,1);
insert into substitute_drugs(substitute_drug_id,main_drug_id) values(5,1);
insert into substitute_drugs(substitute_drug_id,main_drug_id) values(5,2);

insert into drug_allergies(drug_id, patient_id) values ( 3, 5);
insert into drug_allergies(drug_id, patient_id) values ( 4, 5);
insert into drug_allergies( drug_id, patient_id) values ( 3, 8);
insert into drug_allergies(drug_id, patient_id) values ( 4, 6);
insert into drug_allergies(drug_id, patient_id) values ( 4, 7);

		
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (4.7, 3, null,null, 5, null);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (5, 3, null,null, 5, null);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (4, null, 10,null, 5, null);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (3, null, 9,null, 7 , null);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (4, null, null, 1, 5, null);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (5, null, null, null, 5, 1);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (4, null, null, null, 5, 2);
insert into rating(rating, dermatologist_id, pharmacist_id, pharmacy_id, patient_id, drug_id)
values (3, null, null, null, 5, 3);


 /* -- INGREDIENTS --*/
insert into ingredient values (1,'Amilorid');
insert into ingredient values (2,'Hidrohortiazid');
insert into ingredient values (3,'Escitaloparm');
insert into ingredient values (4,'Vekluri');
insert into ingredient values (5,'Ibuprofen');
insert into ingredient values (6,'Piridoksin');
insert into ingredient values (7,'Benfotiamni');

/* -- INGREDIENTS IN DRUG SPECIFICATION --*/
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (1,1);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (1,2);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (1,3);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (2,4);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (2,5);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (4,1);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (3,2);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (3,3);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (5,4);
insert into ingredient_in_drug_specification(drug_specification_id,ingredient_id) values (5,5);

/* -- CONTRAINDICATIONS IN DRUG SPECIFICATION --*/
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (1,1);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (1,2);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (1,3);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (2,4);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (2,2);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (4,1);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (3,2);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (3,3);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (5,4);
insert into contraindications_in_drug_specification(drug_specification_id,contraindication_id) values (5,3);

insert into examination_loyalty_points(type, points) values ('DermatologistExamination', 15);
insert into examination_loyalty_points(type, points) values ('PharmacistExamiantion', 10);


insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Created','2021-09-11', '2021-09-07', null, 9);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Created','2021-10-15', '2021-09-18', null, 17);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Approved','2021-03-22', '2021-02-19', null, 21);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Created','2021-11-25', '2021-10-28', null, 10);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values ('Neozbiljan radnik.', 'Rejected','2021-04-25', '2021-03-25', null, 18);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Created','2021-10-15', '2021-09-26', 2, null);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Created','2021-10-20', '2021-09-25', 3, null);
insert into vacation_request( response_message, status, end_date, start_date, dermatologist_id, pharmacist_id) values (null, 'Approved','2021-09-30', '2021-09-25', 1, null);


insert into promotion(id, end_date, start_date, description, pharmacy_id) values (1,'2021-03-22', '2021-02-19', '20 posto popusta', 1);
insert into promotion(id, end_date, start_date, description, pharmacy_id) values (2,'2021-05-01', '2021-04-20','10 posto popusta' , 1);
insert into promotion(id, end_date, start_date, description, pharmacy_id) values (3,'2021-11-20', '2021-11-26', '20 posto popusta', 2);
insert into promotion(id, end_date, start_date, description, pharmacy_id) values (4,'2021-02-22', '2021-02-17', '30 posto popusta', 2);

insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (1, 20, 1, 15);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (2, 24, 2, 15);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (3, 50, 3, 15);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (4, 40, 4, 16);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (5, 65, 5, 16);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (6, 25, 1, 16);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (7, 35, 2, 19);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (8, 15, 3, 19);
insert into drug_in_storage(id, quantity, drug_id, supplier_id) values (9, 10, 4, 19);

insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 15, 1);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 15, 2);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 15, 3);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 16, 4);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 16, 5);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 16, 6);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 19, 7);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 19, 8);
insert into supplier_drugs_in_storage( supplier_id, drugs_in_storage_id) values ( 19, 9);

insert into orders( deadline, status, administrator_id) values ( '2021-10-17', 'Fulfilled', 13);
insert into orders( deadline, status, administrator_id) values ( '2021-10-22', 'Waiting', 13);
insert into orders( deadline, status, administrator_id) values ( '2021-10-08', 'Waiting', 14);
insert into orders( deadline, status, administrator_id) values ( '2021-10-22', 'Fulfilled', 14);
insert into orders( deadline, status, administrator_id) values ( '2021-12-11', 'Fulfilled', 14);
insert into orders( deadline, status, administrator_id) values ( '2021-08-30', 'Waiting', 20);
insert into orders( deadline, status, administrator_id) values ( '2021-08-29', 'Fulfilled', 13);
insert into orders( deadline, status, administrator_id) values ( '2021-08-28', 'Waiting', 13);
insert into orders( deadline, status, administrator_id) values ( '2021-09-30', 'Waiting', 13);
insert into orders( deadline, status, administrator_id) values ( '2021-10-01', 'Waiting', 13);
/*-- 3-1400 1-1450 2-1500 --*/

insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-11', 'Accepted', 3350, 1, 15);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-11', 'Waiting', 3350, 2, 15);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-11', 'Waiting', 6700, 3, 15);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-01', 'Waiting', 1500, 6, 15);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-02', 'Waiting', 1850, 6, 16);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-08-31', 'Accepted', 2000, 7, 15);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-01', 'Declined', 2300, 7, 16);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-02', 'Declined', 2450, 7, 19);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-08-29', 'Waiting', 2900, 8, 15);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-08-30', 'Waiting', 3100, 8, 16);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-09-01', 'Waiting', 2950, 8, 19);
insert into offer(delivery_deadline, status, total_price, order_id, supplier_id) 
		values ( '2021-10-03', 'Waiting', 3700, 10, 15);


insert into drug_in_order( drug_id, order_id, quantity) values ( 1, 1, 10);
insert into drug_in_order( drug_id, order_id, quantity) values ( 2, 1, 20);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 1, 15);
insert into drug_in_order( drug_id, order_id, quantity) values ( 5, 2, 9);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 2, 2);
insert into drug_in_order( drug_id, order_id, quantity) values ( 1, 3, 5);
insert into drug_in_order( drug_id, order_id, quantity) values ( 2, 3, 6);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 3, 19);
insert into drug_in_order( drug_id, order_id, quantity) values ( 5, 6, 10);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 6, 15);
insert into drug_in_order( drug_id, order_id, quantity) values ( 5, 7, 20);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 7, 30);
insert into drug_in_order( drug_id, order_id, quantity) values ( 4, 7, 10);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 8, 5);
insert into drug_in_order( drug_id, order_id, quantity) values ( 4, 8, 15);
insert into drug_in_order( drug_id, order_id, quantity) values ( 5, 8, 35);
insert into drug_in_order( drug_id, order_id, quantity) values ( 4, 9, 10);
insert into drug_in_order( drug_id, order_id, quantity) values ( 5, 9, 25);
insert into drug_in_order( drug_id, order_id, quantity) values ( 3, 10, 20);
insert into drug_in_order( drug_id, order_id, quantity) values ( 4, 10, 20);

/*-- DERMATOLOGISTS IN PHARMACY --*/

insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (1,1);
--insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (1,2);
insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (1,3);
insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (1,4);
insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (2,1);
insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (2,3);
insert into dermatologists_in_pharmacy(pharmacy_id,dermatologist_id) values (2,2);


insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
values('2021-06-01 15:00','2021-06-01 7:00',1,1);
--insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
--values('2021-06-02 15:00','2021-06-02 7:00',2,1);
insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
values('2021-07-06 15:00','2021-07-06 7:00',3,1);
insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
values('2021-06-02 20:00','2021-06-02 15:00',4,1);
insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
values('2021-06-02 20:00','2021-06-02 15:00',1,2);
insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
values('2021-06-02 20:00','2021-06-02 15:00',3,2);
insert into dermatologist_work_schedule(end_date,start_date,dermatologist_id,pharmacy_id)
values('2021-06-02 20:00','2021-06-02 15:00',2,2);

/*-- SCHEDULED EXAMINATIONS -DERMATOLOGIST  --*/

insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-02-13 12:00', -1, 'DermatologistExamination', 5, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-02-14 12:00', -1, 'DermatologistExamination', 5, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Unfilled', 30, '2021-10-13 13:00', -1, 'DermatologistExamination', null, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Alergijska reakcija na sunce', 1, 1500, 'Filled', 30, '2021-02-01 12:00', 30, 'DermatologistExamination', 7, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Osip', 3, 1500, 'Filled', 30, '2021-12-13 14:00', 10, 'DermatologistExamination', 8, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Ekcemska reakcija', 3, 1500, 'Unfilled', 30, '2021-10-14 13:30', 11, 'DermatologistExamination', 7, 1);
 
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Ekcem', 1, 1400, 'Filled', 30, '2021-02-15 12:00', 7, 'DermatologistExamination', 5, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Unfilled', 30, '2021-10-16 12:00', 30, 'DermatologistExamination', null, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Psorijaza', 1, 1400, 'Filled', 30, '2020-12-17 12:00', 5, 'DermatologistExamination', 5, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Osip', 1, 1500, 'Filled', 30, '2020-12-18 12:00', 10, 'DermatologistExamination', 6, 2);
 
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)

values(null, 2, 1800, 'Filled', 30, '2021-02-15 13:00', 30, 'DermatologistExamination', 7, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 2, 1800, 'Filled', 30, '2021-02-16 14:30', 30, 'DermatologistExamination', 8, 2);
 
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1800, 'Unfilled', 30, '2021-10-17 10:00', 30, 'DermatologistExamination', null, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 3, 1400, 'Unfilled', 30, '2021-10-18 10:00', 30, 'DermatologistExamination', null, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 3, 1400, 'Filled', 30, '2021-02-18 11:30', 30, 'DermatologistExamination', 5, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1750, 'Filled', 30, '2021-02-18 13:30', 30, 'DermatologistExamination', 6, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 4, 1750, 'Unfilled', 30, '2021-10-18 14:30', 30, 'DermatologistExamination', null, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-09-20 13:00', 30, 'DermatologistExamination', 6, 1);

insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-09-02 13:00', 30, 'DermatologistExamination', 6, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-09-01 13:00', 30, 'DermatologistExamination', 6, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-08-29 13:00', 30, 'DermatologistExamination', 6, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-08-29 13:00', 30, 'DermatologistExamination', 6, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 1, 1500, 'Filled', 30, '2021-08-15 13:00', 30, 'DermatologistExamination', 6, 1);



/*-- SCHEDULED EXAMINATIONS -PHARMACIST --*/

insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Blaza upala grla', 10, 1200, 'Filled', 30, '2021-02-15 10:00', 4, 'PharmacistExamination', 5, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 2', 18, 1300, 'Filled', 30, '2020-03-15 13:00', 14, 'PharmacistExamination', 6, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 3', 9, 1300, 'Filled', 30, '2020-02-15 16:00', 14, 'PharmacistExamination', 5, 1);
 insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Blazi tip sorijaze ', 9, 1300, 'Filled', 30, '2021-02-16 09:00', 14, 'PharmacistExamination', 7, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Ekcemska reakcija', 9, 1300, 'Filled', 30, '2021-02-16 11:00', 14, 'PharmacistExamination', 7, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Suga', 9, 1300, 'Filled', 30, '2021-02-16 14:00', 14, 'PharmacistExamination', 8, 1);

insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 9, 1300, 'Unfilled', 30, '2021-10-14 09:00', 0, 'PharmacistExamination', null, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 18, 1400, 'Unfilled', 30, '2021-10-14 11:00', 0, 'PharmacistExamination', null, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 17, 1400, 'Unfilled', 30, '2021-10-14 13:30', 0, 'PharmacistExamination', null, 1);
 
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 10, 1100, 'Unfilled', 30, '2021-10-16 10:30', 30, 'PharmacistExamination', null, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values(null, 18, 1200, 'Filled', 30, '2021-02-16 11:30', 30, 'PharmacistExamination', 5, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 4', 10, 1100, 'Filled', 30, '2021-02-17 10:30', 11, 'PharmacistExamination', 7, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 5', 9, 1200, 'Filled', 30, '2021-02-17 11:30', 6, 'PharmacistExamination', 7, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 6', 10, 1250, 'Filled', 30, '2021-02-17 14:30', 3, 'PharmacistExamination', 5, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Blaza prehlada', 18, 1200, 'Filled', 30, '2021-02-18 11:30', 30, 'PharmacistExamination', 5, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 4', 10, 1100, 'Filled', 30, '2021-02-18 13:30', 11, 'PharmacistExamination', 7, 2);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 5', 9, 1200, 'Filled', 30, '2021-02-18 9:30', 6, 'PharmacistExamination', 7, 1);
insert into examination(diagnose, emplyeed_id, price, status, duration, start, therapy_duration, type, patient_id, pharmacy_id)
values('Opis 6', 10, 1250, 'Filled', 30, '2021-02-19 12:30', 3, 'PharmacistExamination', 5, 2);

 
/* -- DRUG IN PHARMACY --*/
/* drug_id, pharmacy_id, quantity, pricelist*/
insert into drug_in_pharmacy values(3,1,30,null);
insert into drug_in_pharmacy values(4,1,40,null);
insert into drug_in_pharmacy values(5,1,10,null);
insert into drug_in_pharmacy values(1,2,100,null);
insert into drug_in_pharmacy values(2,2,30,null);
insert into drug_in_pharmacy values(4,2,20,null);
insert into drug_in_pharmacy values(5,2,80,null);


 /* -- PRICELIST --*/

insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (3, 1400,'2020-12-01 9:00','2021-12-01 9:00',3,1);
insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (4, 1800, '2020-12-01 9:00','2021-12-01 9:00',4,1);
insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (5, 2150,'2020-12-01 9:00','2021-12-01 9:00',5,1);
insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (6, 1450,'2020-12-01 9:00','2021-12-01 9:00',1,2);
insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (7, 1500,'2020-12-01 9:00','2021-12-01 9:00',2,2);
insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (8, 1700,'2020-12-01 9:00','2021-12-01 9:00',4,2);
insert into pricelist(id, price,end_date,start_date,drug_in_pharmacy_drug_id, drug_in_pharmacy_pharmaci_id)
values (9, 1300,'2020-12-01 9:00','2021-12-01 9:00',5,2);
 
 
/* -- UPDATE PRICELIST FOR DRUG IN PHARMACY --*/
update drug_in_pharmacy set pricelist_id = 3 where drug_id = 3 and pharmaci_id = 1;
update drug_in_pharmacy set pricelist_id = 4 where drug_id = 4 and pharmaci_id = 1;
update drug_in_pharmacy set pricelist_id = 5 where drug_id = 5 and pharmaci_id = 1;
update drug_in_pharmacy set pricelist_id = 6 where drug_id = 1 and pharmaci_id = 2;
update drug_in_pharmacy set pricelist_id = 7 where drug_id = 2 and pharmaci_id = 2;
update drug_in_pharmacy set pricelist_id = 8 where drug_id = 4 and pharmaci_id = 2;
update drug_in_pharmacy set pricelist_id = 9 where drug_id = 5 and pharmaci_id = 2;




	/* -- DRUG RESERVATION --*/

 insert into drug_reservation(reservation_to_date,selling_price,status,patient_id,reserved_drug_drug_id,reserved_drug_pharmaci_id)
 values ('2021-03-09 10:00',0,0,7,3,1);
 insert into drug_reservation(reservation_to_date,selling_price,status,patient_id,reserved_drug_drug_id,reserved_drug_pharmaci_id)
 values ('2021-02-06 11:00',1500,1,5,4,1);
 
 insert into drug_reservation(reservation_to_date,selling_price,status,patient_id,reserved_drug_drug_id,reserved_drug_pharmaci_id)
 values ('2021-09-02 11:00',0,0,5,4,1);

 insert into drug_reservation(reservation_to_date,selling_price,status,patient_id,reserved_drug_drug_id,reserved_drug_pharmaci_id)
 values ('2021-05-07 10:00',0,2,5,1,2);
 insert into drug_reservation(reservation_to_date,selling_price,status,patient_id,reserved_drug_drug_id,reserved_drug_pharmaci_id)
 values ('2021-03-04 12:00',0,0,8,1,2);

 insert into drug_reservation(reservation_to_date,selling_price,status,patient_id,reserved_drug_drug_id,reserved_drug_pharmaci_id)
 values ('2021-02-09 12:00',0,0,6,1,2);
  
 /* -- DRUG DEFICIT QUERY -- */
 
 
 /* -- ERECIPE --*/
 
 insert into erecipe(date_of_issue,patient_id, code) values ('2021-01-01 12:00',8, '2345');
 insert into erecipe(date_of_issue,patient_id, code) values ('2021-01-01 10:00',5, '1246');
 insert into erecipe(date_of_issue,patient_id, code) values ('2020-12-01 9:00',5, '3547');
 
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (1, 1, 1, 5);
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (1, 2, 1, 5);
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (2, 2, 1, 1);
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (3, 2, 1, 1);
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (4, 2, 2, 1);
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (5, 2, 2, 2);
 insert into drug_inerecipe(drug_id, e_recipe_id, pharmaci_id, quantity_inerecipe) values (1, 3, 1, 2);
 
  insert into loyalty_category(id, name, lower, upper, discount)
 values (1, 'Bronze', 0, 50, 10);
 insert into loyalty_category(id, name, lower, upper, discount)
 values (2, 'Silver', 51, 100, 20);
 insert into loyalty_category(id, name, lower, upper, discount)
 values (3, 'Gold', 101, 10000, 30);

 /* -- ERECIPE --*/
 
 insert into complaint(id, object_id, responded, text, type, patient_id) values (1, 1, false, 'Ne posvecuje dovoljno paznje pacijentima!','DermatologistComplaint', 5);
 insert into complaint(id, object_id, responded, text, type, patient_id) values (2, 9, false, 'Jako bezobrazan i neljubazan!','PharmacistComplaint', 5);
 insert into complaint(id, object_id, responded, text, type, patient_id) values (3, 1, false, 'Prevelike cene i neljubazni radnici!','PharmacyComplaint', 5);
