DELETE FROM rc_user;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO rc_user (email, password, role, first_name, last_name, gender, birth_day)
VALUES ('vasya@gmail.com', '{noop}password', 'USER', 'Vasya', 'Pupkin', 'MALE', '1982-06-11'),
       ('vasya2@gmail.com', '{noop}password', 'USER', 'Vasya2', 'Pupkin2', 'MALE', '1982-06-02'),
       ('vasya3@gmail.com', '{noop}password', 'USER', 'Vasya3', 'Pupkin3', 'MALE', '1982-06-03'),
       ('vasya4@gmail.com', '{noop}password', 'USER', 'Vasya4', 'Pupkin4', 'MALE', '1984-06-04'),
       ('vasya5@gmail.com', '{noop}password', 'USER', 'Vasya5', 'Pupkin5', 'MALE', '1985-06-05'),
       ('vasya6@gmail.com', '{noop}password', 'USER', 'Vasya6', 'Pupkin6', 'MALE', '1986-06-06'),
       ('vasya7@gmail.com', '{noop}password', 'USER', 'Vasya7', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya8@gmail.com', '{noop}password', 'USER', 'Vasya8', 'Pupkin8', 'MALE', '1988-06-08'),
       ('vasya9@gmail.com', '{noop}password', 'USER', 'Vasya9', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya10@gmail.com', '{noop}password', 'USER', 'Vasya10', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya11@gmail.com', '{noop}password', 'USER', 'Vasya11', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya12@gmail.com', '{noop}password', 'USER', 'Vasya12', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya13@gmail.com', '{noop}password', 'USER', 'Vasya13', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya14@gmail.com', '{noop}password', 'USER', 'Vasya14', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya15@gmail.com', '{noop}password', 'USER', 'Vasya15', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya16@gmail.com', '{noop}password', 'USER', 'Vasya16', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya17@gmail.com', '{noop}password', 'USER', 'Vasya17', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya18@gmail.com', '{noop}password', 'USER', 'Vasya18', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya19@gmail.com', '{noop}password', 'USER', 'Vasya19', 'Pupkin7', 'MALE', '1987-06-07'),
       ('vasya20@gmail.com', '{noop}password', 'USER', 'Vasya20', 'Pupkin7', 'MALE', '1987-06-07');

