package interface_grafica;

import java.sql.*;
import javax.swing.*;

public class GerenciadorDeDados {

    // === ALUNOS ===
    public static void cadastrarEstudante(String nome, int idade, String matricula) {
        String sql = "INSERT INTO alunos (nome, idade, matricula) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setInt(2, idade);
            stmt.setString(3, matricula);
            stmt.executeUpdate();
            System.out.println("Aluno cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String listarEstudantesRetorno() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM alunos";

        try (Connection conn = ConexaoMySQL.conectar();
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

    public static void editarEstudante(int id, String nome, int idade, String matricula) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, matricula = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
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

        try (Connection conn = ConexaoMySQL.conectar();
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

        try (Connection conn = ConexaoMySQL.conectar();
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
        String sql = "SELECT * FROM professores";

        try (Connection conn = ConexaoMySQL.conectar();
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

    public static void editarProfessor(int id, String nome, int idade, String especialidade) {
        String sql = "UPDATE professores SET nome = ?, idade = ?, especialidade = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
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

        try (Connection conn = ConexaoMySQL.conectar();
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

        try (Connection conn = ConexaoMySQL.conectar();
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
        String sql = "SELECT * FROM cursos";

        try (Connection conn = ConexaoMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Curso: ").append(rs.getString("nome_curso"))
                  .append(", Carga Horária: ").append(rs.getInt("carga_horaria"))
                  .append(", Professor: ").append(rs.getString("professor_responsavel"))
                  .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void editarCurso(int id, String nomeCurso, int cargaHoraria, String professorResponsavel) {
        String sql = "UPDATE cursos SET nome_curso = ?, carga_horaria = ?, professor_responsavel = ? WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
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

        try (Connection conn = ConexaoMySQL.conectar();
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

        try (Connection conn = ConexaoMySQL.conectar();
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
        String sql = "SELECT v.id, a.nome AS aluno, c.nome_curso AS curso, p.nome AS professor " +
                     "FROM vinculacoes v " +
                     "JOIN alunos a ON v.aluno_id = a.id " +
                     "JOIN cursos c ON v.curso_id = c.id " +
                     "JOIN professores p ON v.professor_id = p.id";

        try (Connection conn = ConexaoMySQL.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id"))
                  .append(", Aluno: ").append(rs.getString("aluno"))
                  .append(", Curso: ").append(rs.getString("curso"))
                  .append(", Professor: ").append(rs.getString("professor"))
                  .append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void excluirVinculacao(int id) {
        String sql = "DELETE FROM vinculacoes WHERE id = ?";

        try (Connection conn = ConexaoMySQL.conectar();
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
        String sql = "SELECT * FROM usuarios WHERE username = ? AND senha = ?";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registrarUsuario(String username, String senha) {
        String sql = "INSERT INTO usuarios (username, senha) VALUES (?, ?)";

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, senha);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate")) {
                JOptionPane.showMessageDialog(null, "Usuário já existe!");
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }
}
