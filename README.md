# API Voll.med

![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.4-blue?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Maven](https://img.shields.io/badge/Maven-4.0.0-red?style=for-the-badge&logo=apache-maven)

## 📖 Sobre o Projeto

A **API Voll.med** é um sistema back-end para gerenciamento de uma clínica médica. Desenvolvida como parte do programa de formação da Alura, esta API RESTful permite o cadastro de médicos e pacientes, além do agendamento e cancelamento de consultas, implementando diversas validações e regras de negócio.

O projeto foca nas operações de CRUD (Create, Read, Update, Delete) e na lógica de agendamento da clínica.

---

## 🛠️ Tecnologias Utilizadas

As seguintes ferramentas e tecnologias foram utilizadas na construção do projeto:

-   **Java 17:** Versão da linguagem de programação.
-   **Spring Boot 3:** Framework principal para criação da API.
-   **Spring Web:** Para criar aplicações web e APIs RESTful.
-   **Spring Data JPA:** Para persistência de dados em um banco relacional.
-   **Maven:** Gerenciador de dependências do projeto.
-   **MySQL:** Banco de dados relacional para armazenar os dados.
-   **Flyway:** Ferramenta para versionamento e migração do banco de dados.
-   **Lombok:** Para reduzir o código boilerplate (getters, setters, construtores).

---

## ✨ Funcionalidades

-   👨‍⚕️ **Gerenciamento de Médicos:**
    -   [x] Cadastro de novos médicos.
    -   [x] Listagem e paginação de médicos ativos.
    -   [x] Atualização de informações de um médico.
    -   [x] Exclusão lógica (inativação) de um médico.
-   👥 **Gerenciamento de Pacientes:**
    -   [x] Cadastro de novos pacientes.
    -   [x] Listagem e paginação de pacientes ativos.
    -   [x] Atualização de informações de um paciente.
    -   [x] Exclusão lógica (inativação) de um paciente.
-   🗓️ **Gerenciamento de Consultas:**
    -   [x] Agendamento de novas consultas com validações de negócio.
    -   [x] Cancelamento de consultas.

---

## 🚀 Como Executar o Projeto

Para executar este projeto em sua máquina local, siga os passos abaixo.

### **Pré-requisitos**

-   [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) ou superior.
-   [Maven 3.8](https://maven.apache.org/download.cgi) ou superior.
-   [MySQL](https://dev.mysql.com/downloads/mysql/) ou um container Docker com uma imagem do MySQL.

### **Passo a Passo**

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/yami-r7/api-clinica.git](https://github.com/yami-r7/api-clinica.git)
    cd api-clinica
    ```

2.  **Configure o Banco de Dados:**
    - Crie um banco de dados no MySQL chamado `vollmed_api`.
    - Abra o arquivo `src/main/resources/application.properties`.
    - Altere as seguintes propriedades com as suas credenciais do MySQL:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost/vollmed_api
      spring.datasource.username=SEU_USUARIO_AQUI
      spring.datasource.password=SUA_SENHA_AQUI
      ```

3.  **Execute a Aplicação:**
    - Utilize o Maven para compilar e executar o projeto:
    ```bash
    mvn spring-boot:run
    ```
    - A API estará disponível em `http://localhost:8080`.

---

## Endpoints da API (Documentação)

### Médicos

| Método | Endpoint         | Descrição                            |
| :----- | :--------------- | :----------------------------------- |
| `POST` | `/medicos`       | Cadastra um novo médico.             |
| `GET`  | `/medicos`       | Lista todos os médicos ativos.       |
| `PUT`  | `/medicos`       | Atualiza os dados de um médico.      |
| `DELETE`| `/medicos/{id}` | Inativa o cadastro de um médico.     |

**Exemplo de Request Body para `POST /medicos`:**
```json
{
    "nome": "Rodrigo",
    "email": "rodrigo@voll.med",
    "crm": "123456",
    "especialidade": "ORTOPEDIA",
    "telefone": "11988887777",
    "endereco": {
        "logouro": "rua 1",
        "bairro": "bairro",
        "cep": "12345678",
        "cidade": "Brasilia",
        "uf": "DF",
        "numero": "1",
        "complemento": "complemento"
    }
}
````

-----

### Consultas

| Método | Endpoint         | Descrição                            |
| :----- | :--------------- | :----------------------------------- |
| `POST` | `/consultas`     | Agenda uma nova consulta.            |
| `DELETE` | `/consultas`     | Cancela uma consulta agendada.       |

**Exemplo de Request Body para `POST /consultas`:**

```json
{
    "idMedico": 1,
    "idPaciente": 1,
    "data": "2025-10-10T10:00:00"
}
