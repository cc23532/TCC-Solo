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

    -- Consultar e contar os tipos de humor dentro do intervalo de tempo
    SELECT
        mood AS mood,
        COUNT(*) AS moodCount
    FROM appSolo.UserMood
    WHERE idUser = @idUser
      AND moodDate BETWEEN @startDate AND @endDate
    GROUP BY mood;
END;
