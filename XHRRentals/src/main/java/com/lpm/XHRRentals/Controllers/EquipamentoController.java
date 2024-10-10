package com.lpm.XHRRentals.Controllers;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lpm.XHRRentals.Models.Aluguel;
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

  @PutMapping("equipamentos/alugar/{id}/{inicio}/{duracao}")
  public @ResponseBody Aluguel alugarEquipamento(@PathVariable int id, 
                                                 @PathVariable LocalDate inicio,
                                                 @PathVariable int duracao) {
    EntityManager manager = factory.createEntityManager();
    Equipamento equipamento = manager.find(Equipamento.class, id);
    Aluguel aluguel = null;
    if (equipamento != null) {
      aluguel = equipamento.alugar(inicio, duracao);
      manager.getTransaction().begin();
      manager.persist(aluguel);
      manager.persist(equipamento);
      manager.getTransaction().commit();
    }
    return aluguel;
  }

  @GetMapping("/equipamentos/{id}")
  public @ResponseBody Equipamento buscarEquipamento(@PathVariable int id) {
    EntityManager manager = factory.createEntityManager();
    Equipamento equipamento = manager.find(Equipamento.class, id);
    return equipamento;
  }

  @GetMapping("/equipamentos/relatorio/{id}")
  public @ResponseBody String relatorioEquipamento(@PathVariable int id) {
    EntityManager manager = factory.createEntityManager();
    Equipamento equipamento = manager.find(Equipamento.class, id);
    return equipamento.relatorioAlugueis();
  }

  @GetMapping("/equipamentos/arrecadacao/{id}")
  public @ResponseBody double totalArrecadado(@PathVariable int id) {
    EntityManager manager = factory.createEntityManager();
    Equipamento equipamento = manager.find(Equipamento.class, id);
    return equipamento.totalArrecadado();
  }
}
