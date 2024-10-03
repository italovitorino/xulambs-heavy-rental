package com.lpm.XHRRentals.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.lpm.XHRRentals.Models.Equipamento;

@Controller
public class EquipamentoController {
  
  @PostMapping("/equipamentos")
  public String cadastrarEquipamento(@RequestParam String descr,
                                     @RequestParam double diaria,
                                     @RequestParam int duracao,
                                     @RequestParam double desconto) {
    Equipamento novoEquipamento = new Equipamento(descr, diaria, duracao, desconto);
    
    return entity;
  }
}
