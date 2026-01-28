INSERT INTO Role(id, name)
VALUES (0x0318236D2B59414089BF7A5E73C67C8A, 'ADMIN'),
       (0x6AE6FFD34E7ACC448A5E6FE629047B6D, 'USER');

INSERT INTO Usuario(id, email, nome, senha, username,role_id)
VALUES (0xA09585A27DDB44458E87A986F5EEAC10, 'teste@teste.com', 'juliano', '$123456',
        'julianoosmir',0x0318236D2B59414089BF7A5E73C67C8A);

INSERT INTO Usuario(id, email, nome, senha, username,role_id)
VALUES (0x93ea4ce0a23b4b0aba4e99f4d89d0f92, 'teste@teste.com', 'usuario', '$123456',
        'usuario',0x6AE6FFD34E7ACC448A5E6FE629047B6D);