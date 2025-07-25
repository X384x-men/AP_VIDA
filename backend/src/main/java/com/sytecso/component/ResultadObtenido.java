/**
 * 
 * Created-By: Sytecso
 * Date:       01/01/2019
 */
package com.sytecso.component;

import java.util.ArrayList;
import java.util.Iterator;

public class ResultadObtenido <T extends Object> implements Iterable<T> {
	private ArrayList<T> lista = new ArrayList<T>();

	public void add(T t) {
		lista.add(t);
	}

	public Iterator<T> iterator() {
		return lista.iterator();
	}
}
