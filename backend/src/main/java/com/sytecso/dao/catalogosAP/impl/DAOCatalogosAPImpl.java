package com.sytecso.dao.catalogosAP.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.catalogosAP.DAOCatalogosAP;
import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.catalogosAP.DTOCatalogoDependencias;
import com.sytecso.dto.catalogosAP.DTOCatalogoUnidadAdministrativa;

@Repository
public class DAOCatalogosAPImpl implements DAOCatalogosAP {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<DTOCatalogoDependencias> getDependencias() {
		String query="select idcatalogoDependencias, Descripcion, conv(status, 2,10) from catalogodependencias";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		List<DTOCatalogoDependencias> dependencyList = new ArrayList<DTOCatalogoDependencias>();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			rs=pst.executeQuery();
			while (rs.next()) {
				DTOCatalogoDependencias dependencias = new DTOCatalogoDependencias();
				dependencias.setIdCatalogo(rs.getLong(1));
				dependencias.setDescripcionCatalogo(rs.getString(2));
				dependencias.setStatus(rs.getInt(3));
				dependencyList.add(dependencias);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return dependencyList;
	}

	@Override
	public List<DTOCatalogoUnidadAdministrativa> getUnidadesAdministrativas() {
		String query="select idCatalogoUnidadAdministrativa, descripcion, conv(status, 2,10) from catalogoUnidadAdministrativa";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		List<DTOCatalogoUnidadAdministrativa> unidades = new ArrayList<DTOCatalogoUnidadAdministrativa>();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			rs=pst.executeQuery();
			while (rs.next()) {
				DTOCatalogoUnidadAdministrativa unidad = new DTOCatalogoUnidadAdministrativa();
				unidad.setIdUnidadAdministrativa(rs.getLong(1));
				unidad.setDescripcion(rs.getString(2));
				unidad.setStatus(rs.getInt(3));
				unidades.add(unidad);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return unidades;
	}

	@Override
	public List<DTOCatalogoConceptos> getConceptos() {
		String query=" select idConcepto, descripcion, conv(status, 2,10) from catalogoconceptos";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		List<DTOCatalogoConceptos> conceptos = new ArrayList<DTOCatalogoConceptos>();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			rs=pst.executeQuery();
			while (rs.next()) {
				DTOCatalogoConceptos concepto = new DTOCatalogoConceptos();
				concepto.setIdCatalogoConceptos(rs.getLong(1));
				concepto.setDescripcion(rs.getString(2));
				concepto.setStatus(rs.getInt(3));
				conceptos.add(concepto);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return conceptos;
	}

	@Override
	public DTOCatalogoDependencias getDependencyUser(String rfc) {
		String query="select dependencia from empleado_ap where RFC=? ";
		Connection con = null;
		ResultSet rs=null;
		PreparedStatement pst = null;
		DTOCatalogoDependencias dependencia= new DTOCatalogoDependencias();
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.setString(1, rfc);
			rs=pst.executeQuery();
			while (rs.next()) {
				dependencia.setDescripcionCatalogo(rs.getString(1));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return dependencia;
	}

	@Override
	public boolean updateCatalogoDependenciasString(int indice, String descripcion) {
		String query="update catalogodependencias set descripcion='"+descripcion+"' "
				+ "where  idcatalogoDependencias="+indice+"";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean updateCatalogoUnidadesString(int indice, String descripcion) {
		String query="update catalogounidadadministrativa set descripcion='"+descripcion+"' "
				+ "where  idCatalogoUnidadAdministrativa="+indice+"";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean updateCatalogoConceptosString(int indice, String descripcion) {
		String query="update catalogoConceptos set descripcion='"+descripcion+"' "
				+ "where  idConcepto="+indice+"";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}
	
	@Override
	public boolean updateCatalogoDependenciasStatus(int indice,int estatus) {
		String query="update catalogodependencias set status=conv("+estatus+",10,2) "
				+ "where  idCatalogoDependencias="+indice+"";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean updateCatalogoUnidadesStatus(int indice, int estatus) {
		String query="update catalogounidadadministrativa set status=conv("+estatus+",10,2) "
				+ "where  idCatalogoUnidadAdministrativa="+indice+"";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean updateCatalogoConceptosStatus(int indice, int estatus) {
		String query="update catalogoConceptos set status=conv("+estatus+",10,2) "
				+ "where  idConcepto="+indice+"";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(query);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean insertCatalogoDependencia(String descripcion) {
		String sql= "INSERT INTO catalogodependencias (Descripcion) VALUES ('"+descripcion+"') ";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean inserCatalogoUnidadAdministratuva(String descripcion) {
		
		String sql = " INSERT INTO catalogounidadadministrativa(descripcion) VALUES ('"+descripcion+"') ";
		// TODO Auto-generated method stub
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public boolean inserCatalogoCatalogoConceptos(String descripcion) {
		// corrección de servicios
		String sql=" INSERT INTO catalogoConceptos (descripcion) VALUES ('"+descripcion+"') ";
		Connection con = null;
		ResultSet rs=null;
		boolean status=true;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
			status=false;
		}finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}


}