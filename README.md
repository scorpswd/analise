# Leitura e Geração de Arquivos Flat

**analise** é um interpretador e processador de arquivos flat. Powered by Java.

  - Despeje seus arquivos na pasta de entrada
  - Contemple o resultado na pasta de saída
  - Mágica

# Características

  - Lê arquivos com dados de vendedores, clientes e vendas
  - Processa estes arquivos de maneira paralelizada
  - Calcula o número de clientes e de vendedores, além de identificador a maior venda e o vendedor com a performance mais aquém do esperado
  - Produz um arquivo de retorno que apresenta as informações supracitadas


Você ainda pode:
  - É só isso, mesmo.

### Tecnologia

**analise** é uma realidade apenas graças ao suporte de um número de ferramentas reconhecidas pela comunidade de desenvolvimento:

* [Java]: Presente neste e em quatro bilhões de dispositivos
* [Apache Commons]: Qualidade de vida no trabalho
* [Flatworm]: Leitura e geração de arquivos flat do jeito fácil
* [Log4J2]: Porque debug custa muito tempo
* [Maven]: Como se gerenciava dependências antes disso?

### Instalação e Compilação

**analise** depende do Maven para a obtenção de dependências e consequente compilação do projeto, através de um comando Maven -> Install.

### Execução - Requisitos

 - Dispor de alguns arquivos de entrada
 - Conhecer o destino da variável de ambiente %HOMEPATH%

# Desafio

O desafio recebido propõe que se faça a leitura de uma quantidade indeterminada de arquivos carregados com dados de vendedores, clientes e vendas; apresentados em formato proprietário, com extensão **.DAT** (e somente **.DAT**), e designados a uma etapa de processamento através da qual serão calculados:

* o número de clientes presentes no arquivo
* o número de vendedores presentes no arquivo
* a identificação da maior venda realizada
* o nome do pior vendedor

O enunciado demanda que, para cada arquivo lido, seja produzido um respectivo arquivo de resposta, contemplando os quatro atributos calculados acima. É esperado que os arquivos de entrada/leitura estejam exclusivamente contidos em **%HOMEPATH%/data/in**, ao passo que os arquivos de saída/resposta serão escritos, exclusivamente, em **%HOMEPATH%/data/out**.

A composição dos nomes para arquivos de saída/resposta usará como base o respectivo nome de sua contrapartida de entrada/leitura, incorporando a extensão intermediária **.DONE** antes da extensão final **.DAT**. Como exemplo, suponhamos que exista um arquivo de entrada/leitura chamado **{%HOMEPATH%/data/in/}arqDados.DAT**. Após seu processamento, o respectivo arquivo de saída/resposta será escrito com o nome de **{%HOMEPATH%/data/out/}arqDados.DONE.DAT**.

Como solicitação final, a solução deve se apresentar como um serviço de alta disponibilidade, capaz de ler e processar arquivos adicionados ao caminho de entrada (**%HOMEPATH%/data/in**) em uma janela de tempo não especificada, quando quer que novos arquivos sejam criados ou colados no caminho de entrada.

### O Arquivo de Entrada
É composto por três tipos distintos de registro, identificados pelos três algarismos iniciais de cada linha, de acordo com o que define a tabela que segue:

 Nome do Registro | Identificador
-------------------|----------------
Vendedor          |           001
Cliente           |           002
Venda             |           003

Cada campo apresentado nas linhas do arquivo de entrada é delimitado por uma cedilha, salvo o último campo de cada linha, delimitado pelo separador de linha do sistema operacional. Como exemplo, suponhamos que a linha corrente em processo de leitura se apresente da seguinte forma:

<pre>001<b>ç</b>1234567891234<b>ç</b>Anderson<b>ç</b>50000000</pre>

Após separarmos os valores de acordo com as posições das cedilhas, identificamos os campos em uma base individual, como descreve a tabela:

 Campo 1 | Campo 2       | Campo 3  | Campo 4
---------|---------------|----------|----------
001      |1234567891234  |Anderson  | 50000000

