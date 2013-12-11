package br.com.caelum.estoque.ws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace="http://ws.estoque.caelum.com.br/estoquews/v1")
//@Stateless
public class EstoqueWS {

	//simulando um repositorio / banco de dados
	private Map<String, Integer> REPO = new HashMap<>();

	public EstoqueWS() {
		//populando alguns dados
		REPO.put("SOA", 0);
		REPO.put("TDD", 1);
		REPO.put("RES", 2);
		REPO.put("LOG", 0);
		REPO.put("WEB", 1);
		REPO.put("ARQ", 2);
	}

	@WebMethod(operationName="ItensPeloCodigo")
//	@RequestWrapper(localName="codigos")
//	@ResponseWrapper(localName="itens")
	@WebResult(name="ItemEstoque")
	public List<ItemEstoque> getQuantidade(	
			   @WebParam(name = "codigo") List<String> codigos,
			   @WebParam(name = "tokenUsuario", header = true) String token) {
		
		if(token == null || !token.equals("TOKEN123")) {
			throw new AutorizacaoException("Nao autorizado");
		}
		
		List<ItemEstoque> itens = new ArrayList<>();
		
		if(codigos == null || codigos.isEmpty()) {
			return itens;
		}

		System.out.println("verificando " + codigos);
		
		for (String codigo : codigos) {
			if(REPO.get(codigo) != null)
				itens.add(new ItemEstoque(codigo, REPO.get(codigo)));
		}
		
		return itens;
	}

}