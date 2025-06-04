package interface_grafica;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class InterfacePrincipal {

    private static JFrame frame;
    private static JFrame telaPrincipalFrame;
    private static JTextArea consoleArea = new JTextArea();

    private static int idAlunoEmEdicao = -1;
    private static int idProfessorEmEdicao = -1;
    private static int idCursoEmEdicao = -1;

    // Paleta de Cores ACADEMUS
    private static final Color ACADEMUS_MAIN_PURPLE = new Color(85, 26, 139);
    private static final Color ACADEMUS_LIGHT_PURPLE = new Color(153, 102, 204);
    private static final Color ACADEMUS_BACKGROUND = new Color(245, 240, 255);
    private static final Color ACADEMUS_TEXT_ON_PURPLE = Color.WHITE;
    private static final Color ACADEMUS_TEXT_ON_LIGHT_BG = new Color(50, 50, 50);
    private static final Color ACADEMUS_CONSOLE_BACKGROUND = new Color(230, 230, 230);
    private static final Color ACADEMUS_CONSOLE_TEXT = Color.BLACK;
    private static final Color ACADEMUS_BORDER_COLOR = ACADEMUS_LIGHT_PURPLE.darker();

    // Fontes ACADEMUS
    private static final Font FONT_GENERAL = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_CONSOLE = new Font("Monospaced", Font.PLAIN, 12);
    private static final Font FONT_TITLE_FRAME = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_TAB_TITLE = new Font("Segoe UI", Font.BOLD, 14);


    public static void main(String[] args) {
        try {
            boolean nimbusFound = false;
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusFound = true;
                    break;
                }
            }
            if (!nimbusFound) {
                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            System.err.println("Não foi possível definir o Look and Feel. Usando padrão.");
        }
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        UIManager.put("OptionPane.cancelButtonText", "Cancelar");
        UIManager.put("OptionPane.okButtonText", "OK");
        
        mostrarTelaLogin();
    }

    private static void aplicarEstiloBotao(JButton button) {
        button.setFont(FONT_BUTTON);
        button.setBackground(ACADEMUS_LIGHT_PURPLE);
        button.setForeground(ACADEMUS_TEXT_ON_PURPLE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACADEMUS_MAIN_PURPLE, 1),
            new EmptyBorder(8, 18, 8, 18)
        ));
    }
    
    private static void aplicarEstiloTextField(JTextField textField) {
        textField.setFont(FONT_GENERAL);
        textField.setBackground(Color.WHITE);
        textField.setForeground(ACADEMUS_TEXT_ON_LIGHT_BG);
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACADEMUS_LIGHT_PURPLE, 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        textField.setCaretColor(ACADEMUS_MAIN_PURPLE);
    }

    private static void aplicarEstiloTextField(JPasswordField passwordField) {
        passwordField.setFont(FONT_GENERAL);
        passwordField.setBackground(Color.WHITE);
        passwordField.setForeground(ACADEMUS_TEXT_ON_LIGHT_BG);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACADEMUS_LIGHT_PURPLE, 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        passwordField.setCaretColor(ACADEMUS_MAIN_PURPLE);
    }

    private static void aplicarEstiloLabel(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(ACADEMUS_MAIN_PURPLE);
    }

    private static void aplicarEstiloComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(FONT_GENERAL);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(ACADEMUS_TEXT_ON_LIGHT_BG);
    }

    private static boolean mostrarDialogoConfirmacao(Component parent, String mensagem, String titulo) {
        Object[] options = {"Sim", "Não"};
        int resposta = JOptionPane.showOptionDialog(
            parent,
            mensagem,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,    
            options, 
            options[0]
        );
        return resposta == JOptionPane.YES_OPTION;
    }

    private static void mostrarTelaLogin() {
        if (telaPrincipalFrame != null && telaPrincipalFrame.isVisible()) {
            telaPrincipalFrame.dispose();
        }

        frame = new JFrame("ACADEMUS - Autenticação");
        frame.setName("loginFrame"); // <<< NOME ADICIONADO
        frame.setSize(400, 280);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(ACADEMUS_BACKGROUND);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(ACADEMUS_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("ACADEMUS Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(ACADEMUS_MAIN_PURPLE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 25, 5);
        panel.add(titleLabel, gbc);
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        JLabel usuarioLabel = new JLabel("Usuário:");
        aplicarEstiloLabel(usuarioLabel);
        JTextField usuarioField = new JTextField(15);
        aplicarEstiloTextField(usuarioField);
        usuarioField.setName("loginUsuarioField"); // <<< NOME ADICIONADO

        JLabel senhaLabel = new JLabel("Senha:");
        aplicarEstiloLabel(senhaLabel);
        JPasswordField senhaField = new JPasswordField(15);
        aplicarEstiloTextField(senhaField);
        senhaField.setName("loginSenhaField"); // <<< NOME ADICIONADO

        JButton loginBtn = new JButton("Entrar");
        aplicarEstiloBotao(loginBtn);
        loginBtn.setName("loginEntrarBtn"); // <<< NOME ADICIONADO

        JButton registrarBtn = new JButton("Registrar");
        aplicarEstiloBotao(registrarBtn);
        registrarBtn.setName("loginRegistrarBtn"); // <<< NOME ADICIONADO

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END; panel.add(usuarioLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START; panel.add(usuarioField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END; panel.add(senhaLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_START; panel.add(senhaField, gbc);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        botoesPanel.setBackground(ACADEMUS_BACKGROUND);
        botoesPanel.add(loginBtn);
        botoesPanel.add(registrarBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(botoesPanel, gbc);

        frame.add(panel);
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String user = usuarioField.getText();
            String senha = new String(senhaField.getPassword());
            if (GerenciadorDeDados.autenticarUsuario(user, senha)) { //
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                mostrarTelaPrincipal();
            } else {
                JOptionPane.showMessageDialog(frame, "Usuário ou senha inválidos.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            }
        });
        registrarBtn.addActionListener(e -> mostrarTelaRegistro());
    }

    private static void mostrarTelaRegistro() {
        JFrame registroDialogFrame = new JFrame("ACADEMUS - Registrar Novo Usuário");
        registroDialogFrame.setName("registroFrame"); // <<< NOME ADICIONADO
        registroDialogFrame.setSize(450, 330);
        registroDialogFrame.setLocationRelativeTo(frame);
        registroDialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registroDialogFrame.getContentPane().setBackground(ACADEMUS_BACKGROUND);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(ACADEMUS_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Registro de Novo Usuário", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(ACADEMUS_MAIN_PURPLE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 25, 5);
        panel.add(titleLabel, gbc);
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.gridwidth = 1; gbc.weightx = 0.0;

        JLabel usuarioLabel = new JLabel("Novo Usuário:");
        aplicarEstiloLabel(usuarioLabel);
        JTextField usuarioField = new JTextField(15);
        aplicarEstiloTextField(usuarioField);
        usuarioField.setName("registroUsuarioField"); // <<< NOME ADICIONADO

        JLabel senhaLabel = new JLabel("Senha:");
        aplicarEstiloLabel(senhaLabel);
        JPasswordField senhaField = new JPasswordField(15);
        aplicarEstiloTextField(senhaField);
        senhaField.setName("registroSenhaField"); // <<< NOME ADICIONADO

        JLabel confirmarSenhaLabel = new JLabel("Confirmar Senha:");
        aplicarEstiloLabel(confirmarSenhaLabel);
        JPasswordField confirmarSenhaField = new JPasswordField(15);
        aplicarEstiloTextField(confirmarSenhaField);
        confirmarSenhaField.setName("registroConfirmarSenhaField"); // <<< NOME ADICIONADO

        JButton registrarBtn = new JButton("Confirmar Registro");
        aplicarEstiloBotao(registrarBtn);
        registrarBtn.setName("registroConfirmarBtn"); // <<< NOME ADICIONADO

        JButton cancelarBtn = new JButton("Cancelar");
        aplicarEstiloBotao(cancelarBtn);
        cancelarBtn.setName("registroCancelarBtn"); // <<< NOME ADICIONADO

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END; panel.add(usuarioLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START; panel.add(usuarioField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END; panel.add(senhaLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_START; panel.add(senhaField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END; panel.add(confirmarSenhaLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_START; panel.add(confirmarSenhaField, gbc);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        botoesPanel.setBackground(ACADEMUS_BACKGROUND);
        botoesPanel.add(registrarBtn);
        botoesPanel.add(cancelarBtn);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        panel.add(botoesPanel, gbc);
        
        registroDialogFrame.add(panel);
        registroDialogFrame.setVisible(true);

        registrarBtn.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());
            String confirmarSenha = new String(confirmarSenhaField.getPassword());

            if (usuario.isBlank() || senha.isBlank() || confirmarSenha.isBlank()) {
                JOptionPane.showMessageDialog(registroDialogFrame, "Todos os campos devem ser preenchidos.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!senha.equals(confirmarSenha)) {
                JOptionPane.showMessageDialog(registroDialogFrame, "As senhas digitadas não coincidem.", "Erro de Senha", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_.,#\\^()-]).{8,}$")) {
                JOptionPane.showMessageDialog(registroDialogFrame,
                        "A senha deve ter no mínimo 8 caracteres e conter:\n- Pelo menos uma letra maiúscula (A-Z)\n- Pelo menos uma letra minúscula (a-z)\n- Pelo menos um número (0-9)\n- Pelo menos um caractere especial (ex: @$!%*?&_.,#^()-)",
                        "Requisitos de Senha", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (GerenciadorDeDados.registrarUsuario(usuario, senha)) { //
                JOptionPane.showMessageDialog(registroDialogFrame, "Usuário '" + usuario + "' registrado com sucesso!", "Registro Concluído", JOptionPane.INFORMATION_MESSAGE);
                registroDialogFrame.dispose();
            }
        });
        cancelarBtn.addActionListener(e -> registroDialogFrame.dispose());
    }

    private static void mostrarTelaPrincipal() {
        telaPrincipalFrame = new JFrame("ACADEMUS - Sistema de Gerenciamento Estudantil");
        telaPrincipalFrame.setName("telaPrincipalFrame"); // <<< NOME ADICIONADO
        telaPrincipalFrame.setSize(1024, 768);
        telaPrincipalFrame.setMinimumSize(new Dimension(980, 720));
        telaPrincipalFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaPrincipalFrame.setLocationRelativeTo(null);

        JTabbedPane abas = new JTabbedPane();
        abas.setName("abasPrincipais"); // <<< NOME ADICIONADO
        abas.setFont(FONT_TAB_TITLE);
      
        abas.addTab("Alunos", criarPainelAlunos());
        abas.addTab("Professores", criarPainelProfessores());
        abas.addTab("Cursos", criarPainelCursos());
        abas.addTab("Vinculações", criarPainelVinculacoes());
        
        for (int i = 0; i < abas.getTabCount(); i++) {
            abas.setBackgroundAt(i, ACADEMUS_LIGHT_PURPLE.brighter());
            abas.setForegroundAt(i, ACADEMUS_MAIN_PURPLE);
        }

        consoleArea.setFont(FONT_CONSOLE);
        consoleArea.setEditable(false);
        consoleArea.setBackground(ACADEMUS_CONSOLE_BACKGROUND);
        consoleArea.setForeground(ACADEMUS_CONSOLE_TEXT);
        consoleArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACADEMUS_BORDER_COLOR, 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        consoleArea.setName("consoleArea"); // <<< NOME ADICIONADO

        JScrollPane scrollConsole = new JScrollPane(consoleArea);
        scrollConsole.setPreferredSize(new Dimension(950, 180));
        scrollConsole.setBorder(null);

        JButton limparConsoleBtn = new JButton("Limpar Console");
        aplicarEstiloBotao(limparConsoleBtn);
        limparConsoleBtn.setName("limparConsoleBtn"); // <<< NOME ADICIONADO
        limparConsoleBtn.addActionListener(e -> {
            consoleArea.setText("");
            consoleArea.append("Console limpo pelo usuário.\n");
        });

        JButton sairBtn = new JButton("Sair (Logout)");
        sairBtn.setFont(FONT_BUTTON);
        sairBtn.setBackground(ACADEMUS_MAIN_PURPLE.darker());
        sairBtn.setForeground(ACADEMUS_TEXT_ON_PURPLE);
        sairBtn.setFocusPainted(false);
        sairBtn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(ACADEMUS_MAIN_PURPLE.darker().darker(), 1),
            new EmptyBorder(8, 18, 8, 18)
        ));
        sairBtn.setName("sairLogoutBtn"); // <<< NOME ADICIONADO


        sairBtn.addActionListener(e -> {
            if (mostrarDialogoConfirmacao(telaPrincipalFrame, "Deseja realmente sair e voltar para a tela de login?", "Confirmar Saída")) {
                telaPrincipalFrame.dispose();
                mostrarTelaLogin();
            }
        });

        JPanel bottomButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomButtonsPanel.setBackground(ACADEMUS_BACKGROUND);
        bottomButtonsPanel.add(limparConsoleBtn);
        bottomButtonsPanel.add(sairBtn);

        JPanel southPanel = new JPanel(new BorderLayout(0, 8));
        southPanel.setBackground(ACADEMUS_BACKGROUND);
        southPanel.add(scrollConsole, BorderLayout.CENTER);
        southPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout(0,10));
        mainPanel.setBackground(ACADEMUS_BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));
        mainPanel.add(abas, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        telaPrincipalFrame.add(mainPanel);
        telaPrincipalFrame.setVisible(true);
    }

    private static JPanel criarPainelBase(String tituloDaAba) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(ACADEMUS_BACKGROUND);
        
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
            new LineBorder(ACADEMUS_MAIN_PURPLE, 1, true),
            tituloDaAba, 
            TitledBorder.LEFT,
            TitledBorder.TOP,     
            FONT_TAB_TITLE,
            ACADEMUS_MAIN_PURPLE
        );
        painel.setBorder(BorderFactory.createCompoundBorder(
            titledBorder,
            new EmptyBorder(15, 20, 20, 20)
        ));
        return painel;
    }
    
    private static void adicionarCampoComLabel(JPanel painel, GridBagConstraints gbc, JLabel label, Component campo, int yPos) {
        gbc.gridx = 0; gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.2; 
        painel.add(label, gbc);

        gbc.gridx = 1; gbc.gridy = yPos;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8; 
        painel.add(campo, gbc);
    }


    // --- PAINEL ALUNOS ---
    private static JPanel criarPainelAlunos() {
        JPanel painel = criarPainelBase("Alunos");
        painel.setName("painelAlunos"); // <<< NOME ADICIONADO
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel nomeLabel = new JLabel("Nome Completo:"); aplicarEstiloLabel(nomeLabel);
        JTextField nomeField = new JTextField(25); aplicarEstiloTextField(nomeField);
        nomeField.setName("alunoNomeField"); // <<< NOME ADICIONADO

        JLabel idadeLabel = new JLabel("Idade:"); aplicarEstiloLabel(idadeLabel);
        JTextField idadeField = new JTextField(10); aplicarEstiloTextField(idadeField);
        idadeField.setName("alunoIdadeField"); // <<< NOME ADICIONADO

        JLabel matriculaLabel = new JLabel("Nº Matrícula:"); aplicarEstiloLabel(matriculaLabel);
        JTextField matriculaField = new JTextField(15); aplicarEstiloTextField(matriculaField);
        matriculaField.setName("alunoMatriculaField"); // <<< NOME ADICIONADO

        int y = 0;
        adicionarCampoComLabel(painel, gbc, nomeLabel, nomeField, y++);
        adicionarCampoComLabel(painel, gbc, idadeLabel, idadeField, y++);
        adicionarCampoComLabel(painel, gbc, matriculaLabel, matriculaField, y++);
        
        JButton cadastrarBtn = new JButton("Cadastrar Novo Aluno"); aplicarEstiloBotao(cadastrarBtn);
        cadastrarBtn.setName("alunoCadastrarBtn"); // <<< NOME ADICIONADO

        JButton listarBtn = new JButton("Listar Todos os Alunos"); aplicarEstiloBotao(listarBtn);
        listarBtn.setName("alunoListarBtn"); // <<< NOME ADICIONADO
        
        JPanel cadastroListarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        cadastroListarPanel.setBackground(ACADEMUS_BACKGROUND);
        cadastroListarPanel.add(cadastrarBtn); cadastroListarPanel.add(listarBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(cadastroListarPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel idAlunoEditarLabel = new JLabel("ID Aluno para Editar:"); aplicarEstiloLabel(idAlunoEditarLabel);
        JTextField idAlunoEditarField = new JTextField(10); aplicarEstiloTextField(idAlunoEditarField);
        idAlunoEditarField.setName("alunoIdEditarField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idAlunoEditarLabel, idAlunoEditarField, y++);
        
        JButton carregarAlunoBtn = new JButton("Carregar Dados"); aplicarEstiloBotao(carregarAlunoBtn);
        carregarAlunoBtn.setName("alunoCarregarBtn"); // <<< NOME ADICIONADO

        JButton salvarAlunoBtn = new JButton("Salvar Modificações"); aplicarEstiloBotao(salvarAlunoBtn);
        salvarAlunoBtn.setName("alunoSalvarBtn"); // <<< NOME ADICIONADO
        salvarAlunoBtn.setEnabled(false);
        JPanel edicaoBotoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        edicaoBotoesPanel.setBackground(ACADEMUS_BACKGROUND);
        edicaoBotoesPanel.add(carregarAlunoBtn); edicaoBotoesPanel.add(salvarAlunoBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(edicaoBotoesPanel, gbc);

        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);

        JLabel idExcluirLabel = new JLabel("ID Aluno para Excluir:"); aplicarEstiloLabel(idExcluirLabel);
        JTextField idExcluirField = new JTextField(10); aplicarEstiloTextField(idExcluirField);
        idExcluirField.setName("alunoIdExcluirField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idExcluirLabel, idExcluirField, y++);
        
        JButton excluirBtn = new JButton("Excluir Aluno por ID"); aplicarEstiloBotao(excluirBtn);
        excluirBtn.setName("alunoExcluirBtn"); // <<< NOME ADICIONADO
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(excluirBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.weighty = 1.0; gbc.gridwidth = 2; painel.add(new JLabel(""), gbc);

        cadastrarBtn.addActionListener(e -> {
            try { String nome = nomeField.getText(); String idadeStr = idadeField.getText(); String matricula = matriculaField.getText();
                if (nome.isBlank() || idadeStr.isBlank() || matricula.isBlank()) { JOptionPane.showMessageDialog(painel, "Todos os campos (Nome, Idade, Matrícula) são obrigatórios para o cadastro.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE); return; }
                int idade = Integer.parseInt(idadeStr);
                GerenciadorDeDados.cadastrarEstudante(nome, idade, matricula);
                consoleArea.append("Aluno '" + nome + "' cadastrado com sucesso.\n");
                nomeField.setText(""); idadeField.setText(""); matriculaField.setText(""); idAlunoEditarField.setText(""); idAlunoEmEdicao = -1; salvarAlunoBtn.setEnabled(false);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O campo 'Idade' deve ser um número válido.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Ocorreu um erro ao cadastrar o aluno: " + ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        listarBtn.addActionListener(e -> { consoleArea.setText("--- Lista de Alunos ---\n" + GerenciadorDeDados.listarEstudantesRetorno() + "-----------------------\n"); });
        carregarAlunoBtn.addActionListener(e -> {
            try { if (idAlunoEditarField.getText().isBlank()) { JOptionPane.showMessageDialog(painel, "Por favor, insira o ID do aluno para carregar os dados.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                int idParaEditar = Integer.parseInt(idAlunoEditarField.getText()); String[] dadosAluno = GerenciadorDeDados.getEstudanteParaEdicao(idParaEditar);
                if (dadosAluno != null) { nomeField.setText(dadosAluno[0]); idadeField.setText(dadosAluno[1]); matriculaField.setText(dadosAluno[2]); idAlunoEmEdicao = idParaEditar; salvarAlunoBtn.setEnabled(true); consoleArea.append("Dados do aluno (ID: " + idParaEditar + ") carregados para edição.\n");
                } else { JOptionPane.showMessageDialog(painel, "Aluno com ID " + idParaEditar + " não foi encontrado.", "Aluno Inexistente", JOptionPane.WARNING_MESSAGE); nomeField.setText(""); idadeField.setText(""); matriculaField.setText(""); idAlunoEmEdicao = -1; salvarAlunoBtn.setEnabled(false); }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O ID do aluno para editar deve ser um número válido.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE); idAlunoEmEdicao = -1; salvarAlunoBtn.setEnabled(false); }
        });
        salvarAlunoBtn.addActionListener(e -> {
            if (idAlunoEmEdicao == -1) { JOptionPane.showMessageDialog(painel, "Nenhum aluno foi carregado para edição. Utilize 'Carregar Dados' primeiro.", "Operação Inválida", JOptionPane.WARNING_MESSAGE); return; }
            try { String nome = nomeField.getText(); String idadeStr = idadeField.getText(); String matricula = matriculaField.getText();
                if (nome.isBlank() || idadeStr.isBlank() || matricula.isBlank()) { JOptionPane.showMessageDialog(painel, "Todos os campos (Nome, Idade, Matrícula) são obrigatórios para salvar as alterações.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE); return; }
                int idade = Integer.parseInt(idadeStr);
                GerenciadorDeDados.editarEstudante(idAlunoEmEdicao, nome, idade, matricula);
                consoleArea.append("Aluno (ID: " + idAlunoEmEdicao + ") atualizado com sucesso.\n");
                nomeField.setText(""); idadeField.setText(""); matriculaField.setText(""); idAlunoEditarField.setText(""); idAlunoEmEdicao = -1; salvarAlunoBtn.setEnabled(false);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O campo 'Idade' deve ser um número válido.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Ocorreu um erro ao salvar as alterações: " + ex.getMessage(), "Erro ao Salvar", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        excluirBtn.addActionListener(e -> { 
            try { if (idExcluirField.getText().isBlank()) { JOptionPane.showMessageDialog(painel, "Por favor, insira o ID do aluno que deseja excluir.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                int idParaExcluir = Integer.parseInt(idExcluirField.getText());
                if (mostrarDialogoConfirmacao(painel, "Tem certeza que deseja excluir o aluno com ID " + idParaExcluir + "?\nEsta ação não pode ser desfeita.", "Confirmar Exclusão de Aluno")) {
                    GerenciadorDeDados.excluirEstudante(idParaExcluir);
                    consoleArea.append("Aluno com ID " + idParaExcluir + " foi excluído.\n"); idExcluirField.setText("");
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O ID do aluno para excluir deve ser um número válido.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Ocorreu um erro ao excluir o aluno: " + ex.getMessage(), "Erro na Exclusão", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        return painel;
    }

    // --- PAINEL PROFESSORES ---
    private static JPanel criarPainelProfessores() {
        JPanel painel = criarPainelBase("Professores");
        painel.setName("painelProfessores"); // <<< NOME ADICIONADO
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel nomeLabel = new JLabel("Nome Completo:"); aplicarEstiloLabel(nomeLabel);
        JTextField nomeField = new JTextField(25); aplicarEstiloTextField(nomeField);
        nomeField.setName("profNomeField"); // <<< NOME ADICIONADO

        JLabel idadeLabel = new JLabel("Idade:"); aplicarEstiloLabel(idadeLabel);
        JTextField idadeField = new JTextField(10); aplicarEstiloTextField(idadeField);
        idadeField.setName("profIdadeField"); // <<< NOME ADICIONADO

        JLabel especialidadeLabel = new JLabel("Especialidade:"); aplicarEstiloLabel(especialidadeLabel);
        JTextField especialidadeField = new JTextField(20); aplicarEstiloTextField(especialidadeField);
        especialidadeField.setName("profEspecialidadeField"); // <<< NOME ADICIONADO

        int y = 0;
        adicionarCampoComLabel(painel, gbc, nomeLabel, nomeField, y++);
        adicionarCampoComLabel(painel, gbc, idadeLabel, idadeField, y++);
        adicionarCampoComLabel(painel, gbc, especialidadeLabel, especialidadeField, y++);
        
        JButton cadastrarBtn = new JButton("Cadastrar Novo Professor"); aplicarEstiloBotao(cadastrarBtn);
        cadastrarBtn.setName("profCadastrarBtn"); // <<< NOME ADICIONADO
        JButton listarBtn = new JButton("Listar Todos os Professores"); aplicarEstiloBotao(listarBtn);
        listarBtn.setName("profListarBtn"); // <<< NOME ADICIONADO
        JPanel cadastroListarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        cadastroListarPanel.setBackground(ACADEMUS_BACKGROUND);
        cadastroListarPanel.add(cadastrarBtn); cadastroListarPanel.add(listarBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(cadastroListarPanel, gbc);

        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel idEditarLabel = new JLabel("ID Professor para Editar:"); aplicarEstiloLabel(idEditarLabel);
        JTextField idEditarField = new JTextField(10); aplicarEstiloTextField(idEditarField);
        idEditarField.setName("profIdEditarField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idEditarLabel, idEditarField, y++);
        
        JButton carregarBtn = new JButton("Carregar Dados"); aplicarEstiloBotao(carregarBtn);
        carregarBtn.setName("profCarregarBtn"); // <<< NOME ADICIONADO
        JButton salvarBtn = new JButton("Salvar Modificações"); aplicarEstiloBotao(salvarBtn);
        salvarBtn.setName("profSalvarBtn"); // <<< NOME ADICIONADO
        salvarBtn.setEnabled(false);
        JPanel edicaoBotoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        edicaoBotoesPanel.setBackground(ACADEMUS_BACKGROUND);
        edicaoBotoesPanel.add(carregarBtn); edicaoBotoesPanel.add(salvarBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(edicaoBotoesPanel, gbc);

        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);

        JLabel idExcluirLabel = new JLabel("ID Professor para Excluir:"); aplicarEstiloLabel(idExcluirLabel);
        JTextField idExcluirField = new JTextField(10); aplicarEstiloTextField(idExcluirField);
        idExcluirField.setName("profIdExcluirField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idExcluirLabel, idExcluirField, y++);
        
        JButton excluirBtn = new JButton("Excluir Professor por ID"); aplicarEstiloBotao(excluirBtn);
        excluirBtn.setName("profExcluirBtn"); // <<< NOME ADICIONADO
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(excluirBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.weighty = 1.0; gbc.gridwidth = 2; painel.add(new JLabel(""), gbc);

        cadastrarBtn.addActionListener(e -> {
            try { String nome = nomeField.getText(); String idadeStr = idadeField.getText(); String especialidade = especialidadeField.getText();
                if (nome.isBlank() || idadeStr.isBlank() || especialidade.isBlank()) { JOptionPane.showMessageDialog(painel, "Todos os campos são obrigatórios.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE); return; }
                int idade = Integer.parseInt(idadeStr);
                GerenciadorDeDados.cadastrarProfessor(nome, idade, especialidade);
                consoleArea.append("Professor '" + nome + "' cadastrado com sucesso.\n");
                nomeField.setText(""); idadeField.setText(""); especialidadeField.setText(""); idEditarField.setText(""); idProfessorEmEdicao = -1; salvarBtn.setEnabled(false);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O campo 'Idade' deve ser um número válido.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Erro ao cadastrar professor: " + ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        listarBtn.addActionListener(e -> { consoleArea.setText("--- Lista de Professores ---\n" + GerenciadorDeDados.listarProfessoresRetorno() + "---------------------------\n"); });
        carregarBtn.addActionListener(e -> {
            try { if (idEditarField.getText().isBlank()) { JOptionPane.showMessageDialog(painel, "Insira o ID do professor para carregar.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                int idParaEditar = Integer.parseInt(idEditarField.getText()); String[] dados = GerenciadorDeDados.getProfessorParaEdicao(idParaEditar);
                if (dados != null) { nomeField.setText(dados[0]); idadeField.setText(dados[1]); especialidadeField.setText(dados[2]); idProfessorEmEdicao = idParaEditar; salvarBtn.setEnabled(true); consoleArea.append("Dados do professor (ID: " + idParaEditar + ") carregados.\n");
                } else { JOptionPane.showMessageDialog(painel, "Professor com ID " + idParaEditar + " não encontrado.", "Professor Inexistente", JOptionPane.WARNING_MESSAGE); nomeField.setText(""); idadeField.setText(""); especialidadeField.setText(""); idProfessorEmEdicao = -1; salvarBtn.setEnabled(false); }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O ID do professor deve ser um número.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE); idProfessorEmEdicao = -1; salvarBtn.setEnabled(false); }
        });
        salvarBtn.addActionListener(e -> {
            if (idProfessorEmEdicao == -1) { JOptionPane.showMessageDialog(painel, "Nenhum professor carregado para edição.", "Operação Inválida", JOptionPane.WARNING_MESSAGE); return; }
            try { String nome = nomeField.getText(); String idadeStr = idadeField.getText(); String especialidade = especialidadeField.getText();
                if (nome.isBlank() || idadeStr.isBlank() || especialidade.isBlank()) { JOptionPane.showMessageDialog(painel, "Todos os campos são obrigatórios.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE); return; }
                int idade = Integer.parseInt(idadeStr);
                GerenciadorDeDados.editarProfessor(idProfessorEmEdicao, nome, idade, especialidade);
                consoleArea.append("Professor (ID: " + idProfessorEmEdicao + ") atualizado.\n");
                nomeField.setText(""); idadeField.setText(""); especialidadeField.setText(""); idEditarField.setText(""); idProfessorEmEdicao = -1; salvarBtn.setEnabled(false);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "Idade deve ser um número.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Erro ao salvar professor: " + ex.getMessage(), "Erro ao Salvar", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        excluirBtn.addActionListener(e -> {
            try { if (idExcluirField.getText().isBlank()) { JOptionPane.showMessageDialog(painel, "Insira o ID do professor para excluir.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                int idParaExcluir = Integer.parseInt(idExcluirField.getText());
                if (mostrarDialogoConfirmacao(painel, "Tem certeza que deseja excluir o professor com ID " + idParaExcluir + "?", "Confirmar Exclusão de Professor")) {
                     GerenciadorDeDados.excluirProfessor(idParaExcluir);
                     consoleArea.append("Professor (ID: " + idParaExcluir + ") excluído.\n"); idExcluirField.setText("");
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "ID do professor deve ser um número.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Erro ao excluir professor: " + ex.getMessage(), "Erro na Exclusão", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        return painel;
    }

    // --- PAINEL CURSOS ---
    private static JPanel criarPainelCursos() {
        JPanel painel = criarPainelBase("Cursos");
        painel.setName("painelCursos"); // <<< NOME ADICIONADO
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel nomeLabel = new JLabel("Nome do Curso:"); aplicarEstiloLabel(nomeLabel);
        JTextField nomeField = new JTextField(25); aplicarEstiloTextField(nomeField);
        nomeField.setName("cursoNomeField"); // <<< NOME ADICIONADO

        JLabel cargaHorariaLabel = new JLabel("Carga Horária (h):"); aplicarEstiloLabel(cargaHorariaLabel);
        JTextField cargaHorariaField = new JTextField(10); aplicarEstiloTextField(cargaHorariaField);
        cargaHorariaField.setName("cursoCargaHorariaField"); // <<< NOME ADICIONADO

        JLabel profRespLabel = new JLabel("Prof. Responsável:"); aplicarEstiloLabel(profRespLabel);
        JTextField profRespField = new JTextField(25); aplicarEstiloTextField(profRespField);
        profRespField.setName("cursoProfRespField"); // <<< NOME ADICIONADO

        int y = 0;
        adicionarCampoComLabel(painel, gbc, nomeLabel, nomeField, y++);
        adicionarCampoComLabel(painel, gbc, cargaHorariaLabel, cargaHorariaField, y++);
        adicionarCampoComLabel(painel, gbc, profRespLabel, profRespField, y++);
        
        JButton cadastrarBtn = new JButton("Cadastrar Novo Curso"); aplicarEstiloBotao(cadastrarBtn);
        cadastrarBtn.setName("cursoCadastrarBtn"); // <<< NOME ADICIONADO
        JButton listarBtn = new JButton("Listar Todos os Cursos"); aplicarEstiloBotao(listarBtn);
        listarBtn.setName("cursoListarBtn"); // <<< NOME ADICIONADO
        JPanel cadastroListarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        cadastroListarPanel.setBackground(ACADEMUS_BACKGROUND);
        cadastroListarPanel.add(cadastrarBtn); cadastroListarPanel.add(listarBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(cadastroListarPanel, gbc);

        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel idEditarLabel = new JLabel("ID Curso para Editar:"); aplicarEstiloLabel(idEditarLabel);
        JTextField idEditarField = new JTextField(10); aplicarEstiloTextField(idEditarField);
        idEditarField.setName("cursoIdEditarField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idEditarLabel, idEditarField, y++);
        
        JButton carregarBtn = new JButton("Carregar Dados"); aplicarEstiloBotao(carregarBtn);
        carregarBtn.setName("cursoCarregarBtn"); // <<< NOME ADICIONADO
        JButton salvarBtn = new JButton("Salvar Modificações"); aplicarEstiloBotao(salvarBtn);
        salvarBtn.setName("cursoSalvarBtn"); // <<< NOME ADICIONADO
        salvarBtn.setEnabled(false);
        JPanel edicaoBotoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,0));
        edicaoBotoesPanel.setBackground(ACADEMUS_BACKGROUND);
        edicaoBotoesPanel.add(carregarBtn); edicaoBotoesPanel.add(salvarBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(edicaoBotoesPanel, gbc);

        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);

        JLabel idExcluirLabel = new JLabel("ID Curso para Excluir:"); aplicarEstiloLabel(idExcluirLabel);
        JTextField idExcluirField = new JTextField(10); aplicarEstiloTextField(idExcluirField);
        idExcluirField.setName("cursoIdExcluirField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idExcluirLabel, idExcluirField, y++);
        
        JButton excluirBtn = new JButton("Excluir Curso por ID"); aplicarEstiloBotao(excluirBtn);
        excluirBtn.setName("cursoExcluirBtn"); // <<< NOME ADICIONADO
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(excluirBtn, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.weighty = 1.0; gbc.gridwidth = 2; painel.add(new JLabel(""), gbc);

        cadastrarBtn.addActionListener(e -> {
            try { String nome = nomeField.getText(); String cargaStr = cargaHorariaField.getText(); String prof = profRespField.getText();
                if (nome.isBlank() || cargaStr.isBlank() || prof.isBlank()) { JOptionPane.showMessageDialog(painel, "Todos os campos são obrigatórios.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE); return; }
                int carga = Integer.parseInt(cargaStr);
                GerenciadorDeDados.cadastrarCurso(nome, carga, prof);
                consoleArea.append("Curso '" + nome + "' cadastrado.\n");
                nomeField.setText(""); cargaHorariaField.setText(""); profRespField.setText(""); idEditarField.setText(""); idCursoEmEdicao = -1; salvarBtn.setEnabled(false);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "Carga Horária deve ser um número.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Erro ao cadastrar curso: " + ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        listarBtn.addActionListener(e -> { consoleArea.setText("--- Lista de Cursos ---\n" + GerenciadorDeDados.listarCursosRetorno() + "-----------------------\n"); });
        carregarBtn.addActionListener(e -> {
            try { if (idEditarField.getText().isBlank()) { JOptionPane.showMessageDialog(painel, "Insira o ID do curso para carregar.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                int idParaEditar = Integer.parseInt(idEditarField.getText()); String[] dados = GerenciadorDeDados.getCursoParaEdicao(idParaEditar);
                if (dados != null) { nomeField.setText(dados[0]); cargaHorariaField.setText(dados[1]); profRespField.setText(dados[2]); idCursoEmEdicao = idParaEditar; salvarBtn.setEnabled(true); consoleArea.append("Dados do curso (ID: " + idParaEditar + ") carregados.\n");
                } else { JOptionPane.showMessageDialog(painel, "Curso com ID " + idParaEditar + " não encontrado.", "Curso Inexistente", JOptionPane.WARNING_MESSAGE); nomeField.setText(""); cargaHorariaField.setText(""); profRespField.setText(""); idCursoEmEdicao = -1; salvarBtn.setEnabled(false); }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "O ID do curso deve ser um número.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE); idCursoEmEdicao = -1; salvarBtn.setEnabled(false); }
        });
        salvarBtn.addActionListener(e -> {
            if (idCursoEmEdicao == -1) { JOptionPane.showMessageDialog(painel, "Nenhum curso carregado para edição.", "Operação Inválida", JOptionPane.WARNING_MESSAGE); return; }
            try { String nome = nomeField.getText(); String cargaStr = cargaHorariaField.getText(); String prof = profRespField.getText();
                if (nome.isBlank() || cargaStr.isBlank() || prof.isBlank()) { JOptionPane.showMessageDialog(painel, "Todos os campos são obrigatórios.", "Validação Incompleta", JOptionPane.WARNING_MESSAGE); return; }
                int carga = Integer.parseInt(cargaStr);
                GerenciadorDeDados.editarCurso(idCursoEmEdicao, nome, carga, prof);
                consoleArea.append("Curso (ID: " + idCursoEmEdicao + ") atualizado.\n");
                nomeField.setText(""); cargaHorariaField.setText(""); profRespField.setText(""); idEditarField.setText(""); idCursoEmEdicao = -1; salvarBtn.setEnabled(false);
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "Carga Horária deve ser um número.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Erro ao salvar curso: " + ex.getMessage(), "Erro ao Salvar", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        excluirBtn.addActionListener(e -> {
            try { if (idExcluirField.getText().isBlank()) { JOptionPane.showMessageDialog(painel, "Insira o ID do curso para excluir.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                int idParaExcluir = Integer.parseInt(idExcluirField.getText());
                if (mostrarDialogoConfirmacao(painel, "Tem certeza que deseja excluir o curso com ID " + idParaExcluir + "?", "Confirmar Exclusão de Curso")) {
                     GerenciadorDeDados.excluirCurso(idParaExcluir);
                     consoleArea.append("Curso (ID: " + idParaExcluir + ") excluído.\n"); idExcluirField.setText("");
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(painel, "ID do curso deve ser um número.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Erro ao excluir curso: " + ex.getMessage(), "Erro na Exclusão", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        return painel;
    }

    // --- PAINEL VINCULAÇÕES ---
    private static JPanel criarPainelVinculacoes() {
        JPanel painel = criarPainelBase("Vinculações");
        painel.setName("painelVinculacoes"); // <<< NOME ADICIONADO
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel alunoLabel = new JLabel("Selecionar Aluno:"); aplicarEstiloLabel(alunoLabel);
        JComboBox<String> alunoBox = new JComboBox<>(); aplicarEstiloComboBox(alunoBox);
        alunoBox.setName("vinculacaoAlunoBox"); // <<< NOME ADICIONADO

        JLabel cursoLabel = new JLabel("Selecionar Curso:"); aplicarEstiloLabel(cursoLabel);
        JComboBox<String> cursoBox = new JComboBox<>(); aplicarEstiloComboBox(cursoBox);
        cursoBox.setName("vinculacaoCursoBox"); // <<< NOME ADICIONADO

        JLabel profLabel = new JLabel("Selecionar Professor:"); aplicarEstiloLabel(profLabel);
        JComboBox<String> professorBox = new JComboBox<>(); aplicarEstiloComboBox(professorBox);
        professorBox.setName("vinculacaoProfessorBox"); // <<< NOME ADICIONADO
        
        JButton atualizarCombosBtn = new JButton("Atualizar Listas"); aplicarEstiloBotao(atualizarCombosBtn);
        atualizarCombosBtn.setName("vinculacaoAtualizarListasBtn"); // <<< NOME ADICIONADO
        atualizarCombosBtn.addActionListener(e -> {
            preencherCombo(alunoBox, "alunos"); preencherCombo(cursoBox, "cursos"); preencherCombo(professorBox, "professores");
            consoleArea.append("Listas suspensas atualizadas.\n");
        });
        
        preencherCombo(alunoBox, "alunos"); preencherCombo(cursoBox, "cursos"); preencherCombo(professorBox, "professores");

        int y = 0;
        adicionarCampoComLabel(painel, gbc, alunoLabel, alunoBox, y++);
        adicionarCampoComLabel(painel, gbc, cursoLabel, cursoBox, y++);
        adicionarCampoComLabel(painel, gbc, profLabel, professorBox, y++);
        
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.fill = GridBagConstraints.NONE; painel.add(atualizarCombosBtn, gbc);

        JButton cadastrarBtn = new JButton("Vincular Selecionados"); aplicarEstiloBotao(cadastrarBtn);
        cadastrarBtn.setName("vinculacaoVincularBtn"); // <<< NOME ADICIONADO
        JButton listarBtn = new JButton("Listar Vinculações"); aplicarEstiloBotao(listarBtn);
        listarBtn.setName("vinculacaoListarBtn"); // <<< NOME ADICIONADO
        
        JPanel acaoVinculacaoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        acaoVinculacaoPanel.setBackground(ACADEMUS_BACKGROUND);
        acaoVinculacaoPanel.add(cadastrarBtn); acaoVinculacaoPanel.add(listarBtn);
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.insets = new Insets(15, 8, 8, 8); painel.add(acaoVinculacaoPanel, gbc); gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(15, 0, 15, 0);
        painel.add(new JSeparator(SwingConstants.HORIZONTAL), gbc); gbc.insets = new Insets(8, 8, 8, 8);

        JLabel idExcluirLabel = new JLabel("ID Vinculação p/ Excluir:"); aplicarEstiloLabel(idExcluirLabel);
        JTextField idExcluirField = new JTextField(10); aplicarEstiloTextField(idExcluirField);
        idExcluirField.setName("vinculacaoIdExcluirField"); // <<< NOME ADICIONADO
        adicionarCampoComLabel(painel, gbc, idExcluirLabel, idExcluirField, y++);
        
        JButton excluirBtn = new JButton("Excluir Vinculação por ID"); aplicarEstiloBotao(excluirBtn);
        excluirBtn.setName("vinculacaoExcluirBtn"); // <<< NOME ADICIONADO
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.LINE_START; gbc.fill = GridBagConstraints.NONE; painel.add(excluirBtn, gbc);

        gbc.gridx = 0; gbc.gridy = y; gbc.weighty = 1.0; gbc.gridwidth = 2; painel.add(new JLabel(""), gbc);

        cadastrarBtn.addActionListener(e -> {
            try { String alunoSelecionado = (String) alunoBox.getSelectedItem(); String cursoSelecionado = (String) cursoBox.getSelectedItem(); String profSelecionado = (String) professorBox.getSelectedItem();
                if (alunoSelecionado == null || alunoSelecionado.startsWith("Nenhum") || cursoSelecionado == null || cursoSelecionado.startsWith("Nenhum") || profSelecionado == null || profSelecionado.startsWith("Nenhum")) {
                    JOptionPane.showMessageDialog(painel, "É necessário selecionar um aluno, um curso e um professor válidos das listas.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE); return;
                }
                int alunoId = Integer.parseInt(alunoSelecionado.split(" - ")[0]); int cursoId = Integer.parseInt(cursoSelecionado.split(" - ")[0]); int professorId = Integer.parseInt(profSelecionado.split(" - ")[0]);
                GerenciadorDeDados.cadastrarVinculacao(alunoId, cursoId, professorId); consoleArea.append("Vinculação cadastrada com sucesso!\n");
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) { JOptionPane.showMessageDialog(painel, "Erro ao processar a seleção. Verifique se as listas estão corretamente carregadas e o formato é 'ID - Nome'.", "Erro de Formato da Seleção", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Ocorreu um erro ao cadastrar a vinculação: " + ex.getMessage(), "Erro de Vinculação", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        listarBtn.addActionListener(e -> { consoleArea.setText("--- Lista de Vinculações ---\n" + GerenciadorDeDados.listarVinculacoesRetorno() + "---------------------------\n"); });
        excluirBtn.addActionListener(e -> {
            try { if(idExcluirField.getText().isBlank()){ JOptionPane.showMessageDialog(painel, "Por favor, insira o ID da vinculação que deseja excluir.", "ID Não Informado", JOptionPane.WARNING_MESSAGE); return; }
                 int idParaExcluir = Integer.parseInt(idExcluirField.getText());
                 if(mostrarDialogoConfirmacao(painel, "Tem certeza que deseja excluir a vinculação com ID " + idParaExcluir + "?", "Confirmar Exclusão de Vinculação")) {
                     GerenciadorDeDados.excluirVinculacao(idParaExcluir);
                     consoleArea.append("Vinculação com ID " + idParaExcluir + " foi excluída.\n"); idExcluirField.setText("");
                 }
            } catch (NumberFormatException ex){ JOptionPane.showMessageDialog(painel, "O ID da vinculação deve ser um número válido.", "Formato Inválido de ID", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) { JOptionPane.showMessageDialog(painel, "Ocorreu um erro ao excluir a vinculação: " + ex.getMessage(), "Erro na Exclusão", JOptionPane.ERROR_MESSAGE); ex.printStackTrace(); }
        });
        return painel;
    }

    private static void preencherCombo(JComboBox<String> combo, String tabela) {
        combo.removeAllItems();
        String nomeColuna = tabela.equals("cursos") ? "nome_curso" : "nome";
        String sql = "SELECT id, " + nomeColuna + " FROM " + tabela + " ORDER BY " + nomeColuna;
        try (Connection conn = ConexaoMySQL.conectar(); //
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            boolean hasItems = false;
            while (rs.next()) { combo.addItem(rs.getInt("id") + " - " + rs.getString(2)); hasItems = true; }
            if (!hasItems) { combo.addItem("Nenhum(a) " + (tabela.endsWith("s") ? tabela.substring(0, tabela.length() -1) : tabela) + " cadastrado(a)"); }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar dados para ComboBox da tabela " + tabela + ": " + e.getMessage());
            combo.addItem("Erro ao carregar " + tabela);
        }
    }
}