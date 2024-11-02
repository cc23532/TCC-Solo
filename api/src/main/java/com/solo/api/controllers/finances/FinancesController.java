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
import org.springframework.web.bind.annotation.RestController;

import com.solo.api.repositories.finances.FinancesRepository;
import com.solo.api.models.user.SoloUser;
import com.solo.api.DTO.finances.FinancesSumDTO;
import com.solo.api.models.finances.Finances;

import java.util.List;
import java.util.Date;

@RestController
@RequestMapping("/finances")
public class FinancesController {
    @Autowired
    FinancesRepository repo;

    //extrato de todas as transações 
        //entradas --> destacar em verde
        //saidas --> destacar em vermelho
    //Quando startDate ou endDate estiverem vazios (null), retornamos todas as finanças do usuário (allTime)
    //Quando apenas @endDate estiver vazio, retornamos as finanças a partir de @startDate até o presente momento
    //Quando ambos @startDate e @endDate forem fornecidos, retornamos as finanças dentro do intervalo
    @GetMapping("/extract/{idUser}")
    public ResponseEntity<List<Finances>> getExtractByPeriodAndUser(@PathVariable SoloUser idUser, @RequestBody Date startDate, @RequestBody Date endDate){
        List<Finances> extract = repo.getExtractByPeriodAndUser(startDate, endDate, idUser);

        return extract.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(extract);
    }

    // detalhes de transação a partir do extrato -> transaction details
    @GetMapping("/extract/{idUser}/transaction-details/{idActivity}")
    public ResponseEntity<Finances> getTransactionById(@PathVariable SoloUser idUser, @PathVariable Integer idActivity){
        return repo.findById(idActivity)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

    }

    // update em transação a partir do extrato -> transaction details -> botãao de editar dados
    @PutMapping("/extract/{idUser}/transaction-details/{idActivity}")
    public ResponseEntity<Finances> updateTransaction(@PathVariable SoloUser idUser, @PathVariable Integer idActivity, @RequestBody Finances transaction){
        return repo.findById(idActivity)
            .map(__transaction -> {
                __transaction.setIdUser(transaction.getIdUser());
                __transaction.setActivityDate(transaction.getActivityDate());
                __transaction.setTransactionType(transaction.getTransactionType());
                __transaction.setMovementType(transaction.getMovementType());
                __transaction.setMoneyValue(transaction.getMoneyValue());
                __transaction.setLabel(transaction.getLabel());
                __transaction.setDescription(transaction.getDescription()); 
                return ResponseEntity.ok(repo.save(__transaction));
            }) .orElseGet(() -> {
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
    public ResponseEntity<?> addNewTransaction(@PathVariable SoloUser idUser, @RequestBody String transactionType, @RequestBody String movementType, @RequestBody Double moneyValue, @RequestBody Date activityDate, @RequestBody String label, @RequestBody String description){
        Finances transaction = new Finances();
        transaction.setIdUser(idUser);
        
        //limitar front-end para receber apenas esses dois valores (income ou expense)
        if(transactionType == "income" || transactionType == "expense"){
            transaction.setTransactionType(transactionType); // 'income' OU 'expense' --> entrada ou saída    
        } else {
            return new ResponseEntity<>("Só serão aceitos dados que correspondam a 'income' ou 'expense'." , HttpStatus.BAD_REQUEST);
        }

        //limitar front-end para receber apenas esses dois valores (fixed ou variable)

        if(movementType == "fixed" || movementType == "variable"){
            transaction.setMovementType(movementType); // 'fixed' OU 'variable' --> movimentação fixa ou variável
        } else {
            return new ResponseEntity<>("Só serão aceitos dados que correspondam a 'fixed' ou 'variable'." , HttpStatus.BAD_REQUEST);
        }

        transaction.setMoneyValue(moneyValue);
        transaction.setActivityDate(activityDate);
        transaction.setLabel(label); // devem ser limitados no front-end também
        transaction.setDescription(description);

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    // soma de todas as entradas e saídas de acordo com o período de tempo definido
    //para o time frame, definir como 'last7days', 'last30days', 'lastYear' ou 'allTime'
    @GetMapping("/sum-by-time-frame/{idUser}")
    public ResponseEntity<FinancesSumDTO> getSumByTimeFrame(@PathVariable Integer idUser, @RequestBody String timeFrame){
        FinancesSumDTO results = repo.getSumByTimeFrame(timeFrame, idUser);

        if(results == null){
            return ResponseEntity.status(HttpStatus.CREATED).body(new FinancesSumDTO(idUser, 0.0, 0.0));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(results);
    }
}