### O Registro 001 - Vendedor
Se apresenta como no exemplo que segue:

<pre>001<b>ç</b>1234567891234<b>ç</b>Anderson<b>ç</b>50000000</pre>

 Sequência | Nome                     | Tipo    | Valor Fixo
------------|--------------------------|---------|-------------
Campo 1    |Identificador do Registro |Numérico | 001        
Campo 2    |CPF do Vendedor           |Numérico |            
Campo 3    |Nome do Vendedor          |Literal  |            
Campo 4    |Salário do Vendedor       |Monetário|            

### O Registro 002 - Cliente
Se apresenta como no exemplo que segue:

<pre>002<b>ç</b>2345675434544345<b>ç</b>Sprinter<b>ç</b>Rural</pre>

 Sequência | Nome                     | Tipo    | Valor Fixo
------------|--------------------------|---------|-------------
Campo 1    |Identificador do Registro |Numérico | 002        
Campo 2    |CNPJ do Cliente           |Numérico |            
Campo 3    |Nome do Cliente           |Literal  |            
Campo 4    |Área de Atuação do Cliente|Literal  |            

### O Registro 003 - Venda
Se apresenta como no exemplo que segue:

<pre>003<b>ç</b>10<b>ç</b>[1-10-100,2-30-2.50,3-40-3.10]<b>ç</b>Anderson</pre>

 Sequência | Nome                     | Tipo    | Valor Fixo
------------|--------------------------|---------|-------------
Campo 1    |Identificador do Registro |Numérico | 003        
Campo 2    |Identificador da Venda    |Numérico |            
Campo 3    |Detalhamento da Venda     |Literal  |            
Campo 4    |Nome do Vendedor          |Literal  |            

### O Arquivo de Entrada - um Exemplo:

<pre>
001<b>ç</b>1234567891234<b>ç</b>Anderson<b>ç</b>50000000
001<b>ç</b>3245678865434<b>ç</b>Almondegas<b>ç</b>980.50
002<b>ç</b>2345675434544345<b>ç</b>Sprinter<b>ç</b>Rural
002<b>ç</b>2345675433444345<b>ç</b>Tyson<b>ç</b>Rural
002<b>ç</b>2345675433444355<b>ç</b>BichoGomez<b>ç</b>Rural
003<b>ç</b>10<b>ç</b>[1-10-100,2-30-2.50,3-40-3.10]<b>ç</b>Anderson
003<b>ç</b>08<b>ç</b>[1-34-10,2-33-1.50,3-40-0.10]<b>ç</b>Anderson
003<b>ç</b>11<b>ç</b>[1-1-10,2-1-1.50,3-1-0.10]<b>ç</b>Almondegas
003<b>ç</b>14<b>ç</b>[1-1-120,2-15-1.50,3-80-0.10]<b>ç</b>Almondegas
</pre>

# Solução

A linha principal de execução do projeto inicia verificando a existência dos caminhos de leitura e escrita, criando-os de acordo com a necessidade. A classe utilitária analise.path.PathUtils provê a solução para este e outros problemas cuja resposta se fundamenta na API NIO.

No passo seguinte, o sistema lista todos os arquivos **.DAT** pertencentes ao caminho de entrada e os encaminha para possível processamento.

Um ServiceWatcher também é registrado no caminho de entrada para monitorar o advento de novos arquivos na referida pasta APÓS o início da execução da ferramenta. Desta forma, quando quer que um novo arquivo seja percebido no caminho de entrada, o mesmo será encaminhado para processamento em caráter prioritário.

Um pool fixo de trinta linhas paralelas de execução aguarda os arquivos iminentes na etapa de processamento, não obstante a maneira como a ferramenta os detecta.

A primeira etapa do processamento consiste em verificar se há, de fato, a necessidade de processar o arquivo corrente. Para constatação, é verificado se o arquivo de entrada já possui seu respectivo arquivo de saída. Caso positivo, o arquivo de entrada é tratado como processado, desconsiderado e a linha paralela de execução, liberada para processar um próximo arquivo.
