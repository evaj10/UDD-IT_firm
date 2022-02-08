insert into education_level (level, name) values (1, 'osnovna skola');

insert into role (name) values ('ROLE_HR');

-- password: eva
insert into users (username, password, enabled, last_password_reset_date) values ('eva', '$2a$12$SU/2tcX0RY4kh0UDXRQFxOYhEH6HSWG6szwE99de16dbinhOr/vyu', true, '2017-10-01 21:58:58.508-07');

insert into user_role (user_id, role_id) values (1, 1);
