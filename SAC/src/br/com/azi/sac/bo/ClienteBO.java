package br.com.azi.sac.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.enterprise.context.Dependent;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import br.com.azi.sac.entity.Cliente;
import br.com.azi.sac.entity.Logradouro;

@Dependent
@Transactional
public class ClienteBO {

	@PersistenceContext(unitName = "recepcao")
	private EntityManager em;

	public Cliente salvar(JsonObject jo) {
		Cliente c;
		
		if(jo.isNull("id"))
			c = new Cliente();
		else
			c = em.find(Cliente.class, jo.getJsonNumber("id").longValue());

		c.setNome( (!jo.isNull("nome") ? jo.getJsonString("nome").getString() : null) );
		c.setCelular( (!jo.isNull("celular") ? jo.getJsonString("celular").getString() : null) );
		c.setTelefone( (!jo.isNull("telefone") ? jo.getJsonString("telefone").getString() : null) );
		c.setRamoAtividade( (!jo.isNull("ramoAtividade") ? jo.getJsonString("ramoAtividade").getString() : null) );
		
		Integer idl = jo.getJsonObject("logradouro").getInt("id");
		Query q = getEm().createQuery("select l from Logradouro l where l.id=?1");
		q.setParameter(1, idl);
		Logradouro log = (Logradouro) q.getSingleResult();
		
		c.setLogradouro(log);
		
		c.setComplemento((!jo.isNull("complemento") ? jo.getJsonString("complemento").getString() : null));
		c.setNumero((!jo.isNull("numero") ? jo.getJsonString("numero").getString() : null));
		
		c = em.merge(c);
		
		return c;
	}
	
	@SuppressWarnings("unchecked")
	public Object listarClientes(String busca) {
		List<TreeMap<?,?>> res = new ArrayList<TreeMap<?, ?>>();
		Query q = em.createQuery("select c from Cliente c where c.nome = ?1 OR c.ramoAtividade = ?1 order by c.nome");
		q.setParameter(1, busca);
		
		TreeMap<String, Object> mapa = null;
		List<Cliente> clientes = q.getResultList();
		for(Cliente cliente : clientes) {
			mapa = new TreeMap<String, Object>();
			mapa.put("id", cliente.getId());
			mapa.put("nome", cliente.getNome());
			mapa.put("telefone", cliente.getTelefone());
			mapa.put("celular", cliente.getCelular());
			mapa.put("ramoAtividade", cliente.getRamoAtividade());
			mapa.put("endereco", cliente.getLogradouro().getNome() + ", " + cliente.getLogradouro().getBairro().getNome()
					+ ", " + cliente.getNumero() + ", " + cliente.getComplemento() + ", " + cliente.getLogradouro().getCep()
					+ ", " + cliente.getLogradouro().getBairro().getCidade().getNome() + ", " + cliente.getLogradouro().getBairro().getCidade().getUf().getSigla());
			res.add(mapa);
		}
		
		return res;
	}
	
	
	public Object editar(Long idCliente) {
		
		Cliente c = em.find(Cliente.class, idCliente);
		
		TreeMap<String, Object> res = new TreeMap<String, Object>();
		
		res.put("id", c.getId());
		res.put("nome", c.getNome());
		res.put("celular", c.getCelular());
		res.put("ramoAtividade", c.getRamoAtividade());
		
		return res;
		
	}

	public void excluir(Long id) {
		
		em.remove(em.find(Cliente.class, id));
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> buscaClientes(String busca) {
		busca = busca.replaceAll(" ", "%") + "%";
		Query q = em.createQuery("select c from Cliente c where c.nome like ?1 ");
		q.setParameter(1, busca);
		
		List<Cliente> clientes = q.getResultList();
		
		return clientes;
	}
	
	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	
}
