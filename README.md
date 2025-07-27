
🗂 Projeto: Sistema de Cadastro com SQLite em Java

Este é um projeto simples de terminal feito em Java que simula um sistema de cadastro utilizando um banco de dados SQLite. O objetivo é praticar conceitos como:

Integração com banco de dados usando JDBC;

CRUD básico (Create, Read, Update, Delete);

Estruturação de código Java;

Manipulação de entradas do usuário via terminal.

---

📌 Funcionalidades

✅ Cadastrar uma nova pessoa (nome e idade)

✅ Verificar se o nome já existe antes de cadastrar

✅ Listar todos os cadastros

✅ Excluir registros pelo ID

✅ Buscar ID a partir do nome



---

🛠 Tecnologias Utilizadas

Java (JDK 8+)

SQLite

JDBC (Java Database Connectivity)



---

▶️ Como rodar o projeto

1. Clone o repositório

```git
git clone https://github.com/seu-usuario/ProjetobancoJava.git
cd ProjetobancoJava
```


2. Compile os arquivos Java

> Certifique-se de que o sqlite-jdbc está no seu classpath


```
javac -cp ".:sqlite-jdbc-<versao>.jar" *.java
```

3. Execute o programa

```
java -cp ".:sqlite-jdbc-<versao>.jar" Principal
```


---

🧠 Aprendizados

Esse projeto foi desenvolvido como uma forma de colocar em prática meus estudos iniciais com banco de dados e Java. Nele, aprendi:

Como criar e manipular um banco SQLite via código;

Como evitar erros como "NOT NULL constraint failed";

Como otimizar a verificação de dados diretamente via SQL, sem iterar listas manualmente;

A importância da separação de responsabilidades no código.



---

💡 Melhorias futuras

Validação de entrada mais robusta

Interface gráfica (possivelmente com JavaFX ou Swing)

Exportação de dados em CSV

Ordenação ou filtros nas listagens



---