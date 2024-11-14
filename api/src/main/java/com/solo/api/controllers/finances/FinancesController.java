package com.solo.api.controllers.finances;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.repositories.finances.FinancesRepository;
import com.solo.api.repositories.user.SoloUserRepository;
import com.solo.api.models.user.SoloUser;
import com.solo.api.models.finances.Finances;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/finances")
public class FinancesController {
    @Autowired
    FinancesRepository repo;
    
    @Autowired
    SoloUserRepository userRepo;

    //extrato de todas as transações 
        //entradas --> destacar em verde
        //saidas --> destacar em vermelho
    //Quando startDate ou endDate estiverem vazios (null), retornamos todas as finanças do usuário (allTime)
    //Quando apenas @endDate estiver vazio, retornamos as finanças a partir de @startDate até o presente momento
    //Quando ambos @startDate e @endDate forem fornecidos, retornamos as finanças dentro do intervalo
    
    /* @GetMapping("/extract/{idUser}")
    public ResponseEntity<?> getExtractByPeriodAndUser(@PathVariable Integer idUser, @RequestBody Map<String, String> body) {
        String startDateStr = body.get("startDate");
        String endDateStr = body.get("endDate");
        
        try {
            // Definir startDate como uma data muito antiga se for null
            Date startDate = startDateStr != null ? 
                    new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr) : 
                    new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
            
            // Definir endDate como data atual se for null
            Date endDate = endDateStr != null ? 
                    new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr) : 
                    new Date();

            // Obter extrato pelo período e usuário
            List<Finances> extract = repo.getExtractByPeriodAndUser(startDate, endDate, idUser);

            return extract.isEmpty() 
                    ? ResponseEntity.noContent().build() 
                    : ResponseEntity.ok(extract);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro na conversão dos dados", HttpStatus.BAD_REQUEST);
        }
    } */ 

    @GetMapping("/extract/{idUser}")
    public ResponseEntity<?> getExtractByPeriodAndUser(@PathVariable Integer idUser,
                                                   @RequestParam(required = false) String startDateStr,
                                                   @RequestParam(required = false) String endDateStr) {
        try {
            // Definir startDate como uma data muito antiga se for null
            Date startDate = startDateStr != null ? 
                    new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr) : 
                    new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
            
            // Definir endDate como data atual se for null
            Date endDate = endDateStr != null ? 
                    new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr) : 
                    new Date();

            // Obter extrato pelo período e usuário
            List<Finances> extract = repo.getExtractByPeriodAndUser(startDate, endDate, idUser);

            return extract.isEmpty() 
                    ? ResponseEntity.noContent().build() 
                    : ResponseEntity.ok(extract);

        } catch (Exception e) {
            // Exceções mais específicas podem ser tratadas se necessário
            return new ResponseEntity<>("Erro na conversão dos dados. Verifique o formato das datas.", HttpStatus.BAD_REQUEST);
        }
    } 

    // detalhes de transação a partir do extrato -> transaction details
    @GetMapping("/extract/{idUser}/transaction-details/{idActivity}")
    public ResponseEntity<Finances> getTransactionById(@PathVariable SoloUser idUser, @PathVariable Integer idActivity){
        return repo.findById(idActivity)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/extract/{idUser}/transaction-details/{idActivity}")
public ResponseEntity<?> updateTransaction(@PathVariable Integer idUser, @PathVariable Integer idActivity, @RequestBody Finances transaction) {
    // Busca o usuário pelo idUser para evitar criar uma nova instância
    Optional<SoloUser> userOptional = userRepo.findById(idUser);
    
    if (userOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    }
    
    SoloUser user = userOptional.get();

    return repo.findById(idActivity)
        .map(existingTransaction -> {
            existingTransaction.setIdUser(user);  // Define o usuário existente
            existingTransaction.setActivityDate(transaction.getActivityDate());
            existingTransaction.setTransactionType(transaction.getTransactionType());
            existingTransaction.setMovementType(transaction.getMovementType());
            existingTransaction.setMoneyValue(transaction.getMoneyValue());
            existingTransaction.setLabel(transaction.getLabel());
            existingTransaction.setDescription(transaction.getDescription());
            
            return ResponseEntity.ok(repo.save(existingTransaction));
        })
        .orElseGet(() -> {
            transaction.setIdUser(user);  // Define o usuário para nova transação
            return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(transaction));
        });
}

    // deletar transação a partir do extrato -> transaction details -> botãao de excluir
    @DeleteMapping("/extract/{idUser}/transaction-details/{idActivity}")
    public ResponseEntity<?> deleteTransaction(@PathVariable SoloUser idUser, @PathVariable Integer idActivity){
        if (repo.existsById(idActivity)) {
            repo.deleteById(idActivity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }        
    }

    //adicionar nova transação
    @PostMapping("/add/{idUser}")
    public ResponseEntity<?> addNewTransaction(@PathVariable SoloUser idUser, @RequestBody Map<String, String> body){
        Finances transaction = new Finances();

        String transactionType = body.get("transactionType");
        String movementType = body.get("movementType");
        String moneyValueStr = body.get("moneyValue");
        String activityDateStr = body.get("activityDate");
        String label = body.get("label");
        String description = body.get("description");

        Date activityDate;
        Double moneyValue;

        transaction.setIdUser(idUser);
        
        //limitar front-end para receber apenas esses dois valores (income ou expense)
        transaction.setTransactionType(transactionType); // 'income' OU 'expense' --> entrada ou saída    

        //limitar front-end para receber apenas esses dois valores (fixed ou variable)
        transaction.setMovementType(movementType); // 'fixed' OU 'variable' --> movimentação fixa ou variável
        
        try {
            activityDate = new SimpleDateFormat("yyyy-MM-dd").parse(activityDateStr);
            moneyValue = Double.parseDouble(moneyValueStr);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro na conversão dos dados", HttpStatus.BAD_REQUEST);
        }

        transaction.setMoneyValue(moneyValue);
        transaction.setActivityDate(activityDate);
        transaction.setLabel(label); // devem ser limitados no front-end também
        transaction.setDescription(description);

        repo.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    // soma de todas as entradas e saídas de acordo com o período de tempo definido
    //para o time frame, definir como 'last7days', 'last30days', 'lastYear' ou 'allTime'
    @GetMapping("/sum-by-time-frame/{idUser}")
    public ResponseEntity<?> getSumByTimeFrame(@PathVariable Integer idUser, @RequestBody Map<String, String> body) {
        String timeFrame = body.get("timeFrame");
        
        List<Object[]> results = repo.getSumByTimeFrame(timeFrame, idUser);
        Map<String, Object> response = new HashMap<>();


        if (results.isEmpty()) {
            response.put("idUser", idUser);
            response.put("totalIncomes", 0.0);
            response.put("totalExpenses", 0.0);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        Object[] result = results.get(0);
        response.put("idUser", idUser);
        response.put("totalIncomes", result[0] != null ? result[0] : 0.0);
        response.put("totalExpenses", result[1] != null ? result[1] : 0.0);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
