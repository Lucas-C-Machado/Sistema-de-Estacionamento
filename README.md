# 🚗 Sistema de Estacionamento

Sistema web desenvolvido em **Java** para gerenciamento de veículos em um estacionamento.

O projeto foi construído com finalidade educacional no curso **Técnico em Desenvolvimento de Sistemas**, no contexto educacional do **Senac**, integrando conceitos de **Programação Orientada a Objetos**, desenvolvimento web com **Spring Boot e Thymeleaf**, persistência de dados com **JDBC** e banco de dados **MySQL**.

A aplicação permite realizar as quatro operações fundamentais de um CRUD de veículos:

- **CREATE** — cadastrar veículos;
- **READ** — consultar veículos cadastrados;
- **UPDATE** — editar e atualizar veículos;
- **DELETE** — excluir veículos.

---

# 📌 Sobre o projeto

O Sistema de Estacionamento evoluiu de uma aplicação Java orientada a objetos para uma aplicação web integrada a banco de dados.

Atualmente, o fluxo principal da aplicação pode ser representado da seguinte forma:

```text
Navegador
    ↓
HTML + Thymeleaf
    ↓
HTTP
    ↓
Spring Boot
    ↓
Controller
    ↓
Objetos Java
    ↓
DAO
    ↓
JDBC
    ↓
MySQL
```

Na leitura dos dados, o fluxo ocorre também no sentido inverso:

```text
MySQL
    ↓
ResultSet
    ↓
DAO
    ↓
Objetos Java
    ↓
Controller
    ↓
Model
    ↓
Thymeleaf
    ↓
HTML
    ↓
Navegador
```

Essa arquitetura permite visualizar de maneira clara as responsabilidades de cada parte da aplicação e compreender como os dados percorrem o sistema.

---

# 🛠️ Tecnologias utilizadas

O projeto utiliza:

- **Java**
- **Programação Orientada a Objetos**
- **Spring Boot**
- **Spring MVC**
- **Thymeleaf**
- **Maven**
- **JDBC**
- **MySQL**
- **HTML5**
- **CSS3**
- **Git**
- **GitHub**

## Ambiente atual do projeto

- Java configurado no Maven: **Java 17**
- Spring Boot: **3.5.7**
- Banco de dados: **MySQL**
- Servidor web: **Tomcat embarcado pelo Spring Boot**
- Porta padrão da aplicação: **8080**
- Porta padrão do MySQL: **3306**

---

# ✨ Funcionalidades

## 🚙 Cadastro de veículos — CREATE

O sistema permite cadastrar diferentes tipos de veículos.

Atualmente são suportados:

### Carro

Possui os dados comuns de um veículo e:

- quantidade de portas.

### Moto

Possui os dados comuns de um veículo e:

- cilindradas.

O cadastro é realizado pelo navegador e enviado ao servidor por meio de uma requisição HTTP `POST`.

Fluxo simplificado:

```text
Formulário HTML
    ↓
POST
    ↓
Controller
    ↓
Carro ou Moto
    ↓
DAO
    ↓
PreparedStatement
    ↓
INSERT
    ↓
MySQL
```

---

## 📋 Listagem de veículos — READ

Os veículos cadastrados podem ser consultados diretamente pelo navegador.

A aplicação realiza uma consulta ao banco de dados:

```sql
SELECT *
FROM veiculo
ORDER BY id;
```

Os registros retornados pelo MySQL são recuperados através de um `ResultSet` e transformados novamente em objetos Java.

Fluxo:

```text
GET /veiculos
    ↓
Controller
    ↓
DAO
    ↓
SELECT
    ↓
ResultSet
    ↓
Carro / Moto
    ↓
List<Veiculo>
    ↓
Model
    ↓
Thymeleaf
    ↓
Tabela HTML
```

---

## 🔎 Busca por ID

Para operações que envolvem um veículo específico, o sistema utiliza seu identificador técnico:

```text
id
```

O DAO possui uma operação de busca individual que utiliza conceitualmente:

```sql
SELECT *
FROM veiculo
WHERE id = ?;
```

Essa busca é utilizada, por exemplo, antes da edição de um veículo.

A diferença principal é:

