create or replace function validaChavePessoa()
	returns trigger
	language plpgsql
as $$
declare existe integer;

begin
	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_id);
	if (existe <= 0) then
		existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_id);
	end if;
	if (existe <= 0) then
		raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
	end if;
	return new;
end;
$$


create or replace function validaChavePessoaFornecedor()
	returns trigger
	language plpgsql
as $$
declare existe integer;

begin
	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_forn_id);
	if (existe <= 0) then
		existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_forn_id);
	end if;
	if (existe <= 0) then
		raise exception 'Não foi encontrado o ID ou PK da pessoa para realizar a associação';
	end if;
	return new;
end;
$$


/*NEW = carrega os dados de insert e update*/
/*OLD = carrega os dados da linha antiga antes de atualizar*/


create trigger validaChavePessoaVdCpLojaVirt
before update 
on vd_cp_loja_virt
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaVdCpLojaVirt2
before insert 
on vd_cp_loja_virt
for each row
execute procedure validaChavePessoa();

create trigger validaChavePessoaFornContaPagar
before update 
on conta_pagar
for each row
execute procedure validaChavePessoaFornecedor();

create trigger validaChavePessoaFornContaPagar2
before insert 
on conta_pagar
for each row
execute procedure validaChavePessoaFornecedor();













