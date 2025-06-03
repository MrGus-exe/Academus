package interface_grafica;

import java.util.List;

public class Relatorios {

    public static String gerarRelatorioAlunos(List<String> alunos) {
        StringBuilder relatorio = new StringBuilder("Relatório de Alunos:\n");
        for (String aluno : alunos) {
            relatorio.append(aluno).append("\n");
        }
        return relatorio.toString();
    }

    public static String gerarRelatorioProfessores(List<String> professores) {
        StringBuilder relatorio = new StringBuilder("Relatório de Professores:\n");
        for (String professor : professores) {
            relatorio.append(professor).append("\n");
        }
        return relatorio.toString();
    }

    public static String gerarRelatorioCursos(List<String> cursos) {
        StringBuilder relatorio = new StringBuilder("Relatório de Cursos:\n");
        for (String curso : cursos) {
            relatorio.append(curso).append("\n");
        }
        return relatorio.toString();
    }
}