```text
listar()
    ↓
retorna vários veículos
    ↓
List<Veiculo>


buscarPorId(id)
    ↓
retorna um veículo específico
    ↓
Veiculo
```

---

## ✏️ Edição de veículos — UPDATE

Cada veículo possui um identificador único (`id`).

Na listagem, o usuário pode selecionar um veículo para edição.

Exemplo de rota:

```text
/veiculos/editar/2
```

O número `2` representa o ID do registro.

O sistema:

1. recebe o ID pela URL;
2. busca o veículo correspondente no banco;
3. reconstrói o objeto Java;
4. envia o objeto ao `Model`;
5. disponibiliza o objeto para o Thymeleaf;
6. apresenta o formulário preenchido;
7. recebe os dados alterados;
8. executa o `UPDATE` no banco.

Fluxo:

```text
Lista de veículos
    ↓
Editar
    ↓
GET /veiculos/editar/{id}
    ↓
@PathVariable
    ↓
buscarPorId(id)
    ↓
MySQL
    ↓
Objeto Java
    ↓
Model
    ↓
editar.html
    ↓
POST /veiculos/atualizar
    ↓
Controller
    ↓
DAO
    ↓
UPDATE
    ↓
MySQL
```

A atualização utiliza o `id` para determinar qual registro deverá ser alterado:

```sql
UPDATE veiculo
SET ...
WHERE id = ?;
```

---

## 🗑️ Exclusão de veículos — DELETE

Também é possível excluir um veículo cadastrado.

A exclusão utiliza o `id` como identificador técnico do registro.

Fluxo:

```text
Lista de veículos
    ↓
Excluir
    ↓
POST /veiculos/excluir/{id}
    ↓
Controller
    ↓
@PathVariable
    ↓
DAO
    ↓
DELETE
    ↓
MySQL
    ↓
redirect:/veiculos
```

A operação utiliza conceitualmente:

```sql
DELETE FROM veiculo
WHERE id = ?;
```

Antes do envio da solicitação de exclusão, a interface pode solicitar uma confirmação do usuário para evitar exclusões acidentais.

---

# 🔄 CRUD implementado

O projeto possui atualmente as quatro operações fundamentais de persistência:

| Operação | HTTP | SQL | Finalidade |
|---|---|---|---|
| **CREATE** | POST | `INSERT` | Cadastrar veículo |
| **READ** | GET | `SELECT` | Consultar veículos |
| **UPDATE** | POST | `UPDATE` | Atualizar veículo |
| **DELETE** | POST | `DELETE` | Excluir veículo |

> O CRUD não se resume aos comandos SQL. Cada operação percorre diferentes camadas da aplicação, desde o navegador até o banco de dados.

---

# 🧱 Organização da aplicação

O projeto separa diferentes responsabilidades para facilitar a compreensão, manutenção e evolução do sistema.

---

## Model

Representa os objetos e regras do domínio da aplicação.

Principais classes:

```text
Veiculo
├── Carro
└── Moto
```

`Veiculo` representa características comuns aos veículos.

`Carro` e `Moto` especializam esse comportamento utilizando conceitos de **herança e polimorfismo**.

Entre os conceitos de Programação Orientada a Objetos trabalhados no projeto estão:

- classes;
- objetos;
- atributos;
- métodos;
- construtores;
- encapsulamento;
- herança;
- polimorfismo;
- abstração;
- interfaces;
- sobrescrita de métodos.

---

## Controller

O Controller é responsável por receber e coordenar as requisições HTTP da aplicação.

Fluxo conceitual:

```text
Navegador
    ↓
Controller
    ↓
Objetos / DAO
    ↓
Model
    ↓
View
```

São utilizadas anotações do Spring MVC como:

```java
@Controller
@GetMapping
@PostMapping
@RequestParam
@PathVariable
```

---

## DAO

O padrão **DAO (Data Access Object)** concentra as operações relacionadas ao acesso ao banco de dados.

O `VeiculoDAO` é responsável por operações como:

```text
inserir()
listar()
buscarPorId()
atualizar()
excluir()
```

A comunicação com o MySQL é realizada utilizando JDBC.

---

## JDBC

A aplicação utiliza JDBC diretamente para tornar explícito o processo de comunicação entre Java e banco de dados.

