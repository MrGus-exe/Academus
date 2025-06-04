package interface_grafica;

public class Estudante extends Pessoa {
    private String matricula;

    public Estudante(String nome, int idade, String matricula) {
        super(nome, idade); // Chama o construtor da classe pai
        this.matricula = matricula;
    }

    @Override
    public String exibirDados() {
        return "Estudante: Nome = " + nome + ", Idade = " + idade + ", Matrícula = " + matricula;
    }

    public String getMatricula() {
        return matricula;
    }
}
