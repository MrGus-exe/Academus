package interface_grafica;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JOptionPaneFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.timing.Timeout;
import org.junit.Test;
import org.assertj.core.api.Assertions; // <<< IMPORTAÇÃO ADICIONADA

public class InterfacePrincipalE2ETest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private FrameFixture telaPrincipalWindow;
    private static final Timeout OPTION_PANE_TIMEOUT = Timeout.timeout(5000);

    @Override
    protected void onSetUp() {
        // ... (seu código onSetUp como antes) ...
        GuiActionRunner.execute(() -> {
            InterfacePrincipal.main(null);
            return null;
        });

        window = org.assertj.swing.finder.WindowFinder.findFrame("loginFrame")
                     .withTimeout(10000) 
                     .using(robot()); 
        
        window.show(); 
        window.moveToFront(); 
    }

    @Override
    protected void onTearDown() {
        // ... (seu código onTearDown como antes) ...
        try {
            FrameFixture principalParaFechar = org.assertj.swing.finder.WindowFinder.findFrame("telaPrincipalFrame")
                                                .withTimeout(2000) 
                                                .using(robot());
            if (principalParaFechar != null && principalParaFechar.target().isShowing()) {
                 GuiActionRunner.execute(() -> principalParaFechar.target().dispose());
            }
            if (telaPrincipalWindow != null) { 
                telaPrincipalWindow.cleanUp();
                telaPrincipalWindow = null; 
            }
        } catch (org.assertj.swing.exception.WaitTimedOutError e) {
            System.out.println("onTearDown: Tela principal não encontrada para fechar (provavelmente já estava fechada ou não foi aberta).");
        }

        if (window != null) {
            if (window.target().isShowing()) { 
                 GuiActionRunner.execute(() -> window.target().dispose());
            }
            window.cleanUp();
            window = null;
        }
    }

    @Test
    public void testLoginBemSucedidoEAcessoAbaAlunos() {
        // ... (código do teste como antes) ...
        window.textBox("loginUsuarioField").requireVisible().requireEnabled().enterText("adminE2E");
        window.textBox("loginSenhaField").requireVisible().requireEnabled().enterText("senhaE2E123!");
        window.button("loginEntrarBtn").requireVisible().requireEnabled().click();

        JOptionPaneFixture optionPaneSucesso = window.optionPane(OPTION_PANE_TIMEOUT); 
        optionPaneSucesso.requireMessage("Login bem-sucedido!").okButton().click();

        telaPrincipalWindow = org.assertj.swing.finder.WindowFinder.findFrame("telaPrincipalFrame")
                                        .withTimeout(10000)
                                        .using(robot());
        telaPrincipalWindow.requireVisible();
        telaPrincipalWindow.tabbedPane("abasPrincipais").requireVisible().selectTab("Alunos");
        telaPrincipalWindow.textBox("alunoNomeField").requireVisible();
        telaPrincipalWindow.button("alunoCadastrarBtn").requireVisible();
    }
    
    @Test
    public void testLoginComFalhaUsuarioInvalido() {
        // ... (código do teste como antes) ...
        window.textBox("loginUsuarioField").enterText("usuarioInvalidoMuitoErrado");
        window.textBox("loginSenhaField").enterText("senhaInvalidaQualquer");
        window.button("loginEntrarBtn").click();

        JOptionPaneFixture optionPaneErro = window.optionPane(OPTION_PANE_TIMEOUT);
        optionPaneErro.requireErrorMessage().requireMessage("Usuário ou senha inválidos.").okButton().click();
        
        window.textBox("loginUsuarioField").requireVisible(); 
    }

    @Test
    public void testCadastrarNovoAlunoComSucesso() {
        // Login
        window.textBox("loginUsuarioField").enterText("adminE2E");
        window.textBox("loginSenhaField").enterText("senhaE2E123!");
        window.button("loginEntrarBtn").click();
        
        JOptionPaneFixture optionPaneSucessoLogin = window.optionPane(OPTION_PANE_TIMEOUT);
        optionPaneSucessoLogin.requireMessage("Login bem-sucedido!").okButton().click();

        telaPrincipalWindow = org.assertj.swing.finder.WindowFinder.findFrame("telaPrincipalFrame")
                                        .withTimeout(10000)
                                        .using(robot());
        telaPrincipalWindow.requireVisible();

        // Navegar para a aba Alunos
        telaPrincipalWindow.tabbedPane("abasPrincipais").selectTab("Alunos");

        String nomeNovoAluno = "Aluno TesteE2E " + System.currentTimeMillis();
        String matriculaNovoAluno = "E2E" + (System.currentTimeMillis() / 1000);

        telaPrincipalWindow.textBox("alunoNomeField").enterText(nomeNovoAluno);
        telaPrincipalWindow.textBox("alunoIdadeField").enterText("23");
        telaPrincipalWindow.textBox("alunoMatriculaField").enterText(matriculaNovoAluno);
        telaPrincipalWindow.button("alunoCadastrarBtn").click();
        
        // ***** INÍCIO DA CORREÇÃO *****
        String consoleLogText = telaPrincipalWindow.textBox("consoleArea").text(); // Pega o texto
        Assertions.assertThat(consoleLogText).contains("Aluno '" + nomeNovoAluno + "' cadastrado com sucesso."); // Verifica se contém
        // ***** FIM DA CORREÇÃO *****
        
        telaPrincipalWindow.textBox("alunoNomeField").requireText("");
        telaPrincipalWindow.textBox("alunoIdadeField").requireText("");
        telaPrincipalWindow.textBox("alunoMatriculaField").requireText("");
    }
}