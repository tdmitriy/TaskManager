CREATE SCHEMA IF NOT EXISTS TaskManager
  CHAR SET = utf8
  COLLATE utf8_general_ci;

USE TaskManager;

DROP TABLE IF EXISTS TASK;
DROP TABLE IF EXISTS PRIORITY_TYPE;
DROP TABLE IF EXISTS STATE_TYPE;

CREATE TABLE TASK (
  id              INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  task_name       VARCHAR(128) NOT NULL,
  expiration_date DATE         NOT NULL,
  priority_id     INTEGER      NOT NULL,
  state_id        INTEGER      NOT NULL
)
  ENGINE = InnoDB, AUTO_INCREMENT = 1, DEFAULT CHARSET = utf8;

CREATE TABLE PRIORITY_TYPE (
  id            INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  priority_type VARCHAR(32) NOT NULL UNIQUE
)
  ENGINE = InnoDB, AUTO_INCREMENT = 1, DEFAULT CHARSET = utf8;

CREATE TABLE STATE_TYPE (
  id         INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  state_type VARCHAR(32) NOT NULL UNIQUE
)
  ENGINE = InnoDB, AUTO_INCREMENT = 1, DEFAULT CHARSET = utf8;


INSERT INTO PRIORITY_TYPE (priority_type) VALUES ('Low');
INSERT INTO PRIORITY_TYPE (priority_type) VALUES ('Medium');
INSERT INTO PRIORITY_TYPE (priority_type) VALUES ('High');

INSERT INTO STATE_TYPE (state_type) VALUES ('New');
INSERT INTO STATE_TYPE (state_type) VALUES ('Expired');
INSERT INTO STATE_TYPE (state_type) VALUES ('Finished');

INSERT INTO TASK (id, task_name, expiration_date, priority_id, state_id) VALUES
  (1, 'task1', '2015-11-05', 1, 1),
  (2, 'task2', '2015-01-22', 1, 2),
  (3, 'task3', '2015-02-11', 1, 3),
  (4, 'task4', '2015-02-23', 2, 2),
  (5, 'task5', '2015-03-02', 3, 1);