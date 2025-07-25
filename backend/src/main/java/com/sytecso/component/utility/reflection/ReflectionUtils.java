package com.sytecso.component.utility.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.sytecso.component.utility.UtileriaFechas;
import com.sytecso.component.exceptions.SytecsoController;

public  class ReflectionUtils {
	private ReflectionUtils() {
		throw new IllegalStateException("This class cannot be instanced");
	}
	public static List<Object> createObject(Class<?> clazz, List<LinkedHashMap<String, ?>> map)
			throws InstantiationException, IllegalAccessException {
		List<Object> menus = new ArrayList<>();
		for (LinkedHashMap<String, ?> linkedHashMap : map) {
			Object object = clazz.newInstance();
			for (Entry<String, ?> entry : linkedHashMap.entrySet()) {
				ReflectionUtils.readAndSetObjectProperties(clazz, object, entry);
			}
			menus.add(object);
		}
		return menus;
	}

	/**
	 * @param clazz
	 * @param object
	 * @param entry
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static void readAndSetObjectProperties(Class<?> clazz, Object object, Entry<String, ?> entry)
			throws InstantiationException, IllegalAccessException {
		if (entry.getValue() instanceof List) {
			Class<?> returnType = ReflectionUtils.getSetterValue(clazz, entry.getKey());
			if (returnType != null) {
				List<Object> subObjs = ReflectionUtils.creteListObject(entry, returnType);
				ReflectionUtils.setObjectProperty(object, entry.getKey(), subObjs);
			}
		} else {
			ReflectionUtils.setObjectProperty(object, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * @param entry
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	private  static List<Object> creteListObject(Entry<String, ?> entry, Class<?> typeObject)
			throws InstantiationException, IllegalAccessException {
		List<?> m = (List<?>) entry.getValue();
		List<Object> list = new ArrayList<>();
		for (Object object : m) {
			Object data = typeObject.newInstance();
			LinkedHashMap<String, ?> r = (LinkedHashMap<String, ?>) object;
			for (Entry<String, ?> l : r.entrySet()) {
				if (l.getValue() instanceof List) {
					ReflectionUtils.readAndSetObjectProperties(data.getClass(), data, l);
				} else {
					ReflectionUtils.setObjectProperty(data, l.getKey(), l.getValue());
				}
			}
			list.add(data);
		}
		return list;
	}

	private static void setObjectProperty(Object obj, String property, Object value) {
		try {
			Class<?> clazz = obj.getClass();
			Method[] methods = clazz.getMethods();
			String setterFiled = "set".concat(property);
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();
				if (methodName.equalsIgnoreCase(setterFiled)) {
					Object returnValue = ReflectionUtils.getRetunValue(clazz, property, value.getClass());
					if (returnValue != null) {
						Method calzzMethod = clazz.getMethod(methodName, value.getClass());
						Object argument = ReflectionUtils.castObject(returnValue, value.toString());
						calzzMethod.invoke(obj, argument);
						return;
					}
				}
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	private static void setObjectProperty(Object obj, String property, List<Object> value) {
		try {
			Class<?> clazz = obj.getClass();
			Method[] methods = clazz.getMethods();
			String setterFiled = "set".concat(property);
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();
				if (methodName.equalsIgnoreCase(setterFiled)) {
					Object returnValue = ReflectionUtils.getRetunValue(clazz, property, value.getClass().getInterfaces()[0]);
					if (returnValue != null) {
						Method calzzMethod = clazz.getMethod(methodName, value.getClass().getInterfaces()[0]);
						calzzMethod.invoke(obj, value);
						return;
					}
				}
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	private static Class<?> getSetterValue(Class<?> clazz, String property) {
		try {
			Method[] methods = clazz.getMethods();
			String returnField = "set".concat(property);
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();
				if (methodName.equalsIgnoreCase(returnField)) {
					Field stringListField = clazz.getDeclaredField(property);
					ParameterizedType stringListType = (ParameterizedType) stringListField.getGenericType();
					return Class.forName(stringListType.getActualTypeArguments()[0].getTypeName());
				}
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	private static Object getRetunValue(Class<?> clazz, String property, Class<?> value) {
		try {
			Method[] methods = clazz.getMethods();
			String returnField = "get".concat(property);
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();
				Object obj = method.getReturnType();
				if (methodName.equalsIgnoreCase(returnField) && obj.getClass().isInstance(value)) {
					return obj;
				}
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	private static Object castObject(Object clazz, String obj) {
		if (clazz instanceof BigDecimal) {
			return new BigDecimal(obj);
		} else if (clazz.equals(Float.class)) {
			return new Float(obj);
		} else if (clazz.equals(Integer.class)) {
			return new Integer(obj);
		} else if (clazz.equals(Date.class)) {
			return UtileriaFechas.convertDateToGregorian(obj);
		} else if (clazz.equals(Boolean.class)) {
			return new Boolean(obj);
		}
		return StringUtils.stripAccents(obj);
	}
}
