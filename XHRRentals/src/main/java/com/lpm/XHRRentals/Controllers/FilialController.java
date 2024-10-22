package com.lpm.XHRRentals.Controllers;

import com.lpm.XHRRentals.DTO.EquipamentoDTO;
import com.lpm.XHRRentals.DTO.FilialDTO;
import com.lpm.XHRRentals.Models.Equipamento;
import com.lpm.XHRRentals.Models.Filial;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/filiais")
public class FilialController {

    @PersistenceUnit
    EntityManagerFactory factory;

    @PostMapping
    public @ResponseBody FilialDTO cadastrarFilial(@RequestParam String nome) {
        EntityManager manager = factory.createEntityManager();
        Filial filial = new Filial(nome);

        manager.getTransaction().begin();
        ;
        manager.persist(filial);
        manager.getTransaction().commit();

        return filial.gerarDTO();
    }

    @PutMapping("/{idFilial}/equipamentos/{idEquipamento}")
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

    @GetMapping("/{id}/maior-arrecadacao")
    public @ResponseBody EquipamentoDTO maiorArrecadacao(@PathVariable Long id) {
        EntityManager manager = factory.createEntityManager();
        Filial filial = manager.find(Filial.class, id);
        Equipamento equipamento = null;

        if (filial != null) {
            equipamento = filial.maiorArrecadacao();
        }

        return equipamento != null ? equipamento.gerarDTO() : null;
    }

    @GetMapping("/{idFilial}/maior-arrecadacao/{e1}/{e2}")
    public @ResponseBody EquipamentoDTO maiorArrecadacao(@PathVariable Long idFilial,
                                                         @PathVariable String e1,
                                                         @PathVariable String e2) {
        EntityManager manager = factory.createEntityManager();
        Filial filial = manager.find(Filial.class, idFilial);
        Equipamento maior = null;

        if (filial != null) {
            maior = filial.maiorArrecadacao(e1, e2);
        }

        return maior != null ? maior.gerarDTO() : null;
    }

    @GetMapping("/{id}/relatorio")
    public @ResponseBody String relatorio(@PathVariable Long id) {
        EntityManager manager = factory.createEntityManager();
        Filial filial = manager.find(Filial.class, id);
        String relatorio = "";
        if (filial != null) {
            relatorio = filial.relatorioFilial();
        }

        return relatorio;
    }

    @GetMapping("/{id}/equipamentos")
    public @ResponseBody List<EquipamentoDTO> listarEquipamentos(@PathVariable Long id) {
        EntityManager manager = factory.createEntityManager();
        List<Equipamento> equipamentos = manager.createQuery(
                "SELECT e FROM Equipamento e WHERE e.filial.id = :idFilial",
                Equipamento.class).setParameter("idFilial", id).getResultList();
        return equipamentos.stream().map(Equipamento::gerarDTO).toList();
    }
}
