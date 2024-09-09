package com.solo.api.controllers.user;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import com.solo.api.services.SoloUserService;
import com.solo.api.services.UserHabitsService;

@RestController
@RequestMapping("/register")
public class RegisterApiController {
    @Autowired
    SoloUserService userService;

    @Autowired
    UserHabitsService habitsService;

    @GetMapping("/test")
    public String testEndPoint(){
        return "SOLO: Cadastro de Usuário no ar";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody Map<String, String> body) {
        String nickname = body.get("nickname");
        String birthdayStr = body.get("birthday");
        String email = body.get("email");
        String pwd = body.get("pwd");
        String phone = body.get("phone");
        String weightStr = body.get("weight");
        String heightStr = body.get("height");
        String gender = body.get("gender");
    
        Date birthday;
        double weight;
        double height;
        try {
            birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr);
            weight = Double.parseDouble(weightStr);
            height = Double.parseDouble(heightStr);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro na conversão dos dados", HttpStatus.BAD_REQUEST);
        }
    
        if (nickname.isEmpty() || birthday == null || email.isEmpty() || pwd.isEmpty() || phone.isEmpty() || weight <= 0.0 || height <= 0.0 || gender.isEmpty()) {
            return new ResponseEntity<>("Por favor preencha todos os campos solicitados", HttpStatus.BAD_REQUEST);
        }
    
        String hashPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
    
        // Chamada ao serviço
        int idUser = userService.registerNewUser(nickname, birthday, email, hashPwd, phone, weight, height, gender);
    
        if (idUser <= 0) {
            return new ResponseEntity<>("Falha ao cadastrar novo usuário...", HttpStatus.BAD_REQUEST);
        }
    
        // Retorna o ID do usuário recém-cadastrado
        return new ResponseEntity<>(Collections.singletonMap("idUser", idUser), HttpStatus.OK);
    }

    @PostMapping("/habits/{idUser}")
    public ResponseEntity<?> registerHabits(@PathVariable Integer idUser, @RequestBody Map<String, String> body) {
        try {
            boolean work = Boolean.parseBoolean(body.get("work"));
            Time workBegin = (body.get("workBegin") != null && !body.get("workBegin").isEmpty()) ? Time.valueOf(body.get("workBegin") + ":00") : null;
            Time workEnd = (body.get("workEnd") != null && !body.get("workEnd").isEmpty()) ? Time.valueOf(body.get("workEnd") + ":00") : null;
    
            boolean study = Boolean.parseBoolean(body.get("study"));
            Time studyBegin = (body.get("studyBegin") != null && !body.get("studyBegin").isEmpty()) ? Time.valueOf(body.get("studyBegin") + ":00") : null;
            Time studyEnd = (body.get("studyEnd") != null && !body.get("studyEnd").isEmpty()) ? Time.valueOf(body.get("studyEnd") + ":00") : null;
    
            Time sleepBegin = Time.valueOf(body.get("sleepBegin") + ":00");
            Time sleepEnd = Time.valueOf(body.get("sleepEnd") + ":00");
    
            Boolean workout = (body.get("workout") != null && !body.get("workout").isEmpty()) ? Boolean.parseBoolean(body.get("workout")) : null;
            Time workoutBegin = (body.get("workoutBegin") != null && !body.get("workoutBegin").isEmpty()) ? Time.valueOf(body.get("workoutBegin") + ":00") : null;
            Time workoutEnd = (body.get("workoutEnd") != null && !body.get("workoutEnd").isEmpty()) ? Time.valueOf(body.get("workoutEnd") + ":00") : null;
    
            boolean smoke = Boolean.parseBoolean(body.get("smoke"));
    
            // Chama o serviço para registrar os hábitos
            int result = habitsService.registerUserHabits(idUser, work, workBegin, workEnd, study, studyBegin, studyEnd, sleepBegin, sleepEnd, workout, workoutBegin, workoutEnd, smoke);
    
            if (result != 1) {
                return new ResponseEntity<>("Falha ao registrar hábitos. Resultado da atualização: " + result, HttpStatus.BAD_REQUEST);
            }
    
            return new ResponseEntity<>("Hábitos registrados com sucesso", HttpStatus.OK);
    
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao processar a requisição: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    
}
