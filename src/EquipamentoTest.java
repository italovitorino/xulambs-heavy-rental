import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipamentoTest {
  Equipamento equipamento;
  
  @BeforeEach
  void setUp() {
    equipamento = new Equipamento("Betoneira de 400L", 50, 7);
  }

  @Test
  void equipamentoDeveEstarDisponivel() {
    assertTrue(equipamento.estaDisponivelEm(LocalDate.now()));
  }

  @Test
  void equipamentoDeveEstarIndisponivel() {
    equipamento.alugar(LocalDate.now(), 5);
    assertFalse(equipamento.estaDisponivelEm(LocalDate.now()));
  }

  @Test
  void naoDeveDeixarAlugarPorqueQtdDeDiasEMenorQue1() {
    assertFalse(equipamento.alugar(LocalDate.now(), 0));
  }

  @Test
  void naoDeveDeixarAlugarPorqueQuantidadeDeDiasEMaiorQueOMaximoPermitido() {
    assertFalse(equipamento.alugar(LocalDate.now(), 8));
  }
}
