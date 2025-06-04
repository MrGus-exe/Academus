package interface_grafica;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class GerenciamentoEstudantil extends JFrame {

    private static final String ARQUIVO_ALUNOS = "alunos.txt";
    private static final String ARQUIVO_PROFESSORES = "professores.txt";
    private static final String ARQUIVO_CURSOS = "cursos.txt";
    private static final String ARQUIVO_VINCULACOES = "vinculacoes.txt";

    public GerenciamentoEstudantil() {
        setTitle("Gerenciamento Estudantil");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        JButton btnGerenciarAlunos = new JButton("Gerenciar Alunos");
        JButton btnGerenciarProfessores = new JButton("Gerenciar Professores");
        JButton btnGerenciarCursos = new JButton("Gerenciar Cursos");
        JButton btnGerarRelatorios = new JButton("Gerar Relatórios");
        JButton btnVerVinculacoes = new JButton("Ver Alunos e Professores dos Cursos");
        JButton btnSair = new JButton("Sair do Programa");

        btnGerenciarAlunos.addActionListener(e -> gerenciar("alunos"));
        btnGerenciarProfessores.addActionListener(e -> gerenciar("professores"));
        btnGerenciarCursos.addActionListener(e -> gerenciar("cursos"));
        btnGerarRelatorios.addActionListener(e -> gerarRelatorios());
        btnVerVinculacoes.addActionListener(e -> verVinculacoes());
        btnSair.addActionListener(e -> sairDoPrograma());

        add(btnGerenciarAlunos);
        add(btnGerenciarProfessores);
        add(btnGerenciarCursos);
        add(btnGerarRelatorios);
        add(btnVerVinculacoes);
        add(btnSair);
    }

    private void editar(String tipo) {
        try {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome para editar:");
            if (nome == null || nome.isEmpty()) return;

            String arquivo = switch (tipo) {
                case "alunos" -> ARQUIVO_ALUNOS;
                case "professores" -> ARQUIVO_PROFESSORES;
                case "cursos" -> ARQUIVO_CURSOS;
                default -> throw new IllegalArgumentException("Tipo inválido");
            };

            List<String> dados = GerenciadorDeArquivos.lerDados(arquivo);
            boolean encontrado = false;

            for (int i = 0; i < dados.size(); i++) {
                String linha = dados.get(i);
                if (linha.startsWith(nome + ",")) {
                    encontrado = true;
                    String[] partes = linha.split(",");

                    String novoNome = JOptionPane.showInputDialog(this, "Editar Nome:", partes[0]);
                    String novaIdadeOuCarga = JOptionPane.showInputDialog(this, tipo.equals("cursos") ? "Editar Carga Horária:" : "Editar Idade:", partes[1]);
                    String novoExtra = JOptionPane.showInputDialog(this, tipo.equals("alunos") ? "Editar Matrícula:" : (tipo.equals("professores") ? "Editar Especialidade:" : "Editar Professor Responsável:"), partes[2]);

                    String novaLinha = (novoNome != null ? novoNome : partes[0]) + "," +
                            (novaIdadeOuCarga != null ? novaIdadeOuCarga : partes[1]) + "," +
                            (novoExtra != null ? novoExtra : partes[2]);

                    dados.set(i, novaLinha);
                    JOptionPane.showMessageDialog(this, "Registro atualizado com sucesso!");
                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "Registro não encontrado.");
                return;
            }

            GerenciadorDeArquivos.sobrescreverDados(arquivo, dados);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao editar: " + e.getMessage());
        }
    }

    private void excluir(String tipo) {
        try {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome para excluir:");
            if (nome == null || nome.isEmpty()) return;

            String arquivo = switch (tipo) {
                case "alunos" -> ARQUIVO_ALUNOS;
                case "professores" -> ARQUIVO_PROFESSORES;
                case "cursos" -> ARQUIVO_CURSOS;
                default -> throw new IllegalArgumentException("Tipo inválido");
            };

            List<String> dados = GerenciadorDeArquivos.lerDados(arquivo);
            boolean encontrado = false;

            for (int i = 0; i < dados.size(); i++) {
                String linha = dados.get(i);
                if (linha.startsWith(nome + ",")) {
                    encontrado = true;

                    Object[] opcoes = {"Sim", "Não"};
                    int confirm = JOptionPane.showOptionDialog(this, "Deseja excluir este registro?\n" + linha, "Confirmar Exclusão",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[1]);

                    if (confirm == JOptionPane.YES_OPTION) {
                        dados.remove(i);
                        JOptionPane.showMessageDialog(this, "Registro excluído com sucesso!");
                    }
                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "Registro não encontrado.");
                return;
            }

            GerenciadorDeArquivos.sobrescreverDados(arquivo, dados);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + e.getMessage());
        }
    }

    private void gerarRelatorios() {
        String tipo = JOptionPane.showInputDialog(this, "Digite o tipo de relatório (alunos, professores, cursos):");
        if (tipo == null || tipo.isEmpty()) return;

        try {
            String arquivo;
            String relatorio = "";

            switch (tipo.toLowerCase()) {
                case "alunos":
                    arquivo = ARQUIVO_ALUNOS;
                    List<String> dadosAlunos = GerenciadorDeArquivos.lerDados(arquivo);
                    relatorio = Relatorios.gerarRelatorioAlunos(dadosAlunos);
                    break;
                case "professores":
                    arquivo = ARQUIVO_PROFESSORES;
                    List<String> dadosProfessores = GerenciadorDeArquivos.lerDados(arquivo);
                    relatorio = Relatorios.gerarRelatorioProfessores(dadosProfessores);
                    break;
                case "cursos":
                    arquivo = ARQUIVO_CURSOS;
                    List<String> dadosCursos = GerenciadorDeArquivos.lerDados(arquivo);
                    relatorio = Relatorios.gerarRelatorioCursos(dadosCursos);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Tipo de relatório inválido.");
                    return;
            }

            JOptionPane.showMessageDialog(this, relatorio);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório: " + e.getMessage());
        }
    }

    private void verVinculacoes() {
        try {
            List<String> vinculacoes = GerenciadorDeArquivos.lerDados(ARQUIVO_VINCULACOES);
            StringBuilder resultado = new StringBuilder("Vinculações de Alunos e Professores aos Cursos:\n\n");

            if (vinculacoes.isEmpty()) {
                resultado.append("Nenhuma vinculação encontrada.");
            } else {
                for (String linha : vinculacoes) {
                    resultado.append(linha).append("\n");
                }
            }

            JOptionPane.showMessageDialog(this, resultado.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler as vinculações: " + e.getMessage());
        }
    }

    private void gerenciar(String tipo) {
        String[] opcoes;

        if (tipo.equals("cursos")) {
            opcoes = new String[]{"Cadastrar", "Consultar", "Editar", "Excluir", "Matricular Estudantes", "Associar Professores", "Voltar"};
        } else {
            opcoes = new String[]{"Cadastrar", "Consultar", "Editar", "Excluir", "Voltar"};
        }

        int escolha = JOptionPane.showOptionDialog(this, "Escolha uma opção:", "Gerenciar " + tipo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        switch (escolha) {
            case 0 -> cadastrar(tipo);
            case 1 -> consultar(tipo);
            case 2 -> editar(tipo);
            case 3 -> excluir(tipo);
            case 4 -> {
                if (tipo.equals("cursos")) {
                    String estudante = JOptionPane.showInputDialog(this, "Digite o nome do estudante:");
                    String curso = JOptionPane.showInputDialog(this, "Digite o nome do curso:");
                    matricularEstudanteNoCurso(estudante, curso);
                }
            }
            case 5 -> {
                if (tipo.equals("cursos")) {
                    String professor = JOptionPane.showInputDialog(this, "Digite o nome do professor:");
                    String curso = JOptionPane.showInputDialog(this, "Digite o nome do curso:");
                    associarProfessorAoCurso(professor, curso);
                }
            }
        }
    }

    private void cadastrar(String tipo) {
        try {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome:");
            if (nome == null || nome.isEmpty()) return;

            String extraInfo = "";
            String idadeOuCarga = "";

            if (tipo.equals("cursos")) {
                idadeOuCarga = JOptionPane.showInputDialog(this, "Digite a carga horária:");
                if (idadeOuCarga == null || idadeOuCarga.isEmpty()) return;

                extraInfo = JOptionPane.showInputDialog(this, "Digite o nome do professor responsável:");
                if (extraInfo == null || extraInfo.isEmpty()) return;
            } else {
                idadeOuCarga = JOptionPane.showInputDialog(this, tipo.equals("alunos") ? "Digite a idade:" : "Digite a idade do professor:");
                if (idadeOuCarga == null || idadeOuCarga.isEmpty()) return;

                extraInfo = tipo.equals("alunos") ?
                        JOptionPane.showInputDialog(this, "Digite o número da matrícula:") :
                        JOptionPane.showInputDialog(this, "Digite a especialidade:");
                if (extraInfo == null || extraInfo.isEmpty()) return;
            }

            String linha = nome + "," + idadeOuCarga + "," + extraInfo;

            String arquivo = switch (tipo) {
                case "alunos" -> ARQUIVO_ALUNOS;
                case "professores" -> ARQUIVO_PROFESSORES;
                case "cursos" -> ARQUIVO_CURSOS;
                default -> throw new IllegalArgumentException("Tipo inválido");
            };

            GerenciadorDeArquivos.salvarDados(arquivo, linha);

            JOptionPane.showMessageDialog(this, tipo.substring(0, 1).toUpperCase() + tipo.substring(1) + " cadastrado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + e.getMessage());
        }
    }

    private void consultar(String tipo) {
        try {
            String arquivo = switch (tipo) {
                case "alunos" -> ARQUIVO_ALUNOS;
                case "professores" -> ARQUIVO_PROFESSORES;
                case "cursos" -> ARQUIVO_CURSOS;
                default -> throw new IllegalArgumentException("Tipo inválido");
            };

            List<String> dados = GerenciadorDeArquivos.lerDados(arquivo);
            StringBuilder resultado = new StringBuilder();

            for (String dado : dados) {
                resultado.append(dado).append("\n");
            }

            JOptionPane.showMessageDialog(this, resultado.toString());

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar: " + e.getMessage());
        }
    }

    private void matricularEstudanteNoCurso(String estudante, String curso) {
        try (FileWriter writer = new FileWriter(ARQUIVO_VINCULACOES, true)) {
            writer.write("Estudante: " + estudante + " - Curso: " + curso + "\n");
            JOptionPane.showMessageDialog(this, "Estudante matriculado no curso com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao matricular estudante: " + e.getMessage());
        }
    }

    private void associarProfessorAoCurso(String professor, String curso) {
        try (FileWriter writer = new FileWriter(ARQUIVO_VINCULACOES, true)) {
            writer.write("Professor: " + professor + " - Curso: " + curso + "\n");
            JOptionPane.showMessageDialog(this, "Professor associado ao curso com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao associar professor: " + e.getMessage());
        }
    }

    private void sairDoPrograma() {
        Object[] opcoes = {"Sim", "Não"};
        int confirm = JOptionPane.showOptionDialog(this, "Deseja realmente sair?", "Confirmar Saída",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[1]);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GerenciamentoEstudantil janela = new GerenciamentoEstudantil();
            janela.setVisible(true);
        });
    }
}
