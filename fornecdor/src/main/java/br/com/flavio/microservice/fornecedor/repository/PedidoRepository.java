package br.com.flavio.microservice.fornecedor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.flavio.microservice.fornecedor.model.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long>{

}
