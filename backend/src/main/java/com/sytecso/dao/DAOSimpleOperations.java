package com.sytecso.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;



public interface DAOSimpleOperations<T>{
	@Transactional(readOnly = true)
	public Optional<T> findById(Long id);

	@Transactional(readOnly = true)
	public Optional<List<T>> findAll();

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(Long id);

	@Transactional(rollbackFor = Exception.class)
	public Optional<T> create(T object);

	@Transactional(rollbackFor = Exception.class)
	public boolean create(T object, Object[] params);
	
	@Transactional(rollbackFor = Exception.class)
	public Optional<List<T>> create(List<T> object);

	@Transactional(rollbackFor = Exception.class)
	public Optional<T> updateById(Long id);

	@Transactional(rollbackFor = Exception.class)
	public boolean updateAll(List<T> object);
}
