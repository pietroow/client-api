CREATE OR REPLACE VIEW client_api.vw_cliente AS
SELECT *, extract(year from age(CURRENT_DATE, cliente.data_nascimento)) as IDADE
FROM client_api.tb_cliente cliente;