Entre os principais componentes utilizados estão:

```java
Connection
PreparedStatement
ResultSet
```

Essa abordagem permite acompanhar o caminho percorrido pelos dados sem abstrair a persistência através de um ORM.

As operações utilizam:

```text
executeQuery()
```

para consultas que retornam um `ResultSet`, como `SELECT`, e:

```text
executeUpdate()
```

para operações como:

```text
INSERT
UPDATE
DELETE
```

---

## Thymeleaf

O Thymeleaf é utilizado como mecanismo de templates para integrar dados Java às páginas HTML.

Entre os recursos utilizados estão:

```text
th:href
th:action
th:text
th:value
th:each
th:if
```

Exemplo de listagem:

```html
<tr th:each="veiculo : ${veiculos}">
    <td th:text="${veiculo.id}"></td>
    <td th:text="${veiculo.placa}"></td>
    <td th:text="${veiculo.modelo}"></td>
</tr>
```

---

# 🌐 HTTP e rotas

A aplicação utiliza requisições HTTP para comunicação entre navegador e servidor.

De maneira simplificada:

```text
GET
→ solicitar/obter recursos e representações

POST
→ enviar dados ao servidor para processamento
   e realizar operações que podem modificar
   o estado da aplicação
```

O projeto evita utilizar `GET` para operações destrutivas, como exclusão de registros.

---

# 🌐 Principais rotas

Entre as rotas utilizadas pela aplicação estão:

| Método | Rota | Finalidade |
|---|---|---|
| GET | `/` | Página inicial |
| GET | `/veiculos` | Listar veículos |
| GET | `/veiculos/cadastro` | Exibir formulário de cadastro |
| POST | `/veiculos/cadastrar` | Cadastrar veículo |
| GET | `/veiculos/editar/{id}` | Exibir formulário de edição |
| POST | `/veiculos/atualizar` | Atualizar veículo |
| POST | `/veiculos/excluir/{id}` | Excluir veículo |

Exemplo:

```text
GET /veiculos/editar/4
```

solicita o formulário de edição do veículo cujo ID é `4`.

---

# 🔁 Redirecionamento

Depois de operações como cadastro, atualização ou exclusão, a aplicação pode utilizar:

```java
return "redirect:/veiculos";
```

Isso não significa abrir diretamente o arquivo `veiculos.html`.

O fluxo é:

```text
Operação concluída
    ↓
redirect:/veiculos
    ↓
Nova requisição HTTP
    ↓
GET /veiculos
    ↓
Controller
    ↓
dao.listar()
    ↓
MySQL
    ↓
Model
    ↓
veiculos.html
```

Dessa forma, a listagem é carregada novamente com os dados atualizados.

---

# 🗄️ Banco de dados

O projeto utiliza um banco MySQL chamado:

```text
estacionamento
```

A tabela principal é:

```text
veiculo
```

Estrutura utilizada pelo projeto:

```sql
CREATE DATABASE IF NOT EXISTS estacionamento;

USE estacionamento;

CREATE TABLE IF NOT EXISTS veiculo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    valor_hora DECIMAL(10,2) NOT NULL,
    horas_estacionado INT NOT NULL DEFAULT 0,
    estacionado BOOLEAN NOT NULL DEFAULT FALSE,
    tipo VARCHAR(20) NOT NULL,
    quantidade_portas INT NULL,
    cilindradas INT NULL
);
```

---

# 🔑 Identificação dos veículos

Cada registro possui:

```text
id
```

como chave primária:

```sql
PRIMARY KEY
```

com geração automática:

```sql
AUTO_INCREMENT
```

O `id` é utilizado como **identificador técnico e estável do registro**, principalmente nas operações de busca, edição, atualização e exclusão.

A placa possui:

```sql
UNIQUE
```

porque representa uma informação de negócio que não deve ser duplicada.

Assim, o projeto diferencia:

```text
id
→ identificador técnico do registro

placa
→ informação de negócio do veículo
```

Essa distinção permite, por exemplo, alterar uma placa incorreta sem perder a identificação técnica do registro.

---

# 🚘 Carro e Moto no banco de dados

Carros e motos compartilham informações comuns, mas também possuem dados específicos.

