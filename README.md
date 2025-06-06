# 🎓 Academus - Sistema de Gerenciamento Acadêmico

## 📌 Introdução

**Academus** é uma aplicação desktop desenvolvida em **Java(Swing)** com persistência de dados via **MySQL**, voltada para o gerenciamento acadêmico. A solução permite o cadastro, edição, listagem, exclusão e vinculação de **alunos**, **professores** e **cursos**, além de contar com autenticação de usuários.

O sistema foi desenvolvido com foco em instituições de **pequeno a médio porte**, oferecendo uma ferramenta de fácil uso, eficiente e de baixo custo para controle e organização de dados acadêmicos.

---

## 🖼️ Imagens do Sistema

![Tela de Login](https://github.com/MrGus-exe/Academus/blob/05f7028a4a4a9a4bb2f8626bad545701f1281141/Imagem%20do%20WhatsApp%20de%202025-06-02%20%C3%A0(s)%2023.37.55_7be77d35.jpg)
![Tela Principal](https://github.com/MrGus-exe/Academus/blob/05f7028a4a4a9a4bb2f8626bad545701f1281141/Imagem%20do%20WhatsApp%20de%202025-06-02%20%C3%A0(s)%2023.37.55_702ae3f9.jpg)

---

## ⚙️ Tecnologias Utilizadas

- 💻 Java (Swing)
- 🗃️ MySQL
- 🔌 JDBC
- 🧪 JUnit (testes unitários)
- 🧪 AssertJ Swing (testes automatizados de interface - E2E)
- 🧠 Programação orientada a objetos (POO)
- 📁 Arquitetura em camadas (MVC)

---

## 🧠 Funcionalidades Principais

- Autenticação de usuários
- Cadastro de alunos, professores e cursos
- Edição e exclusão de dados
- Listagem e busca por ID
- Vinculação entre aluno, professor e curso
- Interface gráfica amigável com Java Swing
- Integração com banco de dados MySQL

---

## ✅ Casos de Teste

| ID     | Nome                                       | Objetivo                                                                 |
|--------|--------------------------------------------|--------------------------------------------------------------------------|
| CT001  | Login com dados válidos                    | Verificar se o sistema autentica corretamente com usuário e senha válidos |
| CT002  | Cadastro de aluno, professor ou curso      | Garantir que dados válidos são cadastrados e listados corretamente      |
| CT003  | Exclusão de dados cadastrados              | Verificar exclusão de alunos, professores ou cursos por ID               |
| CT004  | Listagem de alunos, professores e cursos   | Validar exibição correta de dados cadastrados                           |
| CT005  | Vinculação entre aluno, professor e curso  | Garantir que a vinculação entre entidades funciona corretamente         |

---

## 🧪 Planejamento de Testes

O planejamento de testes foi elaborado para garantir a **confiabilidade**, **qualidade** e **funcionalidade** do sistema. Ele abrange os critérios de sucesso para login, cadastro, exclusão, listagem e vinculação entre entidades. Os testes foram baseados em requisitos reais e têm como objetivo identificar falhas, validar comportamentos esperados e reduzir riscos.

---

## 🧾 Resultados e Evidências

Durante os testes, todas as funcionalidades previstas se comportaram conforme esperado. Os dados foram corretamente persistidos, listados e manipulados, e o sistema demonstrou estabilidade e coerência em sua operação. (Adicione aqui prints ou relatórios se desejar.)

---

## 🧠 Conclusão e Lições Aprendidas

O desenvolvimento do sistema **Academus** demonstrou ser uma excelente oportunidade para aplicar conhecimentos de desenvolvimento de software com Java e banco de dados. Dentre os principais aprendizados:

- Construção de interfaces gráficas em Java com Swing
- Aplicação de operações CRUD e autenticação de usuários
- Integração e manipulação de dados com MySQL
- Organização do código em camadas com boas práticas de POO
- Importância do planejamento, testes e validação contínua

O projeto resultou em uma solução funcional, intuitiva e tecnicamente sólida para ambientes educacionais.

---

## 🚀 Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/MrGus-exe/Academus.git

2. Acesse o diretório principal do projeto:
   ```bash
   cd GerenciamentoEstudantil

3. Compile os arquivos com o conector do MySQL:
   ```bash
   javac -cp ".;lib/mysql-connector-j-9.3.0.jar" interface_grafica\ConexaoMySQL.java interface_grafica\GerenciadorDeDados.java interface_grafica\InterfacePrincipal.java

4. Execute a aplicação:
   ```bash
   java -cp ".;lib/mysql-connector-j-9.3.0.jar" interface_grafica.InterfacePrincipal

   

   

