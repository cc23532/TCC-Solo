CREATE OR ALTER VIEW appSolo.StopSmokingSummary AS
SELECT 
    ss.idCount,
    ss.idUser,
    ss.baseDate,
    DATEDIFF(HOUR, ss.baseDate, CURRENT_TIMESTAMP) AS hoursWithoutSmoking,
    -- Cálculo de cigarros evitados: cigarros por dia dividido por 24 horas
    (DATEDIFF(HOUR, ss.baseDate, CURRENT_TIMESTAMP) * ss.cigsPerDay / 24.0) AS avoidedCigarettes,
    -- Cálculo de dinheiro economizado
    ((DATEDIFF(HOUR, ss.baseDate, CURRENT_TIMESTAMP) * ss.cigsPerDay / 24.0) / ss.cigsPerPack) * ss.packPrice AS moneySaved,
    -- Cálculo de minutos de vida ganhos
    (DATEDIFF(HOUR, ss.baseDate, CURRENT_TIMESTAMP) * ss.cigsPerDay * 11.0) / 60.0 AS lifeMinutesSaved
FROM 
    appSolo.StopSmoking ss;
