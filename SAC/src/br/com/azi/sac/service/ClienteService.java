package br.com.azi.sac.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.azi.sac.bo.ClienteBO;

@Path("/cliente")
@Transactional
public class ClienteService {
	
	@Inject
	private ClienteBO cbo;
	
	@GET
	@Path("/salvar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object salvar(@QueryParam("cliente") String dados) {
		
		JsonReader reader = Json.createReader(new ByteArrayInputStream(dados.getBytes()));
		JsonObject obj =  reader.readObject();
		
		return cbo.salvar(obj);
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listarClientes(@QueryParam("busca") String busca) {
		
		return cbo.listarClientes(busca);
	}
	
	@GET
	@Path("/editar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object editar(@QueryParam("id") Long idCliente) {	
		
		return cbo.editar(idCliente);
	}
	
	@GET
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Object excluir(@QueryParam("id") List<Long> ids, @QueryParam("busca") String busca) {
		
		for (Long id : ids)
			cbo.excluir(id);
		
		return cbo.listarClientes(busca);
	}
	
	public ClienteBO getCbo() {
		return cbo;
	}

	public void setCbo(ClienteBO cbo) {
		this.cbo = cbo;
	}
	
	
}
