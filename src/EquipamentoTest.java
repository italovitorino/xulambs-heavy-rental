import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipamentoTest {
  static Equipamento equipamento;

  @BeforeEach
  public void setUp() {
    equipamento = new Equipamento("Betoneira de 400L", 50, 20, 5);
  }

  @Test
  public void construtorComParametrosValidos() {
    Equipamento equipamento = new Equipamento("Andaime", 100.0, 5, 5);
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Andaime"));
    assertTrue(relatEquipamento.contains("100,00"));
    assertTrue(relatEquipamento.contains("5,00%"));
  }

  @Test
  public void construtorComParametrosInvalidos() {
    Equipamento equipamento = new Equipamento("And", 0, -5, 12);
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Equipamento"));
    assertTrue(relatEquipamento.contains("10,00"));
    assertTrue(relatEquipamento.contains("0,00%"));
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
    assertNull(equipamento.alugar(LocalDate.now(), 0));
  }

  @Test
  public void naoDeveDeixarAlugarPorqueQtdDeDiasEMaiorQueOMaximoPermitido() {
    assertNull(equipamento.alugar(LocalDate.now(), 60));
  }

  @Test
  public void deveDeixarAlugar() {
    assertNotNull(equipamento.alugar(LocalDate.now(), 2));
  }

  @Test 
  void totalArrecadadoDeveSerIgualAZero() {
    assertEquals(0.0, equipamento.totalArrecadado(), 0.01);
  }

  @Test
  void totalArrecadadoNaoPodeSerIgualAZero() {
    equipamento.alugar(LocalDate.now(), 2);
    equipamento.alugar(LocalDate.of(2024, 12, 12), 8);
    assertEquals(480.0, equipamento.totalArrecadado(), 0.01);
  }

  @Test
  void naoDeveTerDescontoNaDiariaPorqueQtdEMenorQue7() {
    assertEquals(50.0, equipamento.valorDiario(6), 0.01);
    assertEquals(50.0, equipamento.valorDiario(7), 0.01);
  }

  @Test
  void deveDarDescontoNaDiariaPorqueQtdEMaiorQue7() {
    assertEquals(47.5, equipamento.valorDiario(8), 0.01);
  }

  @Test
  public void relatorioDoEquipamento() {
    equipamento.alugar(LocalDate.now(), 2);
    equipamento.alugar(LocalDate.now().plusDays(3), 2);
    String relatEquipamento = equipamento.dadosEquipamento();
    assertTrue(relatEquipamento.contains("Betoneira de 400L"));
    assertTrue(relatEquipamento.contains("50,00"));
    assertTrue(relatEquipamento.contains("5,00%"));
    assertTrue(relatEquipamento.contains("200"));
  }

  @Test
  public void relatorioDosAlugueis() {
    equipamento.alugar(LocalDate.now(), 2);
    equipamento.alugar(LocalDate.of(2024, 12, 12), 8);
    String relatEquipamento = equipamento.relatorioAlugueis();
    assertTrue(relatEquipamento.contains("Início: " + LocalDate.now()));
    assertTrue(relatEquipamento.contains("Término: " + LocalDate.now().plusDays(2)));
    assertTrue(relatEquipamento.contains("50,00"));
    assertTrue(relatEquipamento.contains("100,00"));
    assertTrue(relatEquipamento.contains("2024-12-12"));
    assertTrue(relatEquipamento.contains("2024-12-20"));
    assertTrue(relatEquipamento.contains("47,50"));
    assertTrue(relatEquipamento.contains("380,00"));
    assertTrue(relatEquipamento.contains("480,00"));
  }
}
