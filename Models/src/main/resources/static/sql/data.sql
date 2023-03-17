insert into users(id, login, password, registration_date)
values ('47a072c8-a19a-4ff1-96d8-b06bffecc096',
        'admin',
        '$2a$06$NTLiBpn0uwixwo6AiCUb7OvWLuj8sP8XkyPOV7w2t99rywCK6xikm', -- equals 'password'
        now())
ON CONFLICT DO NOTHING;

insert into authorities(user_id, role)
values ('47a072c8-a19a-4ff1-96d8-b06bffecc096', 'Administrator')
ON CONFLICT DO NOTHING;