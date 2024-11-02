CREATE PROCEDURE appSolo.GetFinancesSumByTimeFrame
    @timeFrame VARCHAR(20),
    @idUser INT
AS
BEGIN
    DECLARE @startDate DATETIME;
    DECLARE @endDate DATETIME = GETDATE();

    -- Definindo a data de início com base no intervalo de tempo
    IF @timeFrame = 'last7days'
        SET @startDate = DATEADD(DAY, -7, @endDate);
    ELSE IF @timeFrame = 'last30days'
        SET @startDate = DATEADD(DAY, -30, @endDate);
    ELSE IF @timeFrame = 'lastYear'
        SET @startDate = DATEADD(YEAR, -1, @endDate);
    ELSE -- 'alltime'
        SET @startDate = (SELECT created_at FROM appSolo.SoloUser WHERE id = @idUser);

    SELECT 
        (SELECT SUM(moneyValue) 
         FROM appSolo.Finances 
         WHERE transactionType = 'income' 
           AND activityDate BETWEEN @startDate AND @endDate
           AND idUser = @idUser) AS TotalIncomes,
        (SELECT SUM(moneyValue) 
         FROM appSolo.Finances 
         WHERE transactionType = 'expense' 
           AND activityDate BETWEEN @startDate AND @endDate
           AND idUser = @idUser) AS TotalExpenses;
END;
