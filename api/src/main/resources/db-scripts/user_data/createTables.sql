-- CREATE SCHEMA appSolo;

-- Área de Dados do Usuário 
CREATE TABLE appSolo.SoloUser
(
    id INT NOT NULL IDENTITY PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    birthday DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    pwd VARCHAR(255) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    weight FLOAT NOT NULL,
    height FLOAT NOT NULL,
    gender VARCHAR(10) NULL,
    profile_pic VARBINARY(MAX) NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE appSolo.UserHabits
(
    idUser INT NOT NULL,
    work BIT NOT NULL,
    workBegin TIME NULL,
    workEnd TIME NULL,
    study BIT NOT NULL,
    studyBegin TIME NULL,
    studyEnd TIME NULL,
    sleepBegin TIME NOT NULL,
    sleepEnd TIME NOT NULL,
    workout BIT NULL,
    workoutBegin TIME NULL,
    workoutEnd TIME NULL,
    smoke BIT NOT NULL,
    CONSTRAINT FK_UserHabits_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

CREATE TABLE appSolo.UserMood
(
    idUser INT NOT NULL,
    moodDate DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    mood VARCHAR(15) NOT NULL,
    CONSTRAINT FK_UserMood_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

-- Agenda
CREATE TABLE appSolo.Scheduling
(
    idSchedule INT NOT NULL IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    schedulingDate DATE NOT NULL,
    timeBegin TIME NOT NULL,
    timeEnd TIME NOT NULL,
    description TEXT NULL,
    idUser INT NOT NULL,
    CONSTRAINT FK_Scheduling_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

-- Treinamentos
CREATE TABLE appSolo.CardioActivity
(
    idActivity INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    activityDate DATETIME NOT NULL DEFAULT CAST(GETDATE() AS DATETIME),
    duration TIME NULL,
    distance FLOAT NULL,
    averageSpeed FLOAT NULL,
    elevationGain FLOAT NULL,
    lostKCal FLOAT NULL,
    CONSTRAINT FK_CardioActivity_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

CREATE TABLE appSolo.MuscleActivity
(
    idActivity INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    activityDate DATETIME NOT NULL DEFAULT CAST(GETDATE() AS DATETIME),
    duration TIME NULL,
    --category VARCHAR(50)  NULL,
    CONSTRAINT FK_MuscleActivity_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

CREATE TABLE appSolo.MuscleExercise
(
    idExercise INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT FK_MuscleExercises_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

CREATE TABLE appSolo.MuscleActivity_Exercises
(
    idMuscleActivity_Exercise INT NOT NULL IDENTITY PRIMARY KEY,
    idActivity INT NOT NULL,
    idExercise INT NOT NULL,
    name VARCHAR(50) NULL,
    weight FLOAT NOT NULL,
    series INT NOT NULL,
    repetition INT NOT NULL,
    CONSTRAINT FK_MuscleAcEx_Activity FOREIGN KEY (idActivity) REFERENCES appSolo.MuscleActivity(idActivity),
    CONSTRAINT FK_MuscleAcEx_Exercise FOREIGN KEY (idExercise) REFERENCES appSolo.MuscleExercise(idExercise)
);
-- Dieta
CREATE TABLE appSolo.Meal
(
    idMeal INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    mealDate DATE NOT NULL,
    mealTime TIME NOT NULL,
    CONSTRAINT FK_Meal_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

CREATE TABLE appSolo.Meal_Items
(
    idItem INT NOT NULL IDENTITY PRIMARY KEY,
    idMeal INT NOT NULL,
    idFood INT NOT NULL,
    weight FLOAT NOT NULL,
    CONSTRAINT FK_Meal_Items_Meal FOREIGN KEY (idMeal) REFERENCES appSolo.Meal(idMeal),
    CONSTRAINT FK_Meal_Items_FoodData FOREIGN KEY (idFood) REFERENCES appSolo.ibge_food_data(id)
);

-- Pare de Fumar
CREATE TABLE appSolo.StopSmoking
(
    idCount INT NOT NULL PRIMARY KEY,
    idUser INT NOT NULL,
    baseDate DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cigsPerDay INT NOT NULL,
    cigsPerPack INT NOT NULL,
    packPrice FLOAT NOT NULL,
    CONSTRAINT FK_StopSmoking_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);

-- Finanças 
CREATE TABLE appSolo.Finances
(
    idActivity INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    transactionType VARCHAR(50) NOT NULL, --entrada ou saída
    movementType VARCHAR(15) NOT NULL,  --fixa ou variável
    moneyValue FLOAT NOT NULL,
    activityDate DATE NOT NULL,
    label VARCHAR(50) NOT NULL, --alimentação, aluguel, seguro etc
    description TEXT NULL,
    CONSTRAINT FK_Finances_SoloUser FOREIGN KEY (idUser) REFERENCES appSolo.SoloUser(id)
);
