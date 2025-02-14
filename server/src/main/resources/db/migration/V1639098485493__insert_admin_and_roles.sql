-- insert admin
INSERT INTO "user"
  ("name", "email", "password", "email_confirmed")
VALUES
  ('admin', 'admin@email.com', '$2a$10$QBuMJLbVmHzgvTwpxDynSetACNdCBjU5zgo.81RWEDzH46aUrgcNK', true);

-- insert roles
INSERT INTO "role"
  ("name", "short_name", "description", "created_by")
VALUES
  ('ADMINISTRADOR', 'ADM', 'Administrador do sistema', (SELECT "id" FROM "user" WHERE "email" = 'admin@email.com')),
  ('USER',         'USER', 'Usuário do sistema',       (SELECT "id" FROM "user" WHERE "email" = 'admin@email.com'));

-- put roles into admin
INSERT INTO user_role
  ("user_id", "role_id")
VALUES
  (
    (SELECT id FROM "user" WHERE "email" = 'admin@email.com'),
    (SELECT id FROM "role" WHERE "short_name" = 'ADM')
  ),
  (
    (SELECT id FROM "user" WHERE "email" = 'admin@email.com'),
    (SELECT id FROM "role" WHERE "short_name" = 'USER')
  );