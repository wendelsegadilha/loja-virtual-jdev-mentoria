package xyz.wendelsegadilha.lojavirtual;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import junit.framework.TestCase;
import xyz.wendelsegadilha.lojavirtual.controller.AcessoController;
import xyz.wendelsegadilha.lojavirtual.model.Acesso;
import xyz.wendelsegadilha.lojavirtual.repository.AcessoRepository;

@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests extends TestCase{

	@Autowired
	private AcessoController acessoController;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Test
	public void testeCadastroAcesso() {
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_SECRETARIO");
		
		assertEquals(true, acesso.getId() == null);
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		assertEquals(true, acesso.getId() > 0);
	}
	
	@Test
	public void testeBuscaAcesso() {
		
		Acesso acesso = acessoRepository.findById(3L).get();
		assertEquals("ROLE_GERENTE", acesso.getDescricao());
		
	}
	
	@Test
	public void testeDeleteAcesso() {
		
		Acesso acesso = acessoRepository.buscarAcessoDesc("SECRETARIO").get(0);
		assertEquals("ROLE_SECRETARIO", acesso.getDescricao());
		
		acessoRepository.deleteById(acesso.getId());
		
		testeCadastroAcesso();
		
	}

}
