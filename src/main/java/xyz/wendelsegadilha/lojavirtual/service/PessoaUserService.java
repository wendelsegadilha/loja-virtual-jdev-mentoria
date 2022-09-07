package xyz.wendelsegadilha.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import xyz.wendelsegadilha.lojavirtual.model.PessoaJuridica;
import xyz.wendelsegadilha.lojavirtual.model.Usuario;
import xyz.wendelsegadilha.lojavirtual.repository.PesssoaRepository;
import xyz.wendelsegadilha.lojavirtual.repository.UsuarioRepository;

@Service
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PesssoaRepository pesssoaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceSendEmail ServiceSendEmail;

	public PessoaJuridica salvarPessoaJuridica(PessoaJuridica pessoaJuridica) {
		
		/*Seta os endereços antes de salvar*/
		for (int i = 0; i < pessoaJuridica.getEnderecos().size(); i++) {
			pessoaJuridica.getEnderecos().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEnderecos().get(i).setEmpresa(pessoaJuridica);
		}

		pessoaJuridica = pesssoaRepository.save(pessoaJuridica);

		Usuario usuarioPj = usuarioRepository.findUserByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());

		/*Cria um usuario caso não exista um associado a essa pessoa*/
		if (usuarioPj == null){
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null){
				jdbcTemplate.execute("begin; alter table usuarios_acesso drop constraint " + constraint + "; commit;");
			}
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(pessoaJuridica);
			usuarioPj.setPessoa(pessoaJuridica);
			usuarioPj.setLogin(pessoaJuridica.getEmail());
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
			usuarioPj.setSenha(senhaCriptografada);

			usuarioPj = usuarioRepository.save(usuarioPj);
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId());
			
			StringBuilder menssagem = new StringBuilder();
			menssagem.append("<b>Segue abaixo seus dados de acesso para a Loja Virtual</b><br><br>");
			menssagem.append("<b>Login: </b>").append(pessoaJuridica.getEmail()).append("<br>");
			menssagem.append("<b>Senha: </b>").append(senha).append("<br><br>");
			menssagem.append("Obrigado!");
			
			/*Fazer o envio de e-mail do login e da senha*/
			try {
				ServiceSendEmail.enviarEmailHtml("Dados de acesso Loja Virtual", menssagem.toString(), pessoaJuridica.getEmail());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

		return pessoaJuridica;
	}

}
