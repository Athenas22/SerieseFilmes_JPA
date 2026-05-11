# JPA - Série de Filmes

**IFPB - TSI | Persistência de Objetos 2026.1**
**Prof. Fausto Ayres**

**Grupo:**
- Vinícius Ares - 20242370007
- Maria Laura - 20242370015
- Luana Macedo - 20242370026

---

## Descrição
Adaptação do projeto DB4O de Série de Filmes para JPA/Hibernate com banco de dados PostgreSQL.

## Modelo
- `Serie` — id, nome, ano, lista de episódios, lista de gêneros
- `Episodio` — id, nome
- `Genero` — id, nome, lista de séries

## Tecnologias
- Java 21
- Hibernate 7
- JPA
- PostgreSQL
- Log4j2
- Maven

## Como executar

### 1. Pré-requisitos
- PostgreSQL instalado e rodando
- Banco de dados `pob` criado no PostgreSQL
- Lembrar da senha no pgAdmin4

### 2. Criar o banco
```sql
CREATE DATABASE pob;
```

### 3. Rodar na ordem
1. `Cadastrar` — cadastra séries, episódios e gêneros
2. `Listar` — lista todos os objetos do banco
3. `Alterar` — remove relacionamento entre série e gênero
4. `Deletar` — apaga uma série e seus episódios
5. `Consultar` — executa as 3 consultas JPQL

### 4. Resetar o banco (ou abacar o banco)
```sql
DROP TABLE IF EXISTS serie20242370007_genero20242370007 CASCADE;
DROP TABLE IF EXISTS episodio20242370007 CASCADE;
DROP TABLE IF EXISTS genero20242370007 CASCADE;
DROP TABLE IF EXISTS serie20242370007 CASCADE;
```

## Consultas JPQL
1. Séries do ano X
2. Séries do gênero de nome X
3. Séries com mais de N episódios