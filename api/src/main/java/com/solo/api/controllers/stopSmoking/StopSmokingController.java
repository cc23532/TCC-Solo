package com.solo.api.controllers.stopSmoking;

import java.util.Optional;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.models.stopSmoking.StopSmoking;
import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.stopSmoking.StopSmokingRepository;
import com.solo.api.repositories.user.SoloUserRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/stop-smoking")
public class StopSmokingController {
    @Autowired
    StopSmokingRepository repo;

    @Autowired
    SoloUserRepository userRepo;

    @PostMapping("/{idUser}")
    public ResponseEntity<?> startCounting(@PathVariable SoloUser idUser, @RequestBody Map<String, String> body){
        StopSmoking newCount = new StopSmoking();

        String cigsPerDayStr = body.get("cigsPerDay");
        String cigsPerPackStr = body.get("cigsPerPack");
        String packPriceStr = body.get("packPrice");
        
        int cigsPerDay;
        int cigsPerPack;
        Double packPrice;
            
        try {
            cigsPerDay = Integer.parseInt(cigsPerDayStr); // Converte a string para int
            cigsPerPack = Integer.parseInt(cigsPerPackStr); // Converte a string para int
            packPrice = Double.parseDouble(packPriceStr); // Converte a string para Double
        } catch (NumberFormatException e) {
            // Trate a exceção se a conversão falhar
            return new ResponseEntity<>("Erro ao converter os valores: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Trate outras exceções, se necessário
            return new ResponseEntity<>("Erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        newCount.setIdUser(idUser);
        newCount.setCigsPerDay(cigsPerDay);
        newCount.setCigsPerPack(cigsPerPack);
        newCount.setPackPrice(packPrice);        
        newCount.setBaseDate(OffsetDateTime.now());        
        repo.save(newCount);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCount);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<?> getSummary(@PathVariable Integer idUser) {
        Optional<List<Object[]>> summaries = repo.getSummaryByUser(idUser);
        Map<String, Object> response = new HashMap<>();
        
        if (summaries.isEmpty() || summaries.get().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
    
        // Log para depuração
        List<Object[]> dataList = summaries.get();
        Object[] data = dataList.get(0); // Pegue a primeira linha de resultados
    
        if (data.length < 7) {
            return ResponseEntity.badRequest().body("Dados incompletos.");
        }
    
        response.put("idCount", data[0]);
        response.put("idUser", data[1]);
        response.put("baseDate", data[2]);
        response.put("daysWithoutSmoking", data[3]);
        response.put("avoidedCigarettes", data[4]);
        response.put("moneySaved", data[5]);
        response.put("lifeMinutesSaved", data[6]);
    
        return ResponseEntity.ok(response);
    }
    

    @DeleteMapping("/{idUser}/{idCount}")
    public ResponseEntity<?> deleteCount(@PathVariable SoloUser idUser, @PathVariable Integer idCount){
        if (repo.existsById(idCount)) {
            repo.deleteById(idCount);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }        
    }

    @PutMapping("/{idUser}/{idCount}")
    public ResponseEntity<?> updateSmokingData(@PathVariable Integer idUser, @PathVariable Integer idCount, @RequestBody StopSmoking count){
        // Busca o usuário pelo idUser para evitar criar uma nova instância
        Optional<SoloUser> userOptional = userRepo.findById(idUser);
        
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        
        SoloUser user = userOptional.get();

        return repo.findById(idCount)
        .map(__count -> {
            __count.setIdUser(user);
            __count.setCigsPerDay(count.getCigsPerDay());
            __count.setCigsPerPack(count.getCigsPerPack());
            __count.setPackPrice(count.getPackPrice());
            __count.setBaseDate(count.getBaseDate());
            return ResponseEntity.ok(repo.save(__count));
        })
        .orElseGet(() -> {
            count.setIdUser(user);
            count.setBaseDate(OffsetDateTime.now()); // Definir baseDate ao criar nova contagem
            return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(count));
        });
    }
    
}
