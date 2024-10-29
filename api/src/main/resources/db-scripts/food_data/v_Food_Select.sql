CREATE VIEW appSolo.Food_Select AS
SELECT 
    id AS id_item,
    descricao_do_alimento + 
    CASE 
        WHEN descricao_da_preparacao = 'Não se aplica' THEN ''
        ELSE ' ' + descricao_da_preparacao 
    END AS Food_Name
FROM 
    appSolo.ibge_food_data;
