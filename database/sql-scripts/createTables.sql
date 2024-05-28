-- CREATE SCHEMA solo;

-- Área de Dados do Usuário 
CREATE TABLE solo.SoloUser
(
    id INT NOT NULL IDENTITY PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL,
    password VARCHAR(20) NOT NULL,
    birthday DATE NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(12) NOT NULL,
    weight FLOAT NOT NULL,
    height FLOAT NOT NULL,
    profile_pic VARBINARY(MAX) NULL
);

CREATE TABLE solo.UserHabits
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
    -- smoke BIT NOT NULL,
    CONSTRAINT FK_UserHabits_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

CREATE TABLE solo.UserMood
(
    idUser INT NOT NULL,
    mood VARCHAR(10) NOT NULL,
    moodDate DATE NOT NULL,
    CONSTRAINT FK_UserMood_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

-- Agenda
CREATE TABLE solo.Scheduling
(
    idSchedule INT NOT NULL IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    schedulingDate DATE NOT NULL,
    timeBegin TIME NOT NULL,
    timeEnd TIME NOT NULL,
    description TEXT NULL,
    idUser INT NOT NULL,
    CONSTRAINT FK_Scheduling_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

-- Treinamentos
CREATE TABLE solo.CardioActivity
(
    idActivity INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    activityDate DATETIME NOT NULL,
    duration TIME NOT NULL,
    distance FLOAT NOT NULL,
    averageSpeed FLOAT NOT NULL,
    lostKCal FLOAT NOT NULL,
    CONSTRAINT FK_CardioActivity_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

CREATE TABLE solo.MuscleActivity
(
    idActivity INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    activityDate DATETIME NOT NULL,
    duration TIME NOT NULL,
    trainingType VARCHAR(50) NOT NULL,
    CONSTRAINT FK_MuscleActivity_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

CREATE TABLE solo.Muscle_Exercises
(
    idExercise INT NOT NULL IDENTITY PRIMARY KEY,
    idActivity INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    weight FLOAT NOT NULL,
    series INT NOT NULL,
    repetition INT NOT NULL,
    CONSTRAINT FK_Muscle_Exercises_MuscleActivity FOREIGN KEY (idActivity) REFERENCES solo.MuscleActivity(idActivity)
);

-- Dieta
CREATE TABLE solo.Meal
(
    idMeal INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    mealDate DATETIME NOT NULL,
    CONSTRAINT FK_Meal_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

CREATE TABLE solo.Meal_Items
(
    idItem INT NOT NULL IDENTITY PRIMARY KEY,
    idMeal INT NOT NULL,
    food VARCHAR(50) NOT NULL,
    weight FLOAT NOT NULL,
    CONSTRAINT FK_Meal_Items_Meal FOREIGN KEY (idMeal) REFERENCES solo.Meal(idMeal)
);

-- Pare de Fumar
CREATE TABLE solo.StopSmoking
(
    idUser INT NOT NULL PRIMARY KEY,
    cigsPerDay INT NOT NULL,
    cigsPerPack INT NOT NULL,
    packPrice FLOAT NOT NULL,
    CONSTRAINT FK_StopSmoking_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);

-- Finanças 
CREATE TABLE solo.FinancialActivity
(
    idActivity INT NOT NULL IDENTITY PRIMARY KEY,
    idUser INT NOT NULL,
    typeOf VARCHAR(50) NOT NULL,
    value MONEY NOT NULL,
    activityDate DATETIME NOT NULL,
    label VARCHAR(50) NOT NULL,
    description TEXT NULL,
    CONSTRAINT FK_FinancialActivity_SoloUser FOREIGN KEY (idUser) REFERENCES solo.SoloUser(id)
);
