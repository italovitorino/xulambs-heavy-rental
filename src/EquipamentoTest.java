import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipamentoTest {
  static Equipamento equipamento;

  @BeforeEach
  public void setUp() {
    equipamento = new Equipamento("Betoneira de 400L", 50, 7);
  }

  @Test
  public void construtorComParametrosValidos() {
    Equipamento equipamento = new Equipamento("Andaime", 100.0, 5);
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Andaime"));
    assertTrue(relatEquipamento.contains("100,00"));
  }

  @Test
  public void construtorComParametrosInvalidos() {
    Equipamento equipamento = new Equipamento("And", 0, -5);
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Equipamento"));
    assertTrue(relatEquipamento.contains("10,00"));
  }

  @Test
  public void equipamentoDeveEstarDisponivel() {
    assertTrue(equipamento.estaDisponivelEm(LocalDate.now()));
  }

  @Test
  public void equipamentoDeveEstarIndisponivel() {
    equipamento.alugar(LocalDate.now(), 5);
    assertFalse(equipamento.estaDisponivelEm(LocalDate.now()));
  }

  @Test
  public void naoDeveDeixarAlugarPorqueQtdDeDiasEMenorQue1() {
    assertFalse(equipamento.alugar(LocalDate.now(), 0));
  }

  @Test
  public void naoDeveDeixarAlugarPorqueQtdDeDiasEMaiorQueOMaximoPermitido() {
    assertFalse(equipamento.alugar(LocalDate.now(), 8));
  }

  @Test
  public void valorTotalDoAluguel() {
    equipamento.alugar(LocalDate.now(), 5);
    assertEquals(250, equipamento.valorAluguel(), 0.01);
  }

  @Test
  public void relatorioDoEquipamentoQueJaFoiAlugado() {
    equipamento.alugar(LocalDate.now(), 2);
    equipamento.alugar(LocalDate.now().plusDays(3), 2);
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Betoneira de 400L"));
    assertTrue(relatEquipamento.contains("50,00"));
    assertTrue(relatEquipamento.contains(LocalDate.now().plusDays(3) + " - " + LocalDate.now().plusDays(5)));
    assertTrue(relatEquipamento.contains("200"));
  }

  @Test
  public void relatorioDoEquipamentoQueNuncaFoiAlugado() {
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Betoneira de 400L"));
    assertTrue(relatEquipamento.contains("50,00"));
    assertTrue(relatEquipamento.contains("Sem registro!"));
    assertTrue(relatEquipamento.contains("0,00"));
  }
}
