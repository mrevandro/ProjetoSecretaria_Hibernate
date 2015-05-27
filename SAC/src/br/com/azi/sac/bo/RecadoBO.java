package br.com.azi.sac.bo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeMap;

import javax.enterprise.context.Dependent;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.azi.sac.entity.Cliente;
import br.com.azi.sac.entity.Colaborador;
import br.com.azi.sac.entity.Recado;

@Dependent
@Transactional
public class RecadoBO {

	@PersistenceContext(unitName = "recepcao")
	private EntityManager em;

	public Object salvar(JsonObject obj) {

		Recado r;

		if (obj.isNull("id"))
			r = new Recado();
		else
			r = em.find(Recado.class, obj.getJsonNumber("id").longValue());

		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			Date data = (Date) format.parse(obj.getJsonString("data").getString());
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);

			r.setData(!obj.isNull("data") ? cal : null);
			r.setUrgente(!obj.isNull("urgente") ? obj.getBoolean("urgente") : null);
			r.setConteudo(!obj.isNull("conteudo") ? obj.getJsonString("conteudo").getString() : null);
			r.setCliente(em.find(Cliente.class, obj.getJsonNumber("idCliente").longValue()));
			r.setColaborador(em.find(Colaborador.class, obj.getJsonNumber("idColaborador").longValue()));

			r = em.merge(r);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return r;
	}

	@SuppressWarnings("unchecked")
	public Object listar(String busca) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		List<TreeMap<?, ?>> res = new ArrayList<TreeMap<?, ?>>();

		Query q = null;
		java.util.Date data;
		try {
			data = (java.util.Date) sdf.parse(busca);
			Calendar cal = new GregorianCalendar();
			cal.setTime(data);
			q = getEm().createQuery("select r from Recado r left join fetch r.cliente c left join fetch r.colaborador co where r.data = ?1");
			q.setParameter(1, busca);

		} catch (ParseException e) {
			q = getEm().createQuery("select r from Recado r left join fetch r.cliente c left join fetch r.colaborador co where c.nome = ?1 or co.nome = ?1");
			q.setParameter(1, busca);
		}

		TreeMap<String, Object> mapa = null;
		List<Recado> recados = q.getResultList();

		for (Recado recado : recados) {
			mapa = new TreeMap<String, Object>();
			mapa.put("id", recado.getId());
			mapa.put("data", recado.getData());
			mapa.put("conteudo", recado.getConteudo());
			mapa.put("urgente", recado.getUrgente());
			mapa.put("de", recado.getCliente().getNome());
			mapa.put("para", recado.getColaborador().getNome());

			res.add(mapa);
		}

		return res;

	}

	public Object editar(Long id) {

		Recado r = em.find(Recado.class, id);
		
		TreeMap<String, Object> res = new TreeMap<String, Object>();
		
		res.put("id", r.getId());
		res.put("data", r.getData());
		res.put("conteudo", r.getConteudo());
		res.put("urgente", r.getUrgente());
		res.put("de", r.getCliente().getNome());
		res.put("para", r.getColaborador().getNome());
		
		return res;
	}
	

	public void excluir(Long id) {
		em.remove(em.find(Recado.class, id));
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


}
