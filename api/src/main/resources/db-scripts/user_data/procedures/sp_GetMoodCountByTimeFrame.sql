CREATE OR ALTER PROCEDURE appSolo.GetMoodCountByTimeFrame
    @timeFrame VARCHAR(20),
    @idUser INT
AS
BEGIN
    DECLARE @startDate DATE;
    DECLARE @endDate DATE = GETDATE();

    -- Definir a data de início com base no período de tempo
    IF @timeFrame = 'last7days'
        SET @startDate = DATEADD(DAY, -7, @endDate);
    ELSE IF @timeFrame = 'last30days'
        SET @startDate = DATEADD(DAY, -30, @endDate);
    ELSE IF @timeFrame = 'lastYear'
        SET @startDate = DATEADD(YEAR, -1, @endDate);
    ELSE -- 'alltime'
        SET @startDate = (SELECT created_at FROM appSolo.SoloUser WHERE id = @idUser);

    -- Criar uma tabela derivada com os moods válidos
    WITH ValidMoods AS (
        SELECT 'veryhappy' AS mood
        UNION ALL
        SELECT 'happy'
        UNION ALL
        SELECT 'normal'
        UNION ALL
        SELECT 'unhappy'
        UNION ALL
        SELECT 'sad'
    )
    -- Consultar e contar os tipos de humor válidos, incluindo zeros para os que não existem
    SELECT
        vm.mood,
        ISNULL(um.moodCount, 0) AS moodCount
    FROM ValidMoods vm
    LEFT JOIN (
        SELECT
            mood,
            COUNT(*) AS moodCount
        FROM appSolo.UserMood
        WHERE idUser = @idUser
          AND moodDate BETWEEN @startDate AND @endDate
          AND mood IN ('veryhappy', 'happy', 'normal', 'unhappy', 'sad') -- Filtrar apenas os moods válidos
        GROUP BY mood
    ) um ON vm.mood = um.mood;
END;
