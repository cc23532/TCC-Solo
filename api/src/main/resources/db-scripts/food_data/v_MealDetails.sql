CREATE OR ALTER VIEW appSolo.MealDetails AS
SELECT 
    m.idMeal AS idMeal,
    m.mealDate AS Meal_Date,
    m.mealTime AS Meal_Time,
    mi.idItem AS id_item,
    fi.descricao_do_alimento + ' ' + fi.descricao_da_preparacao AS Food_Name,
    mi.weight AS Weight,
    CAST(fi.Energia_kcal * (mi.weight / 100) AS DECIMAL(10, 2)) AS Energy_kcal,
    CAST(fi.Carboidrato_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Carbohydrates,
    CAST(fi.Proteina_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Proteins,
    CAST(fi.Lipidios_totais_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Total_Fats,
    CAST(fi.AG_Saturados_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Saturated_Fats,
    CAST(fi.AG_Trans_total_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Trans_Fats,
    CAST(fi.Fibra_alimentar_total_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Dietary_Fiber,
    CAST(fi.Sodio_mg * (mi.weight / 100) AS DECIMAL(10, 2)) AS Sodium,
    CAST(fi.Acucar_total_g * (mi.weight / 100) AS DECIMAL(10, 2)) AS Sugars
FROM 
    appSolo.Meal m
JOIN 
    appSolo.Meal_Items mi ON m.idMeal = mi.idMeal
JOIN 
    appSolo.ibge_food_data fi ON mi.idFood = fi.id;
