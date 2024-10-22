package com.lpm.XHRRentals.Controllers;

import com.lpm.XHRRentals.DTO.AluguelDTO;
import com.lpm.XHRRentals.DTO.EquipamentoDTO;
import com.lpm.XHRRentals.Models.Aluguel;
import com.lpm.XHRRentals.Models.Equipamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @PersistenceUnit
    EntityManagerFactory factory;

    @PostMapping
    public @ResponseBody EquipamentoDTO cadastrarEquipamento(@RequestParam String descricao,
                                                             @RequestParam double diaria,
                                                             @RequestParam int duracao,
                                                             @RequestParam double desconto) {
        EntityManager manager = factory.createEntityManager();

        Equipamento novoEquipamento = new Equipamento(descricao, diaria, duracao, desconto);

        manager.getTransaction().begin();
        manager.persist(novoEquipamento);
        manager.getTransaction().commit();

        return novoEquipamento.gerarDTO();
    }

    @PutMapping("/alugar/{id}/{inicio}/{duracao}")
    public @ResponseBody AluguelDTO alugarEquipamento(@PathVariable int id,
                                                      @PathVariable LocalDate inicio,
                                                      @PathVariable int duracao) {
        EntityManager manager = factory.createEntityManager();
        Equipamento equipamento = manager.find(Equipamento.class, id);
        Aluguel aluguel = null;
        if (equipamento != null && equipamento.getFilial() != null) {
            aluguel = equipamento.alugar(inicio, duracao);
            manager.getTransaction().begin();
            manager.persist(aluguel);
            manager.persist(equipamento);
            manager.getTransaction().commit();
        }
        return aluguel != null ? aluguel.gerarDTO() : null;
    }

    @GetMapping("/{id}")
    public @ResponseBody EquipamentoDTO buscarEquipamento(@PathVariable int id) {
        EntityManager manager = factory.createEntityManager();
        Equipamento equipamento = manager.find(Equipamento.class, id);
        return equipamento != null ? equipamento.gerarDTO() : null;
    }

    @GetMapping("/relatorio/{id}")
    public @ResponseBody String relatorioEquipamento(@PathVariable int id) {
        EntityManager manager = factory.createEntityManager();
        Equipamento equipamento = manager.find(Equipamento.class, id);
        return equipamento.relatorioAlugueis();
    }

    @GetMapping("/arrecadacao/{id}")
    public @ResponseBody double totalArrecadado(@PathVariable int id) {
        EntityManager manager = factory.createEntityManager();
        Equipamento equipamento = manager.find(Equipamento.class, id);
        return equipamento.totalArrecadado();
    }
}
