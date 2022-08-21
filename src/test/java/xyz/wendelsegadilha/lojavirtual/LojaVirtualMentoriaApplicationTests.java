package xyz.wendelsegadilha.lojavirtual;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;
import xyz.wendelsegadilha.lojavirtual.model.Acesso;
import xyz.wendelsegadilha.lojavirtual.repository.AcessoRepository;

@Profile("test")
@SpringBootTest(classes = LojaVirtualMentoriaApplication.class)
public class LojaVirtualMentoriaApplicationTests extends TestCase{
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		
		/*Contexto da aplicação*/
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_COMPRADOR " + Calendar.getInstance().getTimeInMillis());
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*Requisição e resposta do controlador*/
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/salvarAcesso")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				);
		
		/*Response da requisição*/
		String retorno = retornoApi.andReturn().getResponse().getContentAsString();
		
		System.out.println("Retorno: " + retorno);
		
		/*Converte o retorno da Api em um objeto*/
		Acesso acessoRetorno = objectMapper.readValue(retorno, Acesso.class);
		
		assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
		
		
	}
	
	@Test
	public void testRestApiDeleteAcesso() throws JsonProcessingException, Exception {
		
		/*Contexto da aplicação*/
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_DELTE_TESTE");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*Requisição e resposta do controlador*/
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.post("/deleteAcesso")
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				);
		
		/*Response da requisição*/
		String retorno = retornoApi.andReturn().getResponse().getContentAsString();
		int status = retornoApi.andReturn().getResponse().getStatus();
		
		System.out.println("Retorno da Api: " + retorno);
		System.out.println("Status do retorno: " + status);
		
		/*Efetua os testes*/
		assertEquals("Acesso excluido com sucesso", retorno);
		assertEquals(200, status);
		
	}
	
	@Test
	public void testRestApiDeletePorIdAcesso() throws JsonProcessingException, Exception {
		
		/*Contexto da aplicação*/
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_DELTE_POR_ID_TESTE");
		
		acesso = acessoRepository.save(acesso);
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		/*Requisição e resposta do controlador*/
		ResultActions retornoApi = mockMvc.perform(MockMvcRequestBuilders.delete("/deleteAcessoPorId/"+acesso.getId())
				.content(objectMapper.writeValueAsString(acesso))
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				);
		
		/*Response da requisição*/
		String retorno = retornoApi.andReturn().getResponse().getContentAsString();
		int status = retornoApi.andReturn().getResponse().getStatus();
		
		System.out.println("Retorno da Api: " + retorno);
		System.out.println("Status do retorno: " + status);
		
		/*Efetua os testes*/
		assertEquals("Acesso removido", retorno);
		assertEquals(200, status);
		
	}
	
	@Test
	public void testRestApiObterAcessoID() throws JsonProcessingException, Exception {
		
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    Acesso acesso = new Acesso();
	    
	    acesso.setDescricao("ROLE_OBTER_ID");
	    
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	    
	    
	    Acesso acessoRetorno = objectMapper.readValue(retornoApi.andReturn().getResponse().getContentAsString(), Acesso.class);
	    
	    assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
	    
	    assertEquals(acesso.getId(), acessoRetorno.getId());
	    
	}
	
	@Test
	public void testRestApiObterAcessoDesc() throws JsonProcessingException, Exception {
		
	    DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
	    MockMvc mockMvc = builder.build();
	    
	    Acesso acesso = new Acesso();
	    
	    acesso.setDescricao("ROLE_TESTE_OBTER_LIST");
	    
	    acesso = acessoRepository.save(acesso);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    ResultActions retornoApi = mockMvc
	    						 .perform(MockMvcRequestBuilders.get("/buscarPorDesc/OBTER_LIST")
	    						 .content(objectMapper.writeValueAsString(acesso))
	    						 .accept(MediaType.APPLICATION_JSON)
	    						 .contentType(MediaType.APPLICATION_JSON));
	    
	    assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
	    
	    
	    List<Acesso> retornoApiList = objectMapper.
	    							     readValue(retornoApi.andReturn()
	    									.getResponse().getContentAsString(),
	    									 new TypeReference<List<Acesso>> () {});

	    assertEquals(1, retornoApiList.size());
	    
	    assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
	    
	    acessoRepository.deleteById(acesso.getId());
	    
	}

}
