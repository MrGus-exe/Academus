package interface_grafica;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteGerenciadorDeDados {

    @Before // Para JUnit 4. Se fosse JUnit 5, seria @BeforeEach
    public void limparDadosDeTeste() {
        System.out.println("--- INICIANDO LIMPEZA DE DADOS DE TESTE ---");
        try (Connection conn = ConexaoMySQL.conectar(); //
             Statement stmt = conn.createStatement()) {

            // --- Limpeza de Usuários ---
            String sqlDeleteUsuarios = "DELETE FROM usuarios WHERE username IN ('testeJUnit', 'loginJUnit')";
            System.out.println("Executando limpeza de usuários: " + sqlDeleteUsuarios);
            int usuariosDeletados = stmt.executeUpdate(sqlDeleteUsuarios);
            System.out.println("Usuários de teste ('testeJUnit', 'loginJUnit') deletados: " + usuariosDeletados);

            // --- Limpeza de Alunos (AGORA INCLUINDO MAT998) ---
            String sqlDeleteAlunos = "DELETE FROM alunos WHERE matricula LIKE 'MAT_DEL_%' OR matricula = 'MAT999' OR matricula = 'MAT998' OR nome = 'Teste JUnit' OR nome = 'Excluir JUnit'";
            System.out.println("Executando limpeza de alunos: " + sqlDeleteAlunos);
            int alunosDeletados = stmt.executeUpdate(sqlDeleteAlunos);
            System.out.println("Alunos de teste (incluindo MAT998) deletados: " + alunosDeletados);

            // --- Limpeza de Professores ---
            String sqlDeleteProfessores = "DELETE FROM professores WHERE nome LIKE 'Prof_DEL_%' OR nome = 'Prof JUnit' OR nome = 'Excluir Prof'";
            System.out.println("Executando limpeza de professores: " + sqlDeleteProfessores);
            int professoresDeletados = stmt.executeUpdate(sqlDeleteProfessores);
            System.out.println("Professores de teste deletados: " + professoresDeletados);

            // --- Limpeza de Cursos ---
            String sqlDeleteCursos = "DELETE FROM cursos WHERE nome_curso LIKE '%JUnit%' OR nome_curso LIKE 'Excluir Curso%' OR nome_curso LIKE 'Excluir Curso JUnit %'";
            System.out.println("Executando limpeza de cursos: " + sqlDeleteCursos);
            int cursosDeletados = stmt.executeUpdate(sqlDeleteCursos);
            System.out.println("Cursos de teste deletados: " + cursosDeletados);

            System.out.println("--- LIMPEZA DE DADOS DE TESTE CONCLUÍDA COM SUCESSO ---");

        } catch (SQLException e) {
            System.err.println("### ERRO CRÍTICO AO LIMPAR DADOS DE TESTE (DENTRO DO @Before): " + e.getMessage());
            e.printStackTrace(); 
            fail("Falha crítica ao limpar o banco de dados antes do teste: " + e.getMessage());
        }
    }

    // === ALUNOS ===
    // O teste original testExcluirEstudante usa MAT998.
    // A limpeza @Before agora TENTA remover MAT998.
    // O teste modificado abaixo usa MAT_DEL_ para evitar o conflito se a limpeza por algum motivo não pegar MAT998 de execuções muito antigas.
    // Se o erro de MAT998 persistir, significa que a limpeza de MAT998 no @Before não está funcionando ou não está sendo executada ANTES deste teste específico.

    @Test
    public void testCadastrarEstudante() {
        GerenciadorDeDados.cadastrarEstudante("Teste JUnit", 22, "MAT999"); //
        String resultado = GerenciadorDeDados.listarEstudantesRetorno(); //
        assertTrue("O estudante 'Teste JUnit' deveria estar na lista após o cadastro.", resultado.contains("Teste JUnit")); //
    }

    @Test
    public void testExcluirEstudante() {
        // Este teste usa uma matrícula dinâmica para evitar conflitos com execuções anteriores
        // e para garantir que a lógica de exclusão seja testada com dados frescos.
        String nomeEstudanteParaExcluir = "Excluir JUnit Dinamico"; 
        String matriculaParaExcluir = "MAT_DEL_" + System.currentTimeMillis();

        // A linha original que causava o erro MAT998 era:
        // GerenciadorDeDados.cadastrarEstudante("Excluir JUnit", 23, "MAT998");
        // Agora usamos a matrícula dinâmica:
        GerenciadorDeDados.cadastrarEstudante(nomeEstudanteParaExcluir, 23, matriculaParaExcluir);

        String listaAntes = GerenciadorDeDados.listarEstudantesRetorno(); //
        int idParaExcluir = -1;
        for (String linha : listaAntes.split("\n")) {
            if (linha.contains(", Matrícula: " + matriculaParaExcluir) && linha.contains(nomeEstudanteParaExcluir)) {
                try {
                    String idStr = linha.substring(linha.indexOf("ID: ") + 4, linha.indexOf(","));
                    idParaExcluir = Integer.parseInt(idStr.trim());
                    break;
                } catch (Exception e) {
                    fail("Erro ao extrair ID do estudante para exclusão: " + e.getMessage() + " da linha: " + linha);
                }
            }
        }
        assertTrue("ID do estudante '" + nomeEstudanteParaExcluir + "' com matrícula '" + matriculaParaExcluir + "' não encontrado para exclusão.", idParaExcluir != -1);

        GerenciadorDeDados.excluirEstudante(idParaExcluir); //
        String novaLista = GerenciadorDeDados.listarEstudantesRetorno(); //
        assertFalse("O estudante '" + nomeEstudanteParaExcluir + "' ainda foi encontrado após a tentativa de exclusão.", novaLista.contains(nomeEstudanteParaExcluir)); //
        assertFalse("A matrícula '" + matriculaParaExcluir + "' do estudante excluído ainda foi encontrada.", novaLista.contains(matriculaParaExcluir));
    }

    // === PROFESSORES ===
    @Test
    public void testCadastrarProfessor() {
        GerenciadorDeDados.cadastrarProfessor("Prof JUnit", 40, "Matemática"); //
        String resultado = GerenciadorDeDados.listarProfessoresRetorno(); //
        assertTrue("O professor 'Prof JUnit' deveria estar na lista após o cadastro.", resultado.contains("Prof JUnit")); //
    }

    @Test
    public void testExcluirProfessor() {
        String nomeProfessorParaExcluir = "Prof_DEL_" + System.currentTimeMillis();
        String especialidadeTeste = "Física Exclusão";

        GerenciadorDeDados.cadastrarProfessor(nomeProfessorParaExcluir, 50, especialidadeTeste); //

        String listaAntes = GerenciadorDeDados.listarProfessoresRetorno(); //
        int idParaExcluir = -1;
        for (String linha : listaAntes.split("\n")) {
            if (linha.contains(", Nome: " + nomeProfessorParaExcluir) && linha.contains(", Especialidade: " + especialidadeTeste) ) {
                 try {
                    String idStr = linha.substring(linha.indexOf("ID: ") + 4, linha.indexOf(","));
                    idParaExcluir = Integer.parseInt(idStr.trim());
                    break;
                } catch (Exception e) {
                    fail("Erro ao extrair ID do professor para exclusão: " + e.getMessage() + " da linha: " + linha);
                }
            }
        }
        assertTrue("ID do professor '" + nomeProfessorParaExcluir + "' não encontrado para exclusão.", idParaExcluir != -1);

        GerenciadorDeDados.excluirProfessor(idParaExcluir); //
        String novaLista = GerenciadorDeDados.listarProfessoresRetorno(); //
        assertFalse("O professor '" + nomeProfessorParaExcluir + "' ainda foi encontrado após a tentativa de exclusão.", novaLista.contains(nomeProfessorParaExcluir)); //
    }

    // === CURSOS ===
    @Test
    public void testCadastrarCurso() {
        GerenciadorDeDados.cadastrarCurso("Curso JUnit", 60, "Prof Teste"); //
        String resultado = GerenciadorDeDados.listarCursosRetorno(); //
        assertTrue("O curso 'Curso JUnit' deveria estar na lista após o cadastro.", resultado.contains("Curso JUnit")); //
    }

    @Test
    public void testExcluirCurso() {
        String nomeCursoParaExcluir = "Excluir Curso JUnit " + System.currentTimeMillis();
        GerenciadorDeDados.cadastrarCurso(nomeCursoParaExcluir, 90, "Prof Responsavel Teste"); //

        String listaAntes = GerenciadorDeDados.listarCursosRetorno(); //
        int idParaExcluir = -1;
        for (String linha : listaAntes.split("\n")) {
            if (linha.contains(", Curso: " + nomeCursoParaExcluir)) {
                try {
                    String idStr = linha.substring(linha.indexOf("ID: ") + 4, linha.indexOf(","));
                    idParaExcluir = Integer.parseInt(idStr.trim());
                    break;
                } catch (Exception e) {
                    fail("Erro ao extrair ID do curso para exclusão: " + e.getMessage() + " da linha: " + linha);
                }
            }
        }
        assertTrue("ID do curso '" + nomeCursoParaExcluir + "' não encontrado para exclusão.", idParaExcluir != -1);

        GerenciadorDeDados.excluirCurso(idParaExcluir); //
        String novaLista = GerenciadorDeDados.listarCursosRetorno(); //
        assertFalse("O curso '" + nomeCursoParaExcluir + "' ainda foi encontrado após a tentativa de exclusão.", novaLista.contains(nomeCursoParaExcluir)); //
    }

    // === USUÁRIOS ===
    @Test
    public void testRegistrarUsuario() {
        System.out.println("--- Iniciando testRegistrarUsuario ---");
        boolean resultado = GerenciadorDeDados.registrarUsuario("testeJUnit", "senhaForte123!"); //
        assertTrue("O registro do usuário 'testeJUnit' deveria ter sido bem-sucedido.", resultado); //
        // Se o JOptionPane ainda aparecer aqui, a limpeza de 'testeJUnit' no @Before falhou.
        System.out.println("--- Finalizando testRegistrarUsuario (resultado: " + resultado + ") ---");
    }

    @Test
    public void testAutenticarUsuario() {
        System.out.println("--- Iniciando testAutenticarUsuario ---");
        GerenciadorDeDados.registrarUsuario("loginJUnit", "outraSenhaSegura456@"); //

        boolean autenticado = GerenciadorDeDados.autenticarUsuario("loginJUnit", "outraSenhaSegura456@"); //
        assertTrue("A autenticação do usuário 'loginJUnit' deveria ter sido bem-sucedida.", autenticado); //

        boolean naoAutenticadoSenhaErrada = GerenciadorDeDados.autenticarUsuario("loginJUnit", "senhaErrada123");
        assertFalse("A autenticação do usuário 'loginJUnit' com senha errada deveria falhar.", naoAutenticadoSenhaErrada);

        boolean naoAutenticadoUsuarioInexistente = GerenciadorDeDados.autenticarUsuario("usuarioQueNaoExisteRealmente", "senha123");
        assertFalse("A autenticação de um usuário inexistente deveria falhar.", naoAutenticadoUsuarioInexistente);
        System.out.println("--- Finalizando testAutenticarUsuario ---");
    }
}