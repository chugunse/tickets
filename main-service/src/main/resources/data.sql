insert into users (login, password, full_name)
values ('администратор', '$2a$10$9P4VTjDICtqwgcnCtEfTOe.A6uO5xVc7tfHyMdREO/ITYGk2a8zWW', 'Тестов Тест Тестович');

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN');