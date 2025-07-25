package com.sytecso.component.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import com.sytecso.config.menu.Menu;
import com.sytecso.config.menu.MenuProperties;
import com.sytecso.config.menu.Pantalla;
import com.sytecso.config.menu.SubMenu;
import com.sytecso.model.CatalogoIconos;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.model.Seccion;

public class UtileriaAcceso {

	protected final String getRol(GrantedAuthority grantedAuthority) {
		if (grantedAuthority != null && grantedAuthority.getAuthority() != null
				&& !grantedAuthority.getAuthority().isEmpty()) {
			String authority = grantedAuthority.getAuthority();
			int start = authority.indexOf('_') + 1;
			int end = authority.length();
			return authority.substring(start, end);
		}
		return "";
	}

	/**
	 * Este metodo remueve el baseHref de una url, el baseHref es la ruta principal
	 * de la aplucacion
	 * 
	 * @Param url
	 * @return String
	 **/
	protected final String removeBaseHrefPath(String url, List<String> urls) {
		List<String> params = new ArrayList<>(Arrays.asList(url.split("/")));
		StringBuilder builder = new StringBuilder();
		for (String param : params) {
			if (!param.isEmpty()
					&& urls.stream().noneMatch(data -> data.replace("/", "").trim().equalsIgnoreCase(param))) {
				builder.append("/").append(param);
			}
		}
		builder = this.removeParamsFromUrl(builder);
		if (builder.length() > 0)
			return builder.substring(1, builder.length());
		return null;
	}

	protected final boolean validateDefaultAccess(String authority, String url, Map<String, List<String>> access) {
		if (access.containsKey(authority)) {
			return access.get(authority).stream().anyMatch(data -> data.equals(url));
		}
		return false;
	}

	protected final String removeOnlyBaseHrefPath(String url, List<String> urls) {
		StringBuilder builder = new StringBuilder();
		List<String> params = new ArrayList<>(Arrays.asList(url.split("/")));
		for (String param : params) {
			if (!param.isEmpty()
					&& urls.stream().noneMatch(data -> data.replace("/", "").trim().equalsIgnoreCase(param))) {
				builder.append("/").append(param);
			}
		}
		if (builder.toString().startsWith("/"))
			return builder.toString().substring(1, builder.length());
		return builder.toString();
	}

	/**
	 * Remueve los parametros que aparecen en una url, ejemplo:
	 * /administracion-cuadrillas/cuadrilla?numero=123456&malo=7848&yo=si resulta en
	 * la siguiente cadena /administracion-cuadrillas/cuadrilla?numero&malo&yo
	 * 
	 * @param url
	 * @return StringBuilder
	 **/
	protected final StringBuilder removeParamsFromUrl(StringBuilder url) {
		String params = url.toString();
		if (params.indexOf('=') >= 0) {
			StringBuilder p = new StringBuilder();
			String[] s = params.split("=");
			for (int i = 0; i < s.length; i++) {
				String data = s[i];
				if (data.contains("&")) {
					p.append(data.substring(data.indexOf('&'), data.length()));
				} else if (i == 0) {
					p.append(data);
				}
			}
			return p;
		}
		return url;
	}

	protected final List<String> getRol(SecurityContext context) {
		Set<String> rol = new HashSet<>();
		if (context == null)
			return new ArrayList<>();
		if (context.getAuthentication() == null)
			return new ArrayList<>();
		if (context.getAuthentication().getAuthorities() == null
				|| context.getAuthentication().getAuthorities().isEmpty())
			return new ArrayList<>();
		context.getAuthentication().getAuthorities().stream().forEach(data -> rol.add(this.getRol(data)));
		return new ArrayList<>(rol);
	}

	/**
	 * Comprueba si un rol esta presente en una coleccion de GrantedAuthority
	 * 
	 * @param authorities
	 * @param authoritieValue
	 * @return true si esta presente, false si no esta presente
	 **/
	protected final boolean isAuthoritiePresent(Collection<? extends GrantedAuthority> authorities,
			final String authoritieValue) {
		return !authorities.stream().filter(authoritie -> this.getRol(authoritie).equals(authoritieValue))
				.collect(Collectors.toList()).isEmpty();
	}

	protected ModulosGui createModulos(Menu menu) {
		ModulosGui modulosGui = new ModulosGui();
		modulosGui.setNombre(menu.getMenuTitle());
		modulosGui.setUrl(menu.getUrl());
		modulosGui.setEnabled(menu.getEnabled());
		modulosGui.setIndex(menu.getIndex());
		modulosGui.setSeccion(this.createSecciones(menu.getSubMenu(), modulosGui));
		modulosGui.setCatalogoIconos(this.createCatalogo(menu, modulosGui));
		return modulosGui;
	}

