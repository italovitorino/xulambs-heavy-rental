package com.lpm.XHRRentals.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lpm.XHRRentals.Models.Equipamento;
import com.lpm.XHRRentals.Models.Filial;
import com.lpm.XHRRentals.DTO.FilialDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@Controller
public class FilialController {
  
  @PersistenceUnit
  EntityManagerFactory factory;

  @PostMapping("/filiais")
  public @ResponseBody FilialDTO cadastrarFilial(@RequestParam String nome) {
    EntityManager manager = factory.createEntityManager();
    Filial filial = new Filial(nome);

    manager.getTransaction().begin();;
    manager.persist(filial);
    manager.getTransaction().commit();

    return filial.gerarDTO();
  }

  @PutMapping("filiais/{idFilial}/equipamentos/{idEquipamento}")
  public @ResponseBody int associarEquipamento(@PathVariable Long idFilial, @PathVariable Long idEquipamento) {
    EntityManager manager = factory.createEntityManager();
    Filial filial = manager.find(Filial.class, idFilial);
    Equipamento equipamento = manager.find(Equipamento.class, idEquipamento);

    int totEquipamentos = 0;

    if (filial != null && equipamento != null) {
      totEquipamentos = filial.cadastrarEquipamento(equipamento);
      equipamento.setFilial(filial);
      manager.getTransaction().begin();
      manager.persist(equipamento);
      manager.getTransaction().commit();
    }

    return totEquipamentos;
  }

  @GetMapping("filiais/{id}/maior-arrecadacao")
  public @ResponseBody Equipamento maiorArrecadacao(@PathVariable Long id) {
    EntityManager manager = factory.createEntityManager();
    Filial filial = manager.find(Filial.class, id);
    Equipamento equipamento = null;

    if (filial != null) {
      equipamento = filial.maiorArrecadacao();
    }

    return equipamento;
  }
}
