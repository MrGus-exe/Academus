package interface_grafica;

public class Professor extends Pessoa {
    private String especialidade;

    public Professor(String nome, int idade, String especialidade) {
        super(nome, idade); 
        this.especialidade = especialidade;
    }

    @Override
    public String exibirDados() {
        return "Professor: Nome = " + nome + ", Idade = " + idade + ", Especialidade = " + especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }
}
