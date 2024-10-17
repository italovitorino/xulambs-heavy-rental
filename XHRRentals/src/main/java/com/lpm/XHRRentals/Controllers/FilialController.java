package com.lpm.XHRRentals.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lpm.XHRRentals.Models.Filial;
import com.lpm.XHRRentals.DTO.FilialDTO;
import com.lpm.XHRRentals.Models.Equipamento;

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
}
