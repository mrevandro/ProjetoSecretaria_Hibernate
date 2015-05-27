package br.com.azi.sac.bo;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;

import javax.enterprise.context.Dependent;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.azi.sac.entity.Agendamento;
import br.com.azi.sac.entity.Cliente;
import br.com.azi.sac.entity.Colaborador;
import br.com.azi.sac.entity.EnumAgendamentoTipo;

@Dependent
@Transactional
public class AgendamentoBO {

	@PersistenceContext(unitName = "recepcao")
	private EntityManager em;

	public Object salvar(JsonObject json) {

		Agendamento age;

		if (json.isNull("id"))
			age = new Agendamento();
		else
			age = getEm().find(Agendamento.class, json.getJsonNumber("id").longValue());

		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			Date data = (Date) format.parse(json.getJsonString("data").getString());
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);

			age.setData(!json.isNull("data") ? cal : null);

			SimpleDateFormat formatH = new SimpleDateFormat("HH:MM:SS");
			age.setHorario(!json.isNull("horario") ? new Time(formatH.parse(json.getJsonString("horario").getString()).getTime()) : null);
			age.setObservacao(!json.isNull("observacao") ? json.getJsonString("observacao").getString() : null);
			age.setAgendamentoTipo(!json.isNull("agendamentoTipo") ? EnumAgendamentoTipo.valueOf(json.getJsonString("agendamentoTipo").getString()) : null);
			
			
			
			sincronizarClientesEColaboradores(json, age);
			
			age = em.merge(age);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return age;
	}

	private void sincronizarClientesEColaboradores(JsonObject json, Agendamento age) {
		JsonArray envolvidos = json.getJsonArray("envolvidos");
		
		List<Long> clienteIds = new ArrayList<Long>();
		List<Long> colaboradorIds = new ArrayList<Long>();
		for (JsonValue jsonValue : envolvidos) {
			
			JsonObject envolvido = (JsonObject) jsonValue;
			if(envolvido.getJsonString("tipo").getString().equals("Cliente")) {
				
				Cliente c = em.find(Cliente.class, envolvido.getJsonNumber("id").longValue());
				clienteIds.add(c.getId());
				
				if(!age.getClientes().contains(c))
					age.getClientes().add(c);

			}else{ // Colaborador
				
				Colaborador c = em.find(Colaborador.class, envolvido.getJsonNumber("id").longValue());
				colaboradorIds.add(c.getId());
				
				if(!age.getColaboradores().contains(c))
					age.getColaboradores().add(c);
			}
		}
		
		for (int index=age.getClientes().size(); index>-1; index--)
			
			if(!clienteIds.contains(age.getClientes().get(index)))
				age.getClientes().remove(age.getClientes().get(index));
		
		for (int index=age.getColaboradores().size(); index>-1; index--)
			
			if(!colaboradorIds.contains(age.getColaboradores().get(index)))
				age.getColaboradores().remove(age.getColaboradores().get(index));
	}

	@SuppressWarnings("unchecked")
	public Object listar(String busca) {
		
		String[] tipos = {"Visita", "Reunião"};
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

		List<TreeMap<?, ?>> res = new ArrayList<TreeMap<?, ?>>();
		
		Query q = null;
		Date data;
		try {
			data = (Date) sdf.parse(busca);
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			q = getEm().createQuery("select a from Agendamento a left join fetch a.clientes c left join fetch a.colaboradores co where a.data = ?1");
			q.setParameter(1, busca);
			
		} catch (ParseException e) {
			q = getEm().createQuery("select a from Agendamento a left join fetch a.clientes c left join fetch a.colaboradores co where c.nome = ?1 or co.nome = ?1");
			q.setParameter(1, busca);
		}

		TreeMap<String, Object> mapa = null;
		List<Agendamento> agendamentos = q.getResultList();
		
		for (Agendamento agendamento : agendamentos) {
			mapa = new TreeMap<String, Object>();
			mapa.put("id", agendamento.getId());
			mapa.put("data", agendamento.getData());
			mapa.put("observacoes", agendamento.getObservacao());
			mapa.put("horario", agendamento.getHorario());
			mapa.put("tipo", tipos[agendamento.getAgendamentoTipo().ordinal()]);
			List<TreeMap<?, ?>> lista = new ArrayList<TreeMap<?,?>>();
			for(Cliente cliente: agendamento.getClientes()){
				TreeMap<String, Object> mapaCliente = new TreeMap<String, Object>();
				mapaCliente.put("nome", cliente.getNome());
				mapaCliente.put("tipo", "Cliente");
				lista.add(mapaCliente);
			}
			for(Colaborador colaborador: agendamento.getColaboradores()){
				TreeMap<String, Object> mapaColaborador = new TreeMap<String, Object>();
				mapaColaborador.put("nome", colaborador.getNome());
				mapaColaborador.put("tipo", "Colaborador");
				lista.add(mapaColaborador);
			}
			
			mapa.put("envolvidos", lista);
			res.add(mapa);
		}

		return res;

	}
	
	public Object editar(Long idAgendamento) {
		
		Agendamento a = em.find(Agendamento.class, idAgendamento);
		
		TreeMap<String, Object> res = new TreeMap<String, Object>();
		
		res.put("id", a.getId());
		res.put("data", a.getData());
		res.put("horario", a.getHorario());
		res.put("agendamentoTipo", a.getAgendamentoTipo());
		
		List<TreeMap<?, ?>> lista = new ArrayList<TreeMap<?,?>>();
		for(Cliente cliente: a.getClientes()){
			TreeMap<String, Object> mapaCliente = new TreeMap<String, Object>();
			mapaCliente.put("nome", cliente.getNome());
			mapaCliente.put("tipo", "Cliente");
			lista.add(mapaCliente);
		}
		for(Colaborador colaborador: a.getColaboradores()){
			TreeMap<String, Object> mapaColaborador = new TreeMap<String, Object>();
			mapaColaborador.put("nome", colaborador.getNome());
			mapaColaborador.put("tipo", "Colaborador");
			lista.add(mapaColaborador);
		}
		
		res.put("envolvidos", lista);
		
		return res;
		
	}
	
	public void excluir(Long id) {
		
		em.remove(em.find(Agendamento.class, id));
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
