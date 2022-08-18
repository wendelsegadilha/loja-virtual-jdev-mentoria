package xyz.wendelsegadilha.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.wendelsegadilha.lojavirtual.ExceptionMentoriaJava;
import xyz.wendelsegadilha.lojavirtual.model.Acesso;
import xyz.wendelsegadilha.lojavirtual.repository.AcessoRepository;
import xyz.wendelsegadilha.lojavirtual.service.AcessoService;

@Controller
@RestController
public class AcessoController {

	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;

	@ResponseBody
	@PostMapping(value = "**/salvarAcesso")
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws Exception {
		
		if(acesso.getId() == null) {
			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			if (!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe Acesso com essa descrição: " + acesso.getDescricao());
			}
		}
		
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/deleteAcesso")
	public ResponseEntity<?> deleteAcesso(@RequestBody Acesso acesso) {
		
		acessoService.delete(acesso);
		
		return new ResponseEntity<Object>("Acesso excluido com sucesso", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deleteAcessoPorId/{id}")
	public ResponseEntity<?> deleteAcesso(@PathVariable("id") Long id) {
		
		acessoRepository.deleteById(id);
		
		return new ResponseEntity<Object>("Acesso removido", HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable("id") Long id) throws Exception {
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if (acesso == null) {
			throw new ExceptionMentoriaJava("Acesso de código " + id + " não encontrado");
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping(value = "**/buscarPorDesc/{desc}")
	public ResponseEntity<List<Acesso>> obterAcesso(@PathVariable("desc") String desc) {
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}
	

}
