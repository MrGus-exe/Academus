package interface_grafica;

import java.sql.*;
import javax.swing.*; // Import necessário para JOptionPane em registrarUsuario

public class GerenciadorDeDados {

    // === ALUNOS ===
    public static void cadastrarEstudante(String nome, int idade, String matricula) {
        String sql = "INSERT INTO alunos (nome, idade, matricula) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setString(3, matricula);
            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            // Idealmente, tratar o erro na interface gráfica
        }
    }

    public static String listarEstudantesRetorno() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM alunos ORDER BY nome"; 

        try (Connection conn = ConexaoMySQL.conectar(); //
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Nome: ").append(rs.getString("nome"))
                  .append(", Idade: ").append(rs.getInt("idade"))
                  .append(", Matrícula: ").append(rs.getString("matricula"))
                  .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Busca um aluno pelo ID para carregar seus dados para edição.
     * @param id O ID do aluno a ser buscado.
     * @return Um array de String com [nome, idade, matricula], ou null se não encontrado.
     */
    public static String[] getEstudanteParaEdicao(int id) {
        String sql = "SELECT nome, idade, matricula FROM alunos WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String[] dadosAluno = new String[3];
                dadosAluno[0] = rs.getString("nome");
                dadosAluno[1] = Integer.toString(rs.getInt("idade")); 
                dadosAluno[2] = rs.getString("matricula");
                return dadosAluno;
            } else {
                return null; 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void editarEstudante(int id, String nome, int idade, String matricula) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, matricula = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setString(3, matricula);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Aluno atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirEstudante(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Aluno excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === PROFESSORES ===
    public static void cadastrarProfessor(String nome, int idade, String especialidade) {
        String sql = "INSERT INTO professores (nome, idade, especialidade) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setString(3, especialidade);
            stmt.executeUpdate();
            System.out.println("Professor cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String listarProfessoresRetorno() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM professores ORDER BY nome"; 

        try (Connection conn = ConexaoMySQL.conectar(); //
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Nome: ").append(rs.getString("nome"))
                  .append(", Idade: ").append(rs.getInt("idade"))
                  .append(", Especialidade: ").append(rs.getString("especialidade"))
                  .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Busca um professor pelo ID para carregar seus dados para edição.
     * @param id O ID do professor a ser buscado.
     * @return Um array de String com [nome, idade, especialidade], ou null se não encontrado.
     */
    public static String[] getProfessorParaEdicao(int id) {
        String sql = "SELECT nome, idade, especialidade FROM professores WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String[] dadosProfessor = new String[3];
                dadosProfessor[0] = rs.getString("nome");
                dadosProfessor[1] = Integer.toString(rs.getInt("idade"));
                dadosProfessor[2] = rs.getString("especialidade");
                return dadosProfessor;
            } else {
                return null; // Professor não encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void editarProfessor(int id, String nome, int idade, String especialidade) {
        String sql = "UPDATE professores SET nome = ?, idade = ?, especialidade = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setString(3, especialidade);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Professor atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirProfessor(int id) {
        String sql = "DELETE FROM professores WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Professor excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === CURSOS ===
    public static void cadastrarCurso(String nomeCurso, int cargaHoraria, String professorResponsavel) {
        String sql = "INSERT INTO cursos (nome_curso, carga_horaria, professor_responsavel) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeCurso);
            stmt.setInt(2, cargaHoraria);
            stmt.setString(3, professorResponsavel);
            stmt.executeUpdate();
            System.out.println("Curso cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String listarCursosRetorno() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM cursos ORDER BY nome_curso"; 

        try (Connection conn = ConexaoMySQL.conectar(); //
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Curso: ").append(rs.getString("nome_curso"))
                  .append(", Carga Horária: ").append(rs.getInt("carga_horaria"))
                  .append("h, Professor Resp.: ").append(rs.getString("professor_responsavel")) // "h" adicionado e "Professor" abreviado
                  .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Busca um curso pelo ID para carregar seus dados para edição.
     * @param id O ID do curso a ser buscado.
     * @return Um array de String com [nome_curso, carga_horaria, professor_responsavel], ou null se não encontrado.
     */
    public static String[] getCursoParaEdicao(int id) {
        String sql = "SELECT nome_curso, carga_horaria, professor_responsavel FROM cursos WHERE id = ?";
        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String[] dadosCurso = new String[3];
                dadosCurso[0] = rs.getString("nome_curso");
                dadosCurso[1] = Integer.toString(rs.getInt("carga_horaria"));
                dadosCurso[2] = rs.getString("professor_responsavel");
                return dadosCurso;
            } else {
                return null; // Curso não encontrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void editarCurso(int id, String nomeCurso, int cargaHoraria, String professorResponsavel) {
        String sql = "UPDATE cursos SET nome_curso = ?, carga_horaria = ?, professor_responsavel = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nomeCurso);
            stmt.setInt(2, cargaHoraria);
            stmt.setString(3, professorResponsavel);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Curso atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirCurso(int id) {
        String sql = "DELETE FROM cursos WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Curso excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === VINCULAÇÕES ===
    public static void cadastrarVinculacao(int alunoId, int cursoId, int professorId) {
        String sql = "INSERT INTO vinculacoes (aluno_id, curso_id, professor_id) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);
            stmt.setInt(2, cursoId);
            stmt.setInt(3, professorId);
            stmt.executeUpdate();
            System.out.println("Vinculação cadastrada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String listarVinculacoesRetorno() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT v.id, a.nome AS nome_aluno, c.nome_curso, p.nome AS nome_professor " + // Alias para clareza
                     "FROM vinculacoes v " +
                     "JOIN alunos a ON v.aluno_id = a.id " +
                     "JOIN cursos c ON v.curso_id = c.id " +
                     "JOIN professores p ON v.professor_id = p.id " +
                     "ORDER BY nome_aluno, c.nome_curso"; 

        try (Connection conn = ConexaoMySQL.conectar(); //
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append("ID Vinculação: ").append(rs.getInt("id")) // Texto atualizado
                  .append(", Aluno: ").append(rs.getString("nome_aluno"))
                  .append(", Curso: ").append(rs.getString("nome_curso"))
                  .append(", Professor no Curso: ").append(rs.getString("nome_professor")) // Texto atualizado
                  .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void excluirVinculacao(int id) {
        String sql = "DELETE FROM vinculacoes WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Vinculação excluída com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === USUÁRIOS ===
    public static boolean autenticarUsuario(String username, String senha) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND senha = ?"; // Idealmente, senhas devem ser hasheadas

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, senha); // Comparação de senha em texto plano (não recomendado para produção)

            ResultSet rs = stmt.executeQuery();
            return rs.next(); 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registrarUsuario(String username, String senha) {
        // Idealmente, a senha seria hasheada antes de inserir no banco.
        // Ex: String senhaHasheada = BCrypt.hashpw(senha, BCrypt.gensalt());
        String sql = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";

        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, senha); // Armazenando senha em texto plano (não recomendado)
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            if (e.getSQLState().equals("23000") || e.getMessage().toLowerCase().contains("duplicate entry")) { 
                JOptionPane.showMessageDialog(null, "O nome de usuário '" + username + "' já está em uso. Por favor, escolha outro.", "Usuário Existente", JOptionPane.WARNING_MESSAGE);
            } else {
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(null, "Erro ao registrar usuário no banco de dados: " + e.getMessage(), "Erro Crítico no Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
}