import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

sns.set(style="whitegrid")
plt.rcParams["font.size"] = 14

# Carregar o arquivo CSV com os dados
df = pd.read_csv("resultados.csv")

# Filtrar dados de inserção e busca
df_ins = df[df["Operacao"] == "INSERCAO"]
df_bus = df[df["Operacao"] == "BUSCA"]

def plotar_bar(data, x, y, hue, title, ylabel, filename, log_scale=False):
    plt.figure(figsize=(10, 6))
    ax = sns.barplot(data=data, x=x, y=y, hue=hue, ci=None)
    if log_scale:
        ax.set_yscale('log')
    plt.title(title)
    plt.ylabel(ylabel)
    plt.xlabel(x)
    plt.legend(title=hue)
    plt.tight_layout()
    plt.savefig(filename)
    plt.show()

# 1) Tempo médio de inserção (barras normais)
tempo_ins = df_ins.groupby(['TamanhoTabela', 'FuncaoHash'])['TempoMS'].mean().reset_index()
print("Tempo médio de inserção (ms):")
print(tempo_ins.pivot(index='TamanhoTabela', columns='FuncaoHash', values='TempoMS'))

plotar_bar(
    tempo_ins,
    x='TamanhoTabela', y='TempoMS', hue='FuncaoHash',
    title='Tempo Médio de Inserção por Função Hash e Tamanho da Tabela',
    ylabel='Tempo (ms)', filename='tempo_insercao.png',
    log_scale=False
)

# 2) Número médio de colisões na inserção (barras agrupadas com escala log)
colisoes_ins = df_ins.groupby(['TamanhoTabela', 'FuncaoHash'])['ColisoesOuAcertos'].mean().reset_index()
print("\nNúmero médio de colisões na inserção:")
print(colisoes_ins.pivot(index='TamanhoTabela', columns='FuncaoHash', values='ColisoesOuAcertos'))

plotar_bar(
    colisoes_ins,
    x='TamanhoTabela', y='ColisoesOuAcertos', hue='FuncaoHash',
    title='Número Médio de Colisões na Inserção (escala log, barras agrupadas)',
    ylabel='Colisões (escala log)', filename='colisoes_insercao_bar_log.png',
    log_scale=True
)

# 3) Tempo médio de busca (adicionando 0.1 para evitar zeros, escala log)
tempo_bus = df_bus.groupby(['TamanhoTabela', 'FuncaoHash'])['TempoMS'].mean().reset_index()
tempo_bus['TempoMS_ajustado'] = tempo_bus['TempoMS'] + 0.1  # adiciona 0.1 ms para visibilidade no log
print("\nTempo médio de busca (ms) - ajustado para visualização:")
print(tempo_bus.pivot(index='TamanhoTabela', columns='FuncaoHash', values='TempoMS_ajustado'))

plotar_bar(
    tempo_bus,
    x='TamanhoTabela', y='TempoMS_ajustado', hue='FuncaoHash',
    title='Tempo Médio de Busca por Função Hash e Tamanho da Tabela (escala log, +0.1ms)',
    ylabel='Tempo (ms) ajustado (+0.1)', filename='tempo_busca_log_ajustado.png',
    log_scale=True
)
