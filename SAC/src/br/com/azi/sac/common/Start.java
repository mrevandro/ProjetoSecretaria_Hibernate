package br.com.azi.sac.common;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.azi.sac.service.AgendamentoService;
import br.com.azi.sac.service.ClienteService;
import br.com.azi.sac.service.ColaboradorService;
import br.com.azi.sac.service.RecadoService;

@ApplicationPath("/ws")
public class Start extends Application{
	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> classes = new HashSet<Class<?>>();
	    
		//Registrando WebServices Rest
		classes.add(RecadoService.class);
		classes.add(AgendamentoService.class);
		classes.add(ClienteService.class);
		classes.add(ColaboradorService.class);
		return classes;
	}
}