	protected ModulosGui createModulos(Menu modulo, ModulosGui gui) {
		ModulosGui modulosGui = new ModulosGui();
		modulosGui.setId(gui.getId());
		modulosGui.setNombre(gui.getNombre());
		modulosGui.setUrl(gui.getUrl());
		modulosGui.setEnabled(gui.getEnabled());
		modulosGui.setIndex(gui.getIndex());
		modulosGui.setSeccion(this.createSecciones(modulo.getSubMenu(), gui));
		modulosGui.setCatalogoIconos(this.createCatalogo(modulo, modulosGui));
		return modulosGui;
	}

	private Set<Seccion> createSecciones(List<SubMenu> secciones, ModulosGui modulosGui) {
		if (secciones == null || secciones.isEmpty())
			return new HashSet<>();
		Set<Seccion> seccionesGui = new HashSet<>();

		for (SubMenu subMenu : secciones) {
			Optional<Seccion> entity = modulosGui.getSeccion() != null && !modulosGui.getSeccion().isEmpty()
					? modulosGui.getSeccion().stream().distinct()
							.filter(modulo -> modulo.getNombre().equals(subMenu.getName())).findFirst()
					: Optional.empty();
			Seccion seccion = new Seccion();
			seccion.setDescripcion(subMenu.getDescripcion());
			seccion.setUrl(subMenu.getUrl());
			seccion.setNombre(subMenu.getName());
			seccion.setIndex(subMenu.getIndex());
			seccion.setEnabled(subMenu.getEnabled());
			seccion.setPantallasAsignadas(this.createPantallasAsigadas(subMenu.getPantallas(), seccion, entity));
			seccion.setModulosGui(modulosGui);
			seccion.setCatalogoIconos(this.createCatalogo(subMenu, seccion));
			seccion.setId(entity.isPresent() ? entity.get().getId() : null);
			seccionesGui.add(seccion);
		}
		return seccionesGui;

	}

	private Set<PantallasAsignadas> createPantallasAsigadas(List<Pantalla> menu, Seccion seccion,
			Optional<Seccion> optional) {
		if (menu == null || menu.isEmpty())
			return new HashSet<>();
		Set<PantallasAsignadas> pantallasSet = new HashSet<>();
		for (MenuProperties menuProperties : menu) {
			PantallasAsignadas pantallasAsignadas = new PantallasAsignadas();
			Optional<PantallasAsignadas> entity = optional.isPresent() && optional.get().getPantallasAsignadas() != null
					&& !optional.get().getPantallasAsignadas().isEmpty()
							? optional.get().getPantallasAsignadas().stream().distinct()
									.filter(modulo -> modulo.getNombre().equals(menuProperties.getName())).findFirst()
							: Optional.empty();
			pantallasAsignadas.setId(entity.isPresent() ? entity.get().getId() : null);
			pantallasAsignadas.setSeccion(seccion);
			pantallasAsignadas.setCatalogoIconos(this.createCatalogo(menuProperties, pantallasAsignadas));
			pantallasAsignadas.setDescripcion(menuProperties.getDescripcion());
			pantallasAsignadas.setNombre(menuProperties.getName());
			pantallasAsignadas.setIndex(menuProperties.getIndex());
			pantallasAsignadas.setUrl(menuProperties.getUrl());
			pantallasAsignadas.setEnabled(menuProperties.getEnabled());
			pantallasAsignadas.setServicios(menuProperties.getServicios().split(","));
			pantallasAsignadas.setPantallaAsignadaHasCatalogoServicios(new HashSet<>());
			pantallasSet.add(pantallasAsignadas);
		}
		return pantallasSet;
	}

	private CatalogoIconos createCatalogo(MenuProperties catalogo, PantallasAsignadas pantallasAsignadas) {
		Set<PantallasAsignadas> pantallas = new HashSet<>();
		pantallas.add(pantallasAsignadas);
		CatalogoIconos catalogoIconos = new CatalogoIconos();
		catalogoIconos.setNombre(catalogo.getIcon());
		catalogoIconos.setPantallaAsignada(pantallas);
		return catalogoIconos;
	}

	private CatalogoIconos createCatalogo(MenuProperties catalogo, ModulosGui modulosGui) {
		CatalogoIconos catalogoIconos = new CatalogoIconos();
		Set<ModulosGui> secciones = new HashSet<>();
		secciones.add(modulosGui);
		catalogoIconos.setNombre(catalogo.getIcon());
		catalogoIconos.setModulosGui(secciones);
		return catalogoIconos;
	}

	private CatalogoIconos createCatalogo(MenuProperties catalogo, Seccion seccion) {
		CatalogoIconos catalogoIconos = new CatalogoIconos();
		Set<Seccion> secciones = new HashSet<>();
		secciones.add(seccion);
		catalogoIconos.setNombre(catalogo.getIcon());
		catalogoIconos.setSeccion(secciones);
		return catalogoIconos;
	}


}