Para um carro:

```text
tipo = CARRO
quantidade_portas = valor
cilindradas = NULL
```

Para uma moto:

```text
tipo = MOTO
quantidade_portas = NULL
cilindradas = valor
```

Durante a leitura do banco, o DAO identifica o tipo e reconstrói o objeto Java correspondente:

```text
tipo = CARRO
    ↓
new Carro(...)

tipo = MOTO
    ↓
new Moto(...)
```

---

# 📁 Estrutura geral do projeto

A organização principal segue aproximadamente esta estrutura:

```text
SistemaEstacionamento/
│
├── banco/
│   └── estacionamento.sql
│
├── src/
│   └── main/
│       │
│       ├── java/
│       │   └── br/com/senac/estacionamento/
│       │       │
│       │       ├── conexao/
│       │       │   └── Conexao.java
│       │       │
│       │       ├── controller/
│       │       │   └── VeiculoController.java
│       │       │
│       │       ├── dao/
│       │       │   └── VeiculoDAO.java
│       │       │
│       │       ├── model/
│       │       │   ├── Veiculo.java
│       │       │   ├── Carro.java
│       │       │   └── Moto.java
│       │       │
│       │       └── SistemaEstacionamentoApplication.java
│       │
│       └── resources/
│           │
│           ├── static/
│           │   └── css/
│           │
│           ├── templates/
│           │   ├── index.html
│           │   ├── cadastro.html
│           │   ├── veiculos.html
│           │   └── editar.html
│           │
│           └── application.properties
│
├── pom.xml
├── LICENSE
└── README.md
```

> A estrutura pode sofrer pequenas alterações conforme a evolução didática do projeto.

---

# ⚙️ Preparação do ambiente

Antes de executar o projeto, verifique se estão instalados:

- Java;
- Maven;
- MySQL Server;
- MySQL Workbench ou outro cliente MySQL;
- VS Code, IntelliJ IDEA, NetBeans ou outra IDE compatível;
- Git, caso queira trabalhar com versionamento.

---

# 🚀 Como executar o projeto

## 1. Clone o repositório

```bash
git clone URL_DO_REPOSITORIO
```

Entre na pasta:

```bash
cd SistemaEstacionamento
```

---

## 2. Prepare o banco de dados

Abra:

```text
banco/estacionamento.sql
```

no MySQL Workbench e execute o script.

Ou crie o banco e a tabela utilizando a estrutura SQL apresentada neste README.

Depois, confira:

```sql
USE estacionamento;

SELECT * FROM veiculo;
```

---

## 3. Configure a conexão com o MySQL

Abra:

```text
src/main/java/br/com/senac/estacionamento/conexao/Conexao.java
```

Configure os dados de acordo com seu ambiente local.

Exemplo conceitual:

```text
Servidor: localhost
Porta: 3306
Banco: estacionamento
Usuário: root
Senha: sua senha local
```

> ⚠️ **Importante:** não publique senhas reais, credenciais pessoais ou outros dados sensíveis no GitHub.

Se o projeto utilizar credenciais diretamente em arquivos Java durante a etapa educacional, confira cuidadosamente o conteúdo antes de realizar `commit` e `push`.

---

## 4. Instale as dependências

O projeto utiliza Maven.

Ao abrir o projeto na IDE, aguarde o Maven resolver as dependências definidas no:

```text
pom.xml
```

Também é possível executar:

```bash
mvn clean install
```

---

## 5. Execute a aplicação

A aplicação pode ser iniciada pelo terminal com:

```bash
mvn spring-boot:run
```

Ou executando a classe principal:

```text
SistemaEstacionamentoApplication.java
```

A aplicação utiliza:

```java
@SpringBootApplication
```

e inicia o Spring Boot por meio de:

```java
SpringApplication.run(...)
```

---

## 6. Acesse pelo navegador

Com a aplicação em execução, acesse:

```text
http://localhost:8080
```

Para visualizar diretamente os veículos cadastrados:

```text
http://localhost:8080/veiculos
```

---

# 🧠 Conceitos trabalhados

O projeto integra diferentes conteúdos de desenvolvimento de sistemas.

## Programação Orientada a Objetos

