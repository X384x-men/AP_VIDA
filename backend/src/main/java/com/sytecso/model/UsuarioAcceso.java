package com.sytecso.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "usuariosAcceso")
public class UsuarioAcceso implements Serializable {
	private static final long serialVersionUID = 2328142311084818382L;

	public UsuarioAcceso() {
	}

	public UsuarioAcceso(@NotBlank String usuario, @NotBlank String password, String tipoAcceso) {
		super();
		this.usuario = usuario;
		this.password = password;
		this.tipoAcceso = tipoAcceso;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuariosAcceso")
	private Long id;

	@Column(name = "usuario")
	@NotBlank
	private String usuario;

	@Column(name = "pwd")
	@NotBlank
	private String password;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "tipoAcceso")
	private String tipoAcceso;

	@Column(name = "fechaCreacion")
	private Date fechaCreacion;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "estatus")
	private String estatus;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Usuario_idUsuario", referencedColumnName = "idUsuario")
	private Usuario user;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rolesAcceso_idrolesAcceso", referencedColumnName = "idRolesAcceso")
	private RolAcceso rolesAcceso;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the user
	 */
	public Usuario getUser() {
		return user;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Usuario user) {
		this.user = user;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the rolesAcceso
	 */
	public RolAcceso getRolesAcceso() {
		return rolesAcceso;
	}

	/**
	 * @param rolesAcceso the rolesAcceso to set
	 */
	public void setRolesAcceso(RolAcceso rolesAcceso) {
		this.rolesAcceso = rolesAcceso;
	}

}
