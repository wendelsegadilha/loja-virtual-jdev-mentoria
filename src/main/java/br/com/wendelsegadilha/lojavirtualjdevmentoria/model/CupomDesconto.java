package br.com.wendelsegadilha.lojavirtualjdevmentoria.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cumpom_desconto")
@SequenceGenerator(name = "seq_cumpom_desconto", sequenceName = "seq_cumpom_desconto", allocationSize = 1, initialValue = 1)
public class CupomDesconto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cumpom_desconto")
	private Long id;
	
	private String codigo;
	private BigDecimal valorDescontoReal;
	private String valorDescontoPorcentagem;
	
	@Temporal(TemporalType.DATE)
	private Date dataValidadeCupom;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValorDescontoReal() {
		return valorDescontoReal;
	}

	public void setValorDescontoReal(BigDecimal valorDescontoReal) {
		this.valorDescontoReal = valorDescontoReal;
	}

	public String getValorDescontoPorcentagem() {
		return valorDescontoPorcentagem;
	}

	public void setValorDescontoPorcentagem(String valorDescontoPorcentagem) {
		this.valorDescontoPorcentagem = valorDescontoPorcentagem;
	}

	public Date getDataValidadeCupom() {
		return dataValidadeCupom;
	}

	public void setDataValidadeCupom(Date dataValidadeCupom) {
		this.dataValidadeCupom = dataValidadeCupom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CupomDesconto other = (CupomDesconto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