- classes;
- objetos;
- atributos;
- métodos;
- construtores;
- encapsulamento;
- herança;
- polimorfismo;
- abstração;
- interfaces;
- sobrescrita de métodos.

## Desenvolvimento Web

- cliente e servidor;
- requisição e resposta;
- HTTP;
- GET;
- POST;
- rotas;
- Controller;
- formulários HTML;
- templates;
- redirecionamento.

## Banco de Dados

- MySQL;
- banco de dados relacional;
- tabelas;
- chave primária;
- `AUTO_INCREMENT`;
- restrição `UNIQUE`;
- `NULL`;
- `INSERT`;
- `SELECT`;
- `UPDATE`;
- `DELETE`.

## JDBC

- conexão com banco;
- `Connection`;
- `PreparedStatement`;
- parâmetros SQL;
- `ResultSet`;
- `executeQuery()`;
- `executeUpdate()`.

## Spring MVC

- `@Controller`;
- `@GetMapping`;
- `@PostMapping`;
- `@RequestParam`;
- `@PathVariable`;
- `Model`;
- rotas;
- integração entre Controller e View.

## Thymeleaf

- `th:href`;
- `th:action`;
- `th:text`;
- `th:value`;
- `th:each`;
- `th:if`;
- integração entre objetos Java e HTML.

## Versionamento

- Git;
- commits;
- histórico de alterações;
- GitHub;
- documentação do projeto.

---

# 🔁 Exemplo de fluxo completo — UPDATE

Uma operação de edição percorre várias partes da aplicação:

```text
Usuário
    ↓
veiculos.html
    ↓
GET /veiculos/editar/2
    ↓
VeiculoController
    ↓
@PathVariable
    ↓
dao.buscarPorId(2)
    ↓
PreparedStatement
    ↓
SELECT ... WHERE id = 2
    ↓
MySQL
    ↓
ResultSet
    ↓
Carro ou Moto
    ↓
Controller
    ↓
Model
    ↓
editar.html
    ↓
Usuário altera os dados
    ↓
POST /veiculos/atualizar
    ↓
Controller
    ↓
DAO
    ↓
UPDATE ... WHERE id = 2
    ↓
MySQL
    ↓
redirect:/veiculos
    ↓
nova requisição GET
    ↓
listagem atualizada
```

---

# 🔁 Exemplo de fluxo completo — DELETE

Uma exclusão percorre:

```text
Usuário
    ↓
veiculos.html
    ↓
Excluir
    ↓
Confirmação
    ↓
POST /veiculos/excluir/2
    ↓
VeiculoController
    ↓
@PathVariable
    ↓
dao.excluir(2)
    ↓
PreparedStatement
    ↓
DELETE FROM veiculo
WHERE id = 2
    ↓
MySQL
    ↓
redirect:/veiculos
    ↓
GET /veiculos
    ↓
listagem atualizada
```

---

# 🧪 Testando o CRUD

Para validar a aplicação, pode ser realizado o seguinte fluxo.

## CREATE

Cadastre um novo `Carro` ou `Moto`.

Confira se o veículo aparece em:

```text
/veiculos
```

Depois confira no MySQL:

```sql
SELECT * FROM veiculo;
```

---

## READ

Verifique se os dados apresentados no navegador correspondem aos dados armazenados no banco.

Compare:

```text
Navegador
↕
MySQL
```

---

## UPDATE

Clique em **Editar**.

Altere algum dado permitido e salve.

Confira novamente:

```text
Navegador
↓
MySQL
```

O `id` do registro deverá permanecer o mesmo.

---

## DELETE

Clique em **Excluir**.

Confirme a operação.

Verifique se o registro desapareceu da listagem.

Depois confira novamente:

```sql
SELECT * FROM veiculo;
```

---

# 🐞 Depuração e aprendizado

Durante o desenvolvimento, erros são utilizados como parte do processo de aprendizagem.

Alguns pontos importantes para investigação são:

```text
Navegador
    ↓
Rota
    ↓
Controller
    ↓
Objeto Java
    ↓
DAO
    ↓
PreparedStatement
    ↓
SQL
    ↓
MySQL
```

E, nas consultas:

