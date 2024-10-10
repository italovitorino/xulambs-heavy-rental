package com.lpm.XHRRentals.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lpm.XHRRentals.Models.Equipamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@Controller
public class EquipamentoController {

  @PersistenceUnit
  EntityManagerFactory factory;
  
  @PostMapping("/equipamentos")
  public @ResponseBody Equipamento cadastrarEquipamento(@RequestParam String descricao,
                                     @RequestParam double diaria,
                                     @RequestParam int duracao,
                                     @RequestParam double desconto) {
    EntityManager manager = factory.createEntityManager();

    Equipamento novoEquipamento = new Equipamento(descricao, diaria, duracao, desconto);

    manager.getTransaction().begin();
    manager.persist(novoEquipamento);
    manager.getTransaction().commit();
    
    return novoEquipamento;
  }
}
