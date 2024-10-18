package com.lpm.XHRRentals.Controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lpm.XHRRentals.Models.Aluguel;
import com.lpm.XHRRentals.Models.Equipamento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;

@Controller
@RequestMapping("/alugueis")
public class AluguelController {

  @PersistenceUnit
  EntityManagerFactory factory;

  @GetMapping("/equipamento/{id}")
  public @ResponseBody List<Aluguel> alugueisEquipamento(@PathVariable int id) {
    EntityManager manager = factory.createEntityManager();
    Equipamento equipamento = manager.find(Equipamento.class, id);
    TypedQuery<Aluguel> query = manager.createQuery(
        "Select A from Aluguel A where A.equipamento = :equip", 
        Aluguel.class).setParameter("equip", equipamento);
    return query.getResultList();
  }
}