```text
MySQL
    ↓
ResultSet
    ↓
Objeto Java
    ↓
Controller
    ↓
Model
    ↓
Thymeleaf
    ↓
Navegador
```

Ao encontrar um problema, a proposta é identificar **em qual ponto do fluxo o dado deixou de possuir o valor esperado**.

---

# 🎯 Finalidade educacional

Este projeto possui finalidade educacional.

Sua implementação prioriza a compreensão explícita do funcionamento de uma aplicação Java integrada a banco de dados.

Por esse motivo, tecnologias como JDBC, `PreparedStatement`, `ResultSet` e DAO são utilizadas diretamente, permitindo visualizar as diferentes etapas do fluxo de dados.

O objetivo não é apenas construir um CRUD funcional, mas compreender como:

```text
Interface
+
HTTP
+
Java
+
Programação Orientada a Objetos
+
Spring Boot
+
Thymeleaf
+
JDBC
+
SQL
+
MySQL
```

se conectam para formar uma aplicação web.

---

# 🔮 Possíveis evoluções

O projeto pode continuar evoluindo conforme novos conteúdos forem estudados.

Algumas possibilidades futuras incluem:

- melhoria das validações;
- tratamento amigável de erros;
- mensagens de sucesso e erro para o usuário;
- aprimoramento da interface;
- controle de entrada e saída dos veículos;
- cálculo do valor do estacionamento;
- histórico de permanência;
- filtros e pesquisas;
- testes automatizados;
- melhorias de segurança;
- evolução da arquitetura conforme novos conteúdos forem trabalhados.

> Essas funcionalidades **não representam necessariamente recursos já implementados**. São possibilidades para etapas futuras do projeto.

---

# 📖 Status do projeto

**Em desenvolvimento — versão educacional.**

## Implementado

- [x] Modelagem orientada a objetos
- [x] Classes `Veiculo`, `Carro` e `Moto`
- [x] Herança
- [x] Polimorfismo
- [x] Integração Java + MySQL
- [x] JDBC
- [x] DAO
- [x] `PreparedStatement`
- [x] `ResultSet`
- [x] Spring Boot
- [x] Spring MVC
- [x] Thymeleaf
- [x] Interface web
- [x] Cadastro de veículos
- [x] Listagem de veículos
- [x] Identificação por ID
- [x] Busca por ID
- [x] Formulário de edição
- [x] Atualização de veículos
- [x] Exclusão de veículos
- [x] CRUD completo
- [x] Versionamento com Git
- [x] Documentação do projeto
- [x] Licença educacional não comercial

---

# 👨‍💻 Autoria

**Lucas Machado**

Projeto desenvolvido no contexto educacional do **Senac**, como parte de atividades relacionadas ao desenvolvimento e à aprendizagem de sistemas.

A referência ao Senac indica o contexto educacional do projeto e não implica, por si só, endosso, certificação ou aprovação institucional de versões modificadas ou redistribuídas por terceiros.

---

# 📄 Licença

Este projeto é disponibilizado sob uma **Licença Educacional Não Comercial**.

A licença foi definida para permitir o estudo, utilização, modificação e compartilhamento do projeto em contextos educacionais e não comerciais, preservando sua finalidade de aprendizagem.

## ✅ O que é permitido

Nos termos estabelecidos no arquivo [`LICENSE`](LICENSE), é permitido:

- utilizar o projeto para fins educacionais;
- estudar e analisar o código-fonte;
- executar o projeto para aprendizagem;
- modificar e adaptar o código;
- utilizar partes do código em exercícios e atividades educacionais;
- criar versões derivadas;
- redistribuir o projeto original;
- redistribuir versões modificadas.

Essas permissões estão condicionadas ao cumprimento dos termos completos da licença.

---

## 📌 Condições para redistribuição

Ao redistribuir o projeto original ou uma versão modificada, devem ser observadas, entre outras, as seguintes condições:

- preservar os avisos de autoria;
- manter a licença junto ao projeto;
- manter a utilização e redistribuição para fins não comerciais;
- informar quando alterações significativas tiverem sido realizadas;
- disponibilizar versões derivadas redistribuídas sob os mesmos termos;
- não atribuir ao autor original modificações realizadas por terceiros;
- não sugerir aprovação ou endosso do autor ou do Senac sem autorização.

