CREATE VIEW appSolo.MealDetails AS
SELECT 
    m.idMeal AS idMeal,
    m.mealDate AS Meal_Date,
    m.mealTime AS Meal_Time,
    mi.idItem AS id_item,
    fi.descricao_do_alimento + ' ' + fi.descricao_da_preparacao AS Food_Name,
    mi.weight AS Weight,
    fi.Energia_kcal AS Energy_kcal,
    fi.Carboidrato_g AS Carbohydrates,
    fi.Proteina_g AS Proteins,
    fi.Lipidios_totais_g AS Total_Fats,
    fi.AG_Saturados_g AS Saturated_Fats,
    fi.AG_Trans_total_g AS Trans_Fats,
    fi.Fibra_alimentar_total_g AS Dietary_Fiber,
    fi.Sodio_mg AS Sodium,
    fi.Acucar_total_g AS Sugars
FROM 
    appSolo.Meal m
JOIN 
    appSolo.Meal_Items mi ON m.idMeal = mi.idMeal
JOIN 
    appSolo.ibge_food_data fi ON mi.idFood = fi.id;
