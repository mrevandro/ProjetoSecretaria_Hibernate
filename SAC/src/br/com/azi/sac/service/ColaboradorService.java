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

import br.com.azi.sac.bo.ColaboradorBO;

@Path("/colaborador")
@Transactional
public class ColaboradorService {
	
	@Inject
	private ColaboradorBO cbo;
	
	@GET
	@Path("/salvar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object salvar(@QueryParam("colaborador") String dados) {
		
		JsonReader jr = Json.createReader(new ByteArrayInputStream(dados.getBytes()));
		JsonObject obj = jr.readObject();
		
		return cbo.salvar(obj);
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listar(@QueryParam("busca") String busca) {
		
		return cbo.listar(busca);
		
	}
	
	@GET
	@Path("/editar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object editar(@QueryParam("id") Long idColaborador) {
		
		return cbo.editar(idColaborador);
		
	}
	
	@GET
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Object excluir(@QueryParam("id") List<Long> ids, @QueryParam("busca") String busca) {
		
		for (Long id : ids) 
			cbo.excluir(id);
		
		return cbo.listar(busca);
		
	}

	public ColaboradorBO getCbo() {
		return cbo;
	}

	public void setCbo(ColaboradorBO cbo) {
		this.cbo = cbo;
	}
	
	
}
