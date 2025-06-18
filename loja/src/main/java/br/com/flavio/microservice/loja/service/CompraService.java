package br.com.flavio.microservice.loja.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.flavio.microservice.loja.client.FornecedorClient;
import br.com.flavio.microservice.loja.client.TransportadorClient;
import br.com.flavio.microservice.loja.dto.CompraDTO;
import br.com.flavio.microservice.loja.dto.InfoEntregaDTO;
import br.com.flavio.microservice.loja.dto.InfoFornecedorDTO;
import br.com.flavio.microservice.loja.dto.InfoPedidoDTO;
import br.com.flavio.microservice.loja.dto.VoucherDTO;
import br.com.flavio.microservice.loja.model.Compra;
import br.com.flavio.microservice.loja.model.CompraState;
import br.com.flavio.microservice.loja.repository.CompraRepository;

@Service
public class CompraService {
	
	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private TransportadorClient transportadorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	
	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	

	@HystrixCommand(fallbackMethod = "realizaCompraFallback",
			threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDTO compra) {
		
		Compra compraSalva = new Compra();
		compraSalva.setState(CompraState.RECEBIDO);
		compraRepository.save(compraSalva);
		compra.setCompraId(compraSalva.getId());
		
		InfoFornecedorDTO info = new InfoFornecedorDTO();
		InfoPedidoDTO pedido = new InfoPedidoDTO();
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setState(CompraState.PEDIDO_REALIZADO);
		
		
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraRepository.save(compraSalva);
		
		
		InfoEntregaDTO entregaDTO = new InfoEntregaDTO();
		entregaDTO.setPedidoId(pedido.getId());
		entregaDTO.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDTO.setEnderecoOrigem(info.getEndereco());
		entregaDTO.setEnderecoDestino(compra.getEndereco().toString());
		VoucherDTO voucher = transportadorClient.reservaEntrega(entregaDTO);
		compraSalva.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());
		
		compraRepository.save(compraSalva);
		
		//System.out.println(info.getEndereco());
		
		
		return compraSalva;
	}
	
	public Compra realizaCompraFallback(CompraDTO compra) {
		if(compra.getCompraId() != null) {
			return compraRepository.findById(compra.getCompraId()).get();
		}
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	
	}

}
