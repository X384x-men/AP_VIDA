package com.sytecso.component.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;


import com.sytecso.component.exceptions.SytecsoController;


public class NestedRowMapperList<T> implements RowMapper<List<T>> {

	private Class<T> mappedClass;

	public NestedRowMapperList(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
	}

	@Override
	public List<T> mapRow(ResultSet rs, int rowNum) throws SQLException {

//		T mappedObject = BeanUtils.instantiate(this.mappedClass);
		try {
			List<T> list = new ArrayList<>();
			T mappedObject = this.mappedClass.newInstance();
			BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);

			bw.setAutoGrowNestedPaths(true);

			ResultSetMetaData meta_data = rs.getMetaData();
			int columnCount = meta_data.getColumnCount();
			while (rs.next()) {
				for (int index = 1; index <= columnCount; index++) {
					String column = JdbcUtils.lookupColumnName(meta_data, index);
					Object value = JdbcUtils.getResultSetValue(rs, index,Class.forName(meta_data.getColumnClassName(index)));
					bw.setPropertyValue(column, value);
				}
				list.add(mappedObject);
			}
			
			return list;
		} catch (TypeMismatchException | NotWritablePropertyException | ClassNotFoundException e) {
			SytecsoController.logClassAndMethodWithException(e);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}
}