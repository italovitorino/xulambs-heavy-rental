# Endpoints API - XHRRentals

### Filiais
```markdown
POST /filiais - Cadastrar uma nova filial.

PUT /filiais/{idFilial}/equipamentos/{idEquipamento} - Associar um equipamento a uma filial.

GET /filiais/{id}/maior-arrecadacao - Obter o equipamento com maior arrecadação na filial.

GET /filiais/{idFilial}/maior-arrecadacao/{e1}/{e2} - Comparar arrecadação entre dois equipamentos de uma filial.

GET /filiais/{id}/equipamentos/{descricao} - Buscar equipamento específico na filial.

GET /filiais/{id}/relatorio - Gerar relatório da filial.

GET /filiais/{id}/equipamentos - Listar todos os equipamentos da filial.
```

### Equipamentos

```markdown
POST /equipamentos - Cadastrar um novo equipamento.

PUT /equipamentos/alugar/{id}/{inicio}/{duracao} - Registrar aluguel.

GET /equipamentos/{id} - Buscar equipamento.

GET /equipamentos/relatorio/{id} - Gerar relatório do equipamento.

GET /equipamentos/arrecadacao/{id} - Total arrecadado com aluguéis do equipamento.
```

### Aluguéis

```markdown
GET /alugueis/equipamento/{id} - Gerar relatório de aluguéis de um equipamento.
```