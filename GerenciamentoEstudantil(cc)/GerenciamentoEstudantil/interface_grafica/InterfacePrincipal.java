package interface_grafica;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class InterfacePrincipal {

    private static JFrame frame;
    private static JTextArea consoleArea = new JTextArea();

    public static void main(String[] args) {
        mostrarTelaLogin();
    }

    private static void mostrarTelaLogin() {
        frame = new JFrame("Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JTextField usuarioField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JButton loginBtn = new JButton("Entrar");
        JButton registrarBtn = new JButton("Registrar");

        panel.add(new JLabel("Usuário:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        JPanel botoes = new JPanel();
        botoes.add(loginBtn);
        botoes.add(registrarBtn);

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(botoes, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> {
            String user = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            if (GerenciadorDeDados.autenticarUsuario(user, senha)) {
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                frame.dispose();
                mostrarTelaPrincipal();
            } else {
                JOptionPane.showMessageDialog(frame, "Usuário ou senha inválidos.");
            }
        });

        registrarBtn.addActionListener(e -> {
            String user = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            if (user.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
            } else if (GerenciadorDeDados.registrarUsuario(user, senha)) {
                JOptionPane.showMessageDialog(frame, "Usuário registrado com sucesso!");
            }
        });

        frame.setVisible(true);
    }

    private static void mostrarTelaPrincipal() {
        JFrame janela = new JFrame("Gerenciamento Estudantil");
        janela.setSize(900, 600);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();

        abas.add("Alunos", criarPainelAlunos());
        abas.add("Professores", criarPainelProfessores());
        abas.add("Cursos", criarPainelCursos());
        abas.add("Vinculações", criarPainelVinculacoes());

        consoleArea.setEditable(false);
        JScrollPane scrollConsole = new JScrollPane(consoleArea);
        scrollConsole.setPreferredSize(new Dimension(900, 120));

        janela.setLayout(new BorderLayout());
        janela.add(abas, BorderLayout.CENTER);
        janela.add(scrollConsole, BorderLayout.SOUTH);

        janela.setVisible(true);
    }

    private static JPanel criarPainelAlunos() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));

        JTextField nome = new JTextField();
        JTextField idade = new JTextField();
        JTextField matricula = new JTextField();
        JTextField idExcluir = new JTextField();

        JButton cadastrar = new JButton("Cadastrar");
        JButton listar = new JButton("Listar");
        JButton excluir = new JButton("Excluir pelo ID");

        painel.add(new JLabel("Nome:"));
        painel.add(nome);
        painel.add(new JLabel("Idade:"));
        painel.add(idade);
        painel.add(new JLabel("Matrícula:"));
        painel.add(matricula);
        painel.add(cadastrar);
        painel.add(listar);
        painel.add(new JLabel("ID para Excluir:"));
        painel.add(idExcluir);
        painel.add(excluir);

        cadastrar.addActionListener(e -> {
            try {
                GerenciadorDeDados.cadastrarEstudante(nome.getText(), Integer.parseInt(idade.getText()), matricula.getText());
                consoleArea.append("Aluno cadastrado: " + nome.getText() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao cadastrar aluno.");
            }
        });

        listar.addActionListener(e -> consoleArea.append(GerenciadorDeDados.listarEstudantesRetorno()));

        excluir.addActionListener(e -> {
            try {
                GerenciadorDeDados.excluirEstudante(Integer.parseInt(idExcluir.getText()));
                consoleArea.append("Aluno excluído!\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao excluir aluno.");
            }
        });

        return painel;
    }

    private static JPanel criarPainelProfessores() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));

        JTextField nome = new JTextField();
        JTextField idade = new JTextField();
        JTextField especialidade = new JTextField();
        JTextField idExcluir = new JTextField();

        JButton cadastrar = new JButton("Cadastrar");
        JButton listar = new JButton("Listar");
        JButton excluir = new JButton("Excluir pelo ID");

        painel.add(new JLabel("Nome:"));
        painel.add(nome);
        painel.add(new JLabel("Idade:"));
        painel.add(idade);
        painel.add(new JLabel("Especialidade:"));
        painel.add(especialidade);
        painel.add(cadastrar);
        painel.add(listar);
        painel.add(new JLabel("ID para Excluir:"));
        painel.add(idExcluir);
        painel.add(excluir);

        cadastrar.addActionListener(e -> {
            try {
                GerenciadorDeDados.cadastrarProfessor(nome.getText(), Integer.parseInt(idade.getText()), especialidade.getText());
                consoleArea.append("Professor cadastrado: " + nome.getText() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao cadastrar professor.");
            }
        });

        listar.addActionListener(e -> consoleArea.append(GerenciadorDeDados.listarProfessoresRetorno()));

        excluir.addActionListener(e -> {
            try {
                GerenciadorDeDados.excluirProfessor(Integer.parseInt(idExcluir.getText()));
                consoleArea.append("Professor excluído!\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao excluir professor.");
            }
        });

        return painel;
    }

    private static JPanel criarPainelCursos() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));

        JTextField nomeCurso = new JTextField();
        JTextField cargaHoraria = new JTextField();
        JTextField profResponsavel = new JTextField();
        JTextField idExcluir = new JTextField();

        JButton cadastrar = new JButton("Cadastrar");
        JButton listar = new JButton("Listar");
        JButton excluir = new JButton("Excluir pelo ID");

        painel.add(new JLabel("Nome do Curso:"));
        painel.add(nomeCurso);
        painel.add(new JLabel("Carga Horária:"));
        painel.add(cargaHoraria);
        painel.add(new JLabel("Professor Responsável:"));
        painel.add(profResponsavel);
        painel.add(cadastrar);
        painel.add(listar);
        painel.add(new JLabel("ID para Excluir:"));
        painel.add(idExcluir);
        painel.add(excluir);

        cadastrar.addActionListener(e -> {
            try {
                GerenciadorDeDados.cadastrarCurso(nomeCurso.getText(), Integer.parseInt(cargaHoraria.getText()), profResponsavel.getText());
                consoleArea.append("Curso cadastrado: " + nomeCurso.getText() + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao cadastrar curso.");
            }
        });

        listar.addActionListener(e -> consoleArea.append(GerenciadorDeDados.listarCursosRetorno()));

        excluir.addActionListener(e -> {
            try {
                GerenciadorDeDados.excluirCurso(Integer.parseInt(idExcluir.getText()));
                consoleArea.append("Curso excluído!\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao excluir curso.");
            }
        });

        return painel;
    }

    private static JPanel criarPainelVinculacoes() {
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));

        JComboBox<String> alunoBox = new JComboBox<>();
        JComboBox<String> cursoBox = new JComboBox<>();
        JComboBox<String> professorBox = new JComboBox<>();
        JTextField idExcluir = new JTextField();

        preencherCombo(alunoBox, "alunos");
        preencherCombo(cursoBox, "cursos");
        preencherCombo(professorBox, "professores");

        JButton cadastrar = new JButton("Cadastrar");
        JButton listar = new JButton("Listar");
        JButton excluir = new JButton("Excluir pelo ID");

        painel.add(new JLabel("Aluno:"));
        painel.add(alunoBox);
        painel.add(new JLabel("Curso:"));
        painel.add(cursoBox);
        painel.add(new JLabel("Professor:"));
        painel.add(professorBox);
        painel.add(cadastrar);
        painel.add(listar);
        painel.add(new JLabel("ID para Excluir:"));
        painel.add(idExcluir);
        painel.add(excluir);

        cadastrar.addActionListener(e -> {
            try {
                int alunoId = Integer.parseInt(alunoBox.getSelectedItem().toString().split(" - ")[0]);
                int cursoId = Integer.parseInt(cursoBox.getSelectedItem().toString().split(" - ")[0]);
                int professorId = Integer.parseInt(professorBox.getSelectedItem().toString().split(" - ")[0]);

                GerenciadorDeDados.cadastrarVinculacao(alunoId, cursoId, professorId);
                consoleArea.append("Vinculação cadastrada!\n");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao cadastrar vinculação.");
            }
        });

        listar.addActionListener(e -> consoleArea.append(GerenciadorDeDados.listarVinculacoesRetorno()));

        excluir.addActionListener(e -> {
            try {
                GerenciadorDeDados.excluirVinculacao(Integer.parseInt(idExcluir.getText()));
                consoleArea.append("Vinculação excluída!\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro ao excluir vinculação.");
            }
        });

        return painel;
    }

    private static void preencherCombo(JComboBox<String> combo, String tabela) {
        combo.removeAllItems();
        String nomeColuna = tabela.equals("cursos") ? "nome_curso" : "nome";
        String sql = "SELECT id, " + nomeColuna + " FROM " + tabela;

        try (Connection conn = ConexaoMySQL.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                combo.addItem(rs.getInt("id") + " - " + rs.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
