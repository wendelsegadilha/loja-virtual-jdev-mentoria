package xyz.wendelsegadilha.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "xyz.wendelsegadilha.lojavirtual.model")
@ComponentScan(basePackages = "xyz.wendelsegadilha.*")
@EnableJpaRepositories(basePackages = "xyz.wendelsegadilha.lojavirtual.repository")
@EnableTransactionManagement
public class LojaVirtualMentoriaApplication {

	public static void main(String[] args) {
		
		/*Gera senha para teste*/
		System.out.println("Senha: " + new BCryptPasswordEncoder().encode("123"));//
		
		SpringApplication.run(LojaVirtualMentoriaApplication.class, args);
	}

}
