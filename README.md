Descrição do Projeto
Este trabalho tem como objetivo implementar e analisar o desempenho de diferentes tabelas hash em Java, utilizando três funções hash distintas e três tamanhos variados para o vetor da tabela. Além disso, é feita uma análise detalhada do desempenho por meio de gráficos e tabelas geradas a partir dos dados coletados, para comparar inserção, colisões e buscas.

Implementação
A tabela hash foi implementada utilizando encadeamento separado para tratamento de colisões.

Três tamanhos diferentes para o vetor da tabela hash foram escolhidos: 1000, 10000 e 100000.

Três funções hash foram implementadas e testadas:

Divisão: Hash por resto da divisão do código pelo tamanho da tabela.

Multiplicação: Hash usando a multiplicação por uma constante fracionária e extração da parte fracionária.

Dobramento: Dobramento dos dígitos do código para gerar o índice da tabela.

Os dados testados são conjuntos de registros com códigos numéricos de 9 dígitos, gerados aleatoriamente com seed para garantir a repetibilidade e validação.

Conjuntos de Dados
Foram utilizados três volumes diferentes para avaliação:

1.000.000 (1 milhão) de registros.

5.000.000 (5 milhões) de registros.

20.000.000 (20 milhões) de registros.

Essa variação permite analisar o comportamento da tabela hash em diferentes escalas de dados.

Metodologia de Teste
Para cada combinação de tamanho da tabela, função hash e conjunto de dados, foram medidas:

O tempo de inserção dos elementos na tabela.

O número de colisões ocorridas durante a inserção.

O tempo de busca dos elementos.

O número de comparações feitas durante as buscas.

Para cada busca, foram realizadas pelo menos 5 consultas para assegurar a consistência dos tempos.

Os resultados foram exportados para o arquivo resultados.csv.

Análise dos Resultados
O script Python analisar_resultados.py gera tabelas e gráficos para facilitar a interpretação dos dados. As principais análises apresentadas são:

1) Tempo Médio de Inserção
O gráfico de barras mostra que a função Multiplicação tem um desempenho consistente e rápido para inserção.

A função Divisão também apresenta bom desempenho, ligeiramente mais lenta que Multiplicação em alguns casos.

A função Dobramento é a que apresenta maior tempo médio de inserção, principalmente para os maiores tamanhos de dados.

2) Número Médio de Colisões na Inserção
Usando gráfico de barras agrupadas em escala logarítmica para melhor visualização, foi possível notar:

A função Divisão gera muito mais colisões que as outras, especialmente em tabelas menores.

Multiplicação e Dobramento têm número de colisões bem menores, sendo a multiplicação geralmente a mais eficiente.

3) Tempo Médio de Busca
A busca é muito rápida em geral, porém:

A função Divisão apresenta o menor tempo médio de busca, ainda que as colisões sejam altas.

Multiplicação mantém tempos médios baixos e estáveis.

Dobramento tem tempos de busca maiores, consequência do maior número de colisões.

Quem Foi Melhor e Por Quê?
Multiplicação mostrou ser a função hash mais balanceada, entregando bom desempenho tanto em inserção quanto em busca, com número reduzido de colisões.

Divisão teve o pior desempenho em termos de colisões, mas como o encadeamento trata bem as colisões, o tempo de busca não ficou muito comprometido.

Dobramento apresentou pior desempenho em tempo, refletindo seu maior número de colisões e buscas mais lentas.

O tamanho da tabela influencia diretamente o número de colisões: tabelas maiores resultam em menos colisões e tempos melhores.
