CREATE OR ALTER VIEW appSolo.UserDetails AS 
SELECT 
    su.id idUser,
    su.nickname nickname, su.birthday birthday, su.email email, su.phone phone, su.weight weight, su.height height, su.profile_pic profile_pic, su.gender gender, 
    CASE 
        WHEN uh.work = 1 AND uh.workBegin IS NOT NULL AND uh.workEnd IS NOT NULL 
            THEN CONVERT(VARCHAR, uh.workBegin, 108) + ' - ' + CONVERT(VARCHAR, uh.workEnd, 108)
        ELSE '-' 
    END AS workTime,
    CASE 
        WHEN uh.study = 1 AND uh.studyBegin IS NOT NULL AND uh.studyEnd IS NOT NULL 
            THEN CONVERT(VARCHAR, uh.studyBegin, 108) + ' - ' + CONVERT(VARCHAR, uh.studyEnd, 108)
        ELSE '-' 
    END AS studyTime,
    CONVERT(VARCHAR, uh.sleepBegin, 108) + ' - ' + CONVERT(VARCHAR, uh.sleepEnd, 108) AS sleepTime,
    CASE 
        WHEN uh.workout = 1 AND uh.workoutBegin IS NOT NULL AND uh.workoutEnd IS NOT NULL 
            THEN CONVERT(VARCHAR, uh.workoutBegin, 108) + ' - ' + CONVERT(VARCHAR, uh.workoutEnd, 108)
        ELSE '-' 
    END AS workoutTime,
    CASE 
        WHEN uh.smoke = 1 THEN 'Smoker' ELSE 'Non-smoker' 
    END AS smokeStatus
FROM appSolo.SoloUser su
JOIN appSolo.UserHabits uh ON su.id = uh.idUser;