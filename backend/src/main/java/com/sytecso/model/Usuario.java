package com.sytecso.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Table
@Entity(name = "Usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -4525549258058093360L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario")
	private Long id;

	@Column(name = "fechaCreacion", insertable = false)
	private Date fechaCreacion;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<UsuarioAcceso> user;


	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @return the user
	 */
	public Set<UsuarioAcceso> getUser() {
		return user;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Set<UsuarioAcceso> user) {
		this.user = user;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


}
