insert into users(id, login, password, registration_date)
values ('47a072c8-a19a-4ff1-96d8-b06bffecc096', 'admin',
        '$2a$16$uM0l2gWoBNbsLL431vweUefGIp685woBaRbtzM3BMMZWvBQO2WEi6',
        now())
ON CONFLICT DO NOTHING;

insert into authorities(user_id, role)
values ('47a072c8-a19a-4ff1-96d8-b06bffecc096', 'Administrator')
ON CONFLICT DO NOTHING;