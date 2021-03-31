CREATE TABLE client_api.tb_usuario (
	id UUID PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	data_nascimento DATE NOT NULL,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP
);

--drop table tb_usuario;