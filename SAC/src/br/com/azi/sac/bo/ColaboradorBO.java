package br.com.azi.sac.bo;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.enterprise.context.Dependent;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.azi.sac.entity.Colaborador;

@Dependent
@Transactional
public class ColaboradorBO {

	@PersistenceContext(unitName = "recepcao")
	private EntityManager em;

	public Object salvar(JsonObject obj) {
		Colaborador c = null;

		SimpleDateFormat format = new SimpleDateFormat("HH:MM:SS");
		try {

			if (obj.isNull("id"))
				c = new Colaborador();
			else
				c = getEm().find(Colaborador.class, obj.getJsonNumber("id").longValue());

			c.setNome(!obj.isNull("nome") ? obj.getJsonString("nome").getString() : null);
			c.setRamal(!obj.isNull("ramal") ? obj.getInt("ramal") : null);
			c.setTelefone(!obj.isNull("telefone") ? obj.getJsonString("telefone").getString() : null);
			c.setCelular(!obj.isNull("celular") ? obj.getJsonString("celular").getString() : null);
			c.setAtivo(!obj.isNull("ativo") ? obj.getBoolean("ativo") : null);
			c.setHoraEntrada(!obj.isNull("horaEntrada") ? new Time(format.parse(obj.getJsonString("horaEntrada").getString()).getTime()) : null);
			c.setHoraSaida(!obj.isNull("horaSaida") ? new Time(format.parse(obj.getJsonString("horaSaida").getString()).getTime()) : null);
			c.setHoraInicioIntervalo(!obj.isNull("horaInicioIntervalo") ? new Time(format.parse(obj.getJsonString("horaInicioIntervalo").getString()).getTime()) : null);
			c.setHoraFinalIntervalo(!obj.isNull("horaFinalIntervalo") ? new Time(format.parse(obj.getJsonString("horaFinalIntervalo").getString()).getTime()) : null);

			c = em.merge(c);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	public Object listar(String busca) {

		List<TreeMap<?, ?>> res = new ArrayList<TreeMap<?, ?>>();
		Query q = getEm().createQuery("select c from Colaborador c where c.nome = ?1 order by c.nome");
		q.setParameter(1, busca);

		TreeMap<String, Object> mapa = null;
		List<Colaborador> colaboradores = q.getResultList();

		for (Colaborador colaborador : colaboradores) {
			mapa = new TreeMap<String, Object>();
			mapa.put("id", colaborador.getId());
			mapa.put("nome", colaborador.getNome());
			mapa.put("ramal", colaborador.getRamal());
			mapa.put("telefone", colaborador.getTelefone());
			mapa.put("celular", colaborador.getCelular());
			mapa.put("ativo", colaborador.getAtivo());
			mapa.put("horaEntrada", colaborador.getHoraEntrada());
			mapa.put("horaSaida", colaborador.getHoraSaida());
			mapa.put("horaInicioIntervalo", colaborador.getHoraInicioIntervalo());
			mapa.put("horaFinalIntervalo", colaborador.getHoraFinalIntervalo());
			res.add(mapa);
		}

		return res;

	}

	public Object editar(Long idColaborador) {

		Colaborador c = getEm().find(Colaborador.class, idColaborador);

		TreeMap<String, Object> res = new TreeMap<String, Object>();

		res.put("id", c.getId());
		res.put("nome", c.getNome());
		res.put("ramal", c.getRamal());
		res.put("telefone", c.getTelefone());
		res.put("celular", c.getCelular());
		res.put("ativo", c.getAtivo());
		res.put("horaEntrada", c.getHoraEntrada());
		res.put("horaSaida", c.getHoraSaida());
		res.put("horaInicioIntervalo", c.getHoraInicioIntervalo());
		res.put("horaFinalIntervalo", c.getHoraFinalIntervalo());

		return res;

	}

	public void excluir(Long id) {

		getEm().remove(getEm().find(Colaborador.class, id));

	}

	@SuppressWarnings("unchecked")
	public List<Colaborador> buscaColaboradores(String busca) {
		busca = busca.replaceAll(" ", "%") + "%";
		Query q = em.createQuery("select c from Colaborador c where c.nome like ?1 ");
		q.setParameter(1, busca);
		
		List<Colaborador> colaboradores = q.getResultList();
		
		return colaboradores;
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
