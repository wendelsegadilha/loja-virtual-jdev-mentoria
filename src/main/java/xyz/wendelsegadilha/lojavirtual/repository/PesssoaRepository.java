package xyz.wendelsegadilha.lojavirtual.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import xyz.wendelsegadilha.lojavirtual.model.PessoaJuridica;

public interface PesssoaRepository extends CrudRepository<PessoaJuridica, Long> {

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    PessoaJuridica existeCnpjCadastrado(String cnpj);

}
