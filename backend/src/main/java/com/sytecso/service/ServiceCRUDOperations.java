package com.sytecso.service;

import java.util.List;
import java.util.Optional;


public interface ServiceCRUDOperations<T> {

	public Optional<T> findById(Long id);

	public Optional<List<T>> findAll();

	public boolean deleteById(Long id);

	public Optional<T> create(T object);
	
	public Optional<List<T>> create(List<T> object);

	public boolean create(T object, int option);

	public Optional<T> updateById(Long id);

	public boolean updateAll(List<T> object);
}
