INSERT INTO nurse(ID, FIRST_NAME ,LAST_NAME) values ( 1, 'Eigenia', 'Liashuk' );
INSERT INTO nurse(ID, FIRST_NAME ,LAST_NAME) values ( 2, 'Ekaterina', 'Kozura' );
INSERT INTO nurse(ID, FIRST_NAME ,LAST_NAME) values ( 3, 'Tatsiana', 'Rezko' );

INSERT INTO patient(ID, FIRST_NAME, LAST_NAME, DIAGNOSIS, ILLNESS_DATE)
VALUES (1, 'Dzianis', 'Berastsen', 'Skalioz', '2021-10-01');
INSERT INTO patient(ID, FIRST_NAME, LAST_NAME, DIAGNOSIS, ILLNESS_DATE)
VALUES (2, 'Kirill', 'Yrkovski', 'qwe', '2021-05-15');
INSERT INTO patient(ID, FIRST_NAME, LAST_NAME, DIAGNOSIS, ILLNESS_DATE)
VALUES (3, 'Vladislav', 'Ivanov', 'xzc', '2021-01-29');
INSERT INTO patient(ID, FIRST_NAME, LAST_NAME, DIAGNOSIS, ILLNESS_DATE)
VALUES (4, 'Valeriy', 'Sergeev', 'sdfg', '2021-05-15');

INSERT INTO nurses_patient(ID, NURSE_ID, PATIENT_ID)
VALUES (1, 1, 3);
INSERT INTO nurses_patient(ID, NURSE_ID, PATIENT_ID)
VALUES (1, 3, 2);
INSERT INTO nurses_patient(ID, NURSE_ID, PATIENT_ID)
VALUES (1, 2, 3);
INSERT INTO nurses_patient(ID, NURSE_ID, PATIENT_ID)
VALUES (1, 2, 4)