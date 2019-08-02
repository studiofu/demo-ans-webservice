DROP TABLE IF EXISTS T_POLICY_MASTER;
DROP TABLE IF EXISTS T_CLIENT;
 
  
CREATE TABLE T_POLICY_MASTER (
	UNIQUE_ID INT AUTO_INCREMENT  PRIMARY KEY,
	POLICY_NUMBER VARCHAR(10) NOT NULL,
	OWNER_CLIENT_NUMBER INT NOT NULL,
	PLAN_CODE VARCHAR(250) NOT NULL	
);

INSERT INTO T_POLICY_MASTER (POLICY_NUMBER, OWNER_CLIENT_NUMBER, PLAN_CODE) VALUES
('2019000001',1000,'ANNUITY'),
('2019000002',1001,'ANNUITY'),
('2019000003',1002,'ANNUITY')
;

CREATE TABLE T_CLIENT_MASTER (
	UNIQUE_ID INT AUTO_INCREMENT  PRIMARY KEY,
	CLIENT_ID INT NOT NULL,
	FIRST_NAME VARCHAR(250) NOT NULL,
	LAST_NAME VARCHAR(250) NOT NULL	
);

INSERT INTO T_CLIENT_MASTER (CLIENT_ID, FIRST_NAME, LAST_NAME) VALUES 
(1000, 'Tom', 'Lau'),
(1001, 'Tai Man', 'Chan'),
(1002, 'ABC', 'Wong')
;

