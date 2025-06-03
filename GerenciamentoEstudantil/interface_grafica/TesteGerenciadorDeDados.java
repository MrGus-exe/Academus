package interface_grafica;

import org.junit.*;
import static org.junit.Assert.*;

public class TesteGerenciadorDeDados {

    // === ALUNOS ===
    @Test
    public void testCadastrarEstudante() {
        GerenciadorDeDados.cadastrarEstudante("Teste JUnit", 22, "MAT999");
        String resultado = GerenciadorDeDados.listarEstudantesRetorno();
        assertTrue(resultado.contains("Teste JUnit"));
    }

    @Test
    public void testExcluirEstudante() {
        GerenciadorDeDados.cadastrarEstudante("Excluir JUnit", 23, "MAT998");
        String lista = GerenciadorDeDados.listarEstudantesRetorno();
        String[] linhas = lista.split("\n");
        String ultimaLinha = linhas[linhas.length - 1];
        int id = Integer.parseInt(ultimaLinha.split(",")[0].split(":")[1].trim());
        GerenciadorDeDados.excluirEstudante(id);
        String novaLista = GerenciadorDeDados.listarEstudantesRetorno();
        assertFalse(novaLista.contains("Excluir JUnit"));
    }

    // === PROFESSORES ===
    @Test
    public void testCadastrarProfessor() {
        GerenciadorDeDados.cadastrarProfessor("Prof JUnit", 40, "Matemática");
        String resultado = GerenciadorDeDados.listarProfessoresRetorno();
        assertTrue(resultado.contains("Prof JUnit"));
    }

    @Test
    public void testExcluirProfessor() {
        GerenciadorDeDados.cadastrarProfessor("Excluir Prof", 50, "Física");
        String lista = GerenciadorDeDados.listarProfessoresRetorno();
        String[] linhas = lista.split("\n");
        String ultimaLinha = linhas[linhas.length - 1];
        int id = Integer.parseInt(ultimaLinha.split(",")[0].split(":")[1].trim());
        GerenciadorDeDados.excluirProfessor(id);
        String novaLista = GerenciadorDeDados.listarProfessoresRetorno();
        assertFalse(novaLista.contains("Excluir Prof"));
    }

    // === CURSOS ===
    @Test
    public void testCadastrarCurso() {
        GerenciadorDeDados.cadastrarCurso("Curso JUnit", 60, "Prof Teste");
        String resultado = GerenciadorDeDados.listarCursosRetorno();
        assertTrue(resultado.contains("Curso JUnit"));
    }

    @Test
    public void testExcluirCurso() {
        GerenciadorDeDados.cadastrarCurso("Excluir Curso", 90, "Prof Responsa");
        String lista = GerenciadorDeDados.listarCursosRetorno();
        String[] linhas = lista.split("\n");
        String ultimaLinha = linhas[linhas.length - 1];
        int id = Integer.parseInt(ultimaLinha.split(",")[0].split(":")[1].trim());
        GerenciadorDeDados.excluirCurso(id);
        String novaLista = GerenciadorDeDados.listarCursosRetorno();
        assertFalse(novaLista.contains("Excluir Curso"));
    }

    // === USUÁRIOS ===
    @Test
    public void testRegistrarUsuario() {
        boolean resultado = GerenciadorDeDados.registrarUsuario("testeJUnit", "1234");
        assertTrue(resultado);
    }

    @Test
    public void testAutenticarUsuario() {
        GerenciadorDeDados.registrarUsuario("loginJUnit", "senha123");
        boolean autenticado = GerenciadorDeDados.autenticarUsuario("loginJUnit", "senha123");
        assertTrue(autenticado);
    }
}
