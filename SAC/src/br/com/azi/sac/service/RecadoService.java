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
import br.com.azi.sac.bo.ColaboradorBO;
import br.com.azi.sac.bo.RecadoBO;

@Path("/recado")
@Transactional
public class RecadoService {

	@Inject
	private RecadoBO rbo;
	
	@Inject
	private ClienteBO cbo;
	
	@Inject
	private ColaboradorBO cobo;
	
	@GET
	@Path("/salvar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object salvar(@QueryParam("recado") String dados) {

		JsonReader jr = Json.createReader(new ByteArrayInputStream(dados.getBytes()));
		JsonObject obj = jr.readObject();

		return getRbo().salvar(obj);
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listar(@QueryParam("busca") String busca) {
		
		return rbo.listar(busca);
		
	}
	
	@GET
	@Path("/editar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object editar(@QueryParam("id") Long id) {
		
		return rbo.editar(id);
	}
	
	@GET
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Object excluir(@QueryParam("id") List<Long> ids, @QueryParam("busca") String busca) {
		
		for (Long id : ids) 
			rbo.excluir(id);
		
		
		return rbo.listar(busca);
	}
	
	@GET
	@Path("/buscarClientes")
	@Produces(MediaType.APPLICATION_JSON)
	public Object buscarClientes(@QueryParam("busca") String busca) {
		
		return cbo.buscaClientes(busca);
	}
	
	@GET
	@Path("/buscarColaboradores")
	@Produces(MediaType.APPLICATION_JSON)
	public Object buscarColaboradores(@QueryParam("busca") String busca) {
		
		return cobo.buscaColaboradores(busca);
	}

	public RecadoBO getRbo() {
		return rbo;
	}

	public void setRbo(RecadoBO rbo) {
		this.rbo = rbo;
	}
}
