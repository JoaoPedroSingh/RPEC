Descrição do Projeto

Este trabalho tem como objetivo implementar e analisar o desempenho de diferentes tabelas hash em Java, utilizando três funções hash distintas e três tamanhos variados para o vetor da tabela. Além disso, realizamos uma análise detalhada do desempenho por meio de gráficos e tabelas geradas a partir dos dados coletados, para comparar inserção, colisões e buscas.

Implementação

Tabela hash com encadeamento separado para tratamento de colisões.

Três tamanhos para o vetor da tabela hash:
1000, 10000 e 100000.

Três funções hash testadas:

Divisão: resto da divisão do código pelo tamanho da tabela.

Multiplicação: multiplicação por constante fracionária, uso da parte fracionária.

Dobramento: soma e dobra dos dígitos do código para gerar índice.

Dados gerados aleatoriamente com seed para garantir repetibilidade.

Conjuntos de Dados

Volumes testados:

1.000.000 (1 milhão) registros

5.000.000 (5 milhões) registros

20.000.000 (20 milhões) registros

Metodologia de Teste

Para cada combinação (tamanho, função hash, volume), medimos:

Tempo de inserção

Número de colisões

Tempo de busca

Número de comparações na busca

Foram realizadas pelo menos 5 buscas por rodada.
Resultados exportados no arquivo resultados.csv.

Análise dos Resultados

1) Tempo Médio de Inserção
   
A função Multiplicação demonstrou o melhor desempenho geral, com tempos de inserção consistentemente menores em todas as configurações.

A função Divisão, apesar de simples, apresentou tempos maiores, provavelmente devido ao maior número de colisões.

A função Dobramento mostrou o pior desempenho, refletindo seu maior número de colisões e sobrecarga.

2) Número Médio de Colisões na Inserção
   
Usando escala logarítmica para melhor visualização, a função Divisão gera muito mais colisões que as outras.

As funções Multiplicação e Dobramento apresentam colisões em níveis similares, mas a multiplicação se destaca pela eficiência.

O aumento do tamanho da tabela reduz significativamente as colisões.

3) Tempo Médio de Busca
   
A função Divisão apresentou os menores tempos médios de busca, mesmo com mais colisões, sugerindo uma boa distribuição para buscas rápidas.

A função Multiplicação manteve tempos estáveis e baixos, reforçando sua eficiência.

A função Dobramento apresentou tempos maiores, influenciada por mais colisões.

Conclusão

A função hash de Multiplicação foi a que melhor equilibrou desempenho e número de colisões, sendo recomendada para aplicações que exigem rapidez na inserção e busca.

A função Divisão é simples e eficiente na busca, mas sofre com colisões que impactam inserções.

A função Dobramento teve desempenho inferior devido a mais colisões.

O tamanho da tabela é crucial para o desempenho, pois tabelas maiores apresentam menos colisões e melhor performance.

A escolha da função hash e do tamanho da tabela deve considerar o volume e as características dos dados para otimização.
