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

import br.com.azi.sac.bo.AgendamentoBO;
import br.com.azi.sac.bo.ClienteBO;
import br.com.azi.sac.bo.ColaboradorBO;

@Path("/agendamento")
@Transactional
public class AgendamentoService {
	
	@Inject
	private AgendamentoBO abo;
	
	@Inject
	private ClienteBO cbo;
	
	@Inject
	private ColaboradorBO cobo;
	
	@GET
	@Path("/salvar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object salvar(@QueryParam("agendamento") String dados) {
		
		JsonReader jr = Json.createReader(new ByteArrayInputStream(dados.getBytes()));
		JsonObject obj = jr.readObject();
		
		return abo.salvar(obj);
	}
	
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listar(@QueryParam("busca") String busca) {
		
		return abo.listar(busca);
		
	}
	
	@GET
	@Path("/editar")
	@Produces(MediaType.APPLICATION_JSON)
	public Object editar(@QueryParam("id") Long id) {
		
		return abo.editar(id);
	}
	
	@GET
	@Path("/excluir")
	@Produces(MediaType.APPLICATION_JSON)
	public Object excluir(@QueryParam("id") List<Long> ids, @QueryParam("busca") String busca) {
		
		for (Long id : ids) 
			abo.excluir(id);
		
		
		return abo.listar(busca);
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
	
	public AgendamentoBO getAbo() {
		return abo;
	}

	public void setAbo(AgendamentoBO abo) {
		this.abo = abo;
	}

	public ClienteBO getCbo() {
		return cbo;
	}

	public void setCbo(ClienteBO cbo) {
		this.cbo = cbo;
	}

	public ColaboradorBO getCobo() {
		return cobo;
	}

	public void setCobo(ColaboradorBO cobo) {
		this.cobo = cobo;
	}
}
