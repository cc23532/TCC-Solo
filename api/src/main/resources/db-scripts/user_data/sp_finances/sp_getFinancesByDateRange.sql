CREATE PROCEDURE appSolo.GetFinancesByDateRange
    @startDate DATE = NULL,  -- Permitindo NULL para começar com uma data vazia
    @endDate DATE = NULL,
    @idUser INT
AS
BEGIN
    IF @startDate IS NULL AND @endDate IS NULL
    BEGIN
        -- Quando ambos @startDate e @endDate estiverem vazios, retornamos todas as finanças do usuário
        SELECT 
            idActivity,
            idUser,
            transactionType,
            movementType,
            moneyValue,
            activityDate,
            label,
            description
        FROM 
            appSolo.Finances
        WHERE 
            idUser = @idUser;
    END
    ELSE IF @startDate IS NOT NULL AND @endDate IS NULL
    BEGIN
        -- Quando apenas @endDate estiver vazio, retornamos as finanças a partir de @startDate até o presente momento
        SELECT 
            idActivity,
            idUser,
            transactionType,
            movementType,
            moneyValue,
            activityDate,
            label,
            description
        FROM 
            appSolo.Finances
        WHERE 
            activityDate >= @startDate
            AND idUser = @idUser;
    END
    ELSE
    BEGIN
        -- Quando ambos @startDate e @endDate forem fornecidos, retornamos as finanças dentro do intervalo
        SELECT 
            idActivity,
            idUser,
            transactionType,
            movementType,
            moneyValue,
            activityDate,
            label,
            description
        FROM 
            appSolo.Finances
        WHERE 
            activityDate BETWEEN @startDate AND @endDate
            AND idUser = @idUser;
    END
END;
