package com.solo.api.controllers.stopSmoking;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.DTO.stopSmoking.StopSmokingSummaryDTO;
import com.solo.api.models.stopSmoking.StopSmoking;
import com.solo.api.models.user.SoloUser;
import com.solo.api.repositories.stopSmoking.StopSmokingRepository;

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

    @PostMapping("/{idUser}")
    public ResponseEntity<StopSmoking> startCounting(@PathVariable SoloUser idUser, @RequestBody int cigsPerDay, @RequestBody int cigsPerPack, @RequestBody Double packPrice) {
        StopSmoking newCount = new StopSmoking();
        newCount.setSoloUser(idUser);
        newCount.setCigsPerDay(cigsPerDay);
        newCount.setCigsPerPack(cigsPerPack);
        newCount.setPackPrice(packPrice);        
        repo.save(newCount);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCount);
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<Optional<StopSmokingSummaryDTO>> getSummary(@PathVariable SoloUser idUser) {
        Optional<StopSmokingSummaryDTO> summary = repo.getSummaryByUser(idUser);
        return summary.isPresent()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(summary);
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
    public ResponseEntity<StopSmoking> updateSmokingData(@PathVariable SoloUser idUser, @PathVariable Integer idCount, @RequestBody StopSmoking count){
        return repo.findById(idCount)
        .map(__count -> {
            __count.setCigsPerDay(count.getCigsPerDay());
            __count.setCigsPerPack(count.getCigsPerPack());
            __count.setPackPrice(count.getPackPrice());
            return ResponseEntity.ok(repo.save(__count));
        })
        .orElseGet(() -> {
            return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(count));
        });
    }
    
}
