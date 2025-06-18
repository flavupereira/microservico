package br.com.flavio.microservice.fornecedor.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.flavio.microservice.fornecedor.dto.ItemDoPedidoDTO;
import br.com.flavio.microservice.fornecedor.model.Pedido;
import br.com.flavio.microservice.fornecedor.model.PedidoItem;
import br.com.flavio.microservice.fornecedor.model.Produto;
import br.com.flavio.microservice.fornecedor.repository.PedidoRepository;
import br.com.flavio.microservice.fornecedor.repository.ProdutoRepository;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	public Pedido realizaPedido(List<ItemDoPedidoDTO> itens) {
		if (itens == null || itens.isEmpty()) {
			throw new IllegalArgumentException("Lista de itens do pedido não pode ser nula ou vazia.");
		}

		List<PedidoItem> pedidoItens = toPedidoItem(itens);
		Pedido pedido = new Pedido(pedidoItens);
		pedido.setTempoDePreparo(itens.size()); // lógica pode ser melhorada futuramente
		return pedidoRepository.save(pedido);
	}

	public Pedido getPedidoPorId(Long id) {
		return this.pedidoRepository.findById(id).orElse(new Pedido());
	}

	private List<PedidoItem> toPedidoItem(List<ItemDoPedidoDTO> itens) {
		List<Long> idsProdutos = itens
				.stream()
				.map(ItemDoPedidoDTO::getId)
				.collect(Collectors.toList());

		List<Produto> produtosDoPedido = produtoRepository.findByIdIn(idsProdutos);

		Map<Long, Produto> produtoMap = produtosDoPedido
				.stream()
				.collect(Collectors.toMap(Produto::getId, Function.identity()));

		return itens
				.stream()
				.map(item -> {
					Produto produto = produtoMap.get(item.getId());
					if (produto == null) {
						throw new IllegalArgumentException("Produto com ID " + item.getId() + " não encontrado");
					}
					PedidoItem pedidoItem = new PedidoItem();
					pedidoItem.setProduto(produto);
					pedidoItem.setQuantidade(item.getQuantidade());
					return pedidoItem;
				})
				.collect(Collectors.toList());
	}
}
