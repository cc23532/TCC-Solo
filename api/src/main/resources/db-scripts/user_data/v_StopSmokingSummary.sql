CREATE VIEW appSolo.StopSmokingSummary AS
SELECT 
    ss.idCount,
    ss.idUser,
    ss.baseDate,
    DATEDIFF(CURRENT_TIMESTAMP, ss.baseDate) AS daysWithoutSmoking,
    DATEDIFF(CURRENT_TIMESTAMP, ss.baseDate) * ss.cigsPerDay AS avoidedCigarettes,
    (DATEDIFF(CURRENT_TIMESTAMP, ss.baseDate) * ss.cigsPerDay / ss.cigsPerPack) * ss.packPrice AS moneySaved,
    (DATEDIFF(CURRENT_TIMESTAMP, ss.baseDate) * ss.cigsPerDay * 11) / 60 AS lifeMinutesSaved
FROM 
    appSolo.StopSmoking ss;
