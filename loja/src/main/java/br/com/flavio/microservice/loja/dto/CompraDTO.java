package br.com.flavio.microservice.loja.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.flavio.microservice.loja.model.CompraState;

public class CompraDTO {
	
	@JsonIgnore
	private Long compraId;

	private List<ItemDaCompraDTO> itens;
	
	private EnderecoDTO endereco;
	

	public Long getCompraId() {
		return compraId;
	}

	public void setCompraId(Long compraId) {
		this.compraId = compraId;
	}

	public List<ItemDaCompraDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemDaCompraDTO> itens) {
		this.itens = itens;
	}

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

	
	
	
}
