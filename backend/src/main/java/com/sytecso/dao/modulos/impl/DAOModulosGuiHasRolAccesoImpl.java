package com.sytecso.dao.modulos.impl;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.modulos.DAOModulosGuiHasRolAcceso;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.SeccionHasRolAcceso;
import com.sytecso.service.seccion.ServiceSeccionHasRolesAcceso;

@Repository
public class DAOModulosGuiHasRolAccesoImpl implements DAOModulosGuiHasRolAcceso {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ServiceSeccionHasRolesAcceso serviceSeccionHasRolAcceso;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean remove(ModulosGuiHasRolesAcceso modulo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.remove(modulo);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean create(ModulosGuiHasRolesAcceso modulo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(modulo);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(Set<ModulosGuiHasRolesAcceso> modulos, Set<SeccionHasRolAcceso> secciones) {
		Session session = this.sessionFactory.getCurrentSession();
		int index = 0;
		int currentIndex = 0;
		for (Iterator<ModulosGuiHasRolesAcceso> iterator = modulos.iterator(); iterator.hasNext();) {
			try {
				ModulosGuiHasRolesAcceso modulosGuiHasRolesAcceso = iterator.next();
				index++;
				currentIndex++;
				session.saveOrUpdate(modulosGuiHasRolesAcceso);
				index = UtileriaSql.flushAndClearTransaction(session, index, modulos.size(), currentIndex);
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
				return;
			}
		}
		this.serviceSeccionHasRolAcceso.create(secciones);
	}
}