---

## 🚫 Uso comercial

A licença deste projeto **não concede autorização para exploração comercial**.

Isso inclui, nos termos definidos no arquivo `LICENSE`, restrições à venda, sublicenciamento comercial e exploração econômica do projeto, de suas versões derivadas ou de partes substanciais de seu código.

Eventuais permissões para utilização comercial dependem de autorização específica e separada dos respectivos titulares dos direitos aplicáveis.

---

## 🔄 Versões modificadas

É permitido criar versões modificadas do projeto para fins educacionais e não comerciais.

Caso essas versões sejam redistribuídas, deverão:

1. preservar a atribuição ao projeto original;
2. indicar que foram modificadas;
3. manter esta licença;
4. permanecer sob os mesmos termos de utilização não comercial;
5. não atribuir ao autor original responsabilidade pelas modificações realizadas por terceiros.

Uma atribuição ao projeto original pode ser apresentada da seguinte maneira:

> Baseado no projeto **Sistema de Estacionamento**, desenvolvido por **Lucas Machado** no contexto educacional do **Senac**.

---

## 🏫 Referência ao Senac

Este projeto foi desenvolvido no contexto educacional do **Senac**.

A referência à instituição não concede automaticamente direitos para utilização de:

- marcas;
- logotipos;
- identidade visual;
- sinais distintivos;
- outros ativos protegidos pertencentes ao Senac.

A redistribuição ou modificação deste projeto também não implica endosso, certificação, parceria ou aprovação institucional pelo Senac.

---

## 📦 Componentes de terceiros

O projeto utiliza tecnologias, bibliotecas, frameworks e componentes de terceiros.

Esses componentes permanecem sujeitos às suas **respectivas licenças**.

Isso inclui, conforme as dependências efetivamente utilizadas pelo projeto, tecnologias como:

- Java;
- Spring Boot;
- Spring MVC;
- Thymeleaf;
- Maven;
- MySQL Connector/J;
- MySQL;
- demais dependências declaradas no projeto.

A Licença Educacional Não Comercial deste projeto não substitui nem modifica as licenças aplicáveis aos componentes de terceiros.

---

## ⚠️ Garantias e responsabilidade

O projeto é disponibilizado **no estado em que se encontra**, com finalidade educacional.

Não são fornecidas garantias quanto à adequação do software para uma finalidade específica, ausência de erros, disponibilidade, desempenho ou compatibilidade com ambientes específicos.

Consulte o arquivo de licença para conhecer os termos completos relacionados a garantias e responsabilidade.

---

## 📖 Termos completos

Os termos completos estão disponíveis no arquivo:

### [`LICENSE`](LICENSE)

> Esta é uma **licença personalizada para um projeto educacional** e não corresponde a uma licença open source aprovada pela **Open Source Initiative (OSI)**.

Para utilizações institucionais, publicação oficial ou situações que envolvam direitos do Senac ou de terceiros, os termos devem ser submetidos à revisão institucional apropriada.

---

# 📚 Sobre este repositório

Este repositório registra a evolução progressiva do **Sistema de Estacionamento**, permitindo acompanhar a integração entre:

```text
Programação Orientada a Objetos
            +
           Java
            +
        Spring Boot
            +
       Spring MVC
            +
        Thymeleaf
            +
           JDBC
            +
           SQL
            +
          MySQL
            +
           Git
            ↓
     Aplicação Web
```

Mais do que apresentar um sistema finalizado, o projeto busca registrar o **processo de construção, integração e evolução de uma aplicação**, tornando visível o caminho percorrido pelos dados entre interface, aplicação e banco de dados.

---

# ℹ️ Informações finais

**Projeto:** Sistema de Estacionamento  
**Autor:** Lucas Machado  
**Contexto educacional:** Senac  
**Finalidade:** Educacional  
**Tecnologia principal:** Java  
**Framework:** Spring Boot  
**Template Engine:** Thymeleaf  
**Persistência:** JDBC  
**Banco de dados:** MySQL  
**Build e dependências:** Maven  
**Versionamento:** Git / GitHub  
**Licença:** Licença Educacional Não Comercial  
**Status:** Em desenvolvimento
