package xyz.wendelsegadilha.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import xyz.wendelsegadilha.lojavirtual.controller.AcessoController;
import xyz.wendelsegadilha.lojavirtual.model.Acesso;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests {

	@Autowired
	private AcessoController acessoController;
	
	@Test
	public void testeCadastroAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_USER");
		
		acessoController.salvarAcesso(acesso);
	}

}
