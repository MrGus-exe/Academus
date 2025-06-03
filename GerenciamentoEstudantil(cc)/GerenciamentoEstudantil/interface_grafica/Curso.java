package interface_grafica;

public class Curso {
    private String nomeCurso;
    private int cargaHoraria;
    private String professor;

    public Curso(String nomeCurso, int cargaHoraria, String professor) {
        this.nomeCurso = nomeCurso;
        this.cargaHoraria = cargaHoraria;
        this.professor = professor;
    }

    public String exibirDados() {
        return "Curso: Nome = " + nomeCurso + ", Carga Horária = " + cargaHoraria + ", Professor = " + professor;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public String getProfessor() {
        return professor;
    }
}
