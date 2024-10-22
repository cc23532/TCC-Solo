CREATE VIEW appSolo.Food_Select AS
SELECT 
    id AS id_item,
    descricao_do_alimento + ' ' + descricao_da_preparacao AS Food_Name
FROM 
    appSolo.ibge_food_data