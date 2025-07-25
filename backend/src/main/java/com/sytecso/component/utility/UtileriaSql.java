package com.sytecso.component.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.sytecso.config.logger.SytecsoLogger;
import com.sytecso.component.exceptions.SytecsoController;

public class UtileriaSql {
	private UtileriaSql() {
		throw new IllegalStateException("UtileriaSql");
	}

	public static final int BATCH_SIZE = 50;





	public static void closeConection(Connection connection, PreparedStatement preparedStatement) {
		try {
			if (connection != null)
				connection.close();
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (Exception e) {
			SytecsoLogger.info("OCURRIO UN ERRRO AL CERRAR LAS CONEXIONES A LA BD");
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void closeConection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if (connection != null)
				connection.close();
			if (preparedStatement != null)
				preparedStatement.close();
			if (resultSet != null)
				resultSet.close();
		} catch (Exception e) {
			SytecsoLogger.info("OCURRIO UN ERRRO AL CERRAR LAS CONEXIONES A LA BD");
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void closePreparedStatement(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void closeResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	

	public static List<Long> getGeneratedKeys(ResultSet resultSet) {
		List<Long> ids = new ArrayList<>();
		try {
			while (resultSet.next()) {
				ids.add(resultSet.getLong(1));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			closeResultSet(resultSet);
		}
		return ids;
	}

	public static void conectionRollBack(Connection connection) {
		try {
			connection.rollback();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void connectionCommit(boolean status, Connection connection) {
		try {
			if (status) {
				SytecsoLogger.info("Iniciando commit transaccional");
				connection.commit();
				SytecsoLogger.info("Terminando commit transaccional");
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void closeConnectionAndCommit(Connection connection, PreparedStatement preparedStatement,
			ResultSet resultSet, boolean estatus) {
		try {
			if (estatus) {
				connectionCommit(true, connection);
			} else {
				conectionRollBack(connection);
			}
			closeConection(connection, preparedStatement, resultSet);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}

	}
	public static void closeConnectionAndCommit(Connection connection, PreparedStatement preparedStatement, boolean estatus) {
		try {
			if (estatus) {
				connectionCommit(true, connection);
			} else {
				conectionRollBack(connection);
			}
			closeConection(connection, preparedStatement);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}

	}


	public static void closePreparedStatemetAndResultSet(PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
			if (resultSet != null)
				resultSet.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void sqlException() throws SQLException {
		throw new SQLException();
	}

	public static void closeConnection(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static void closeConnectionAndRollBack(Connection connection, boolean estatus) {
		try {
			if (!estatus)
				connection.rollback();
			if (estatus)
				connection.commit();
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	public static int validafindColumnResultSet(ResultSet resultSet, String nameColumn) {
		try {
			return resultSet.findColumn(nameColumn);

		} catch (SQLException e) {
			SytecsoLogger.error("La columna " + nameColumn + " no existe ", e);
		}

		return -1;

	}

	public static int flushAndClearTransaction(Session session, int index, int listSize, int currentIndex) {
		if (index % BATCH_SIZE == 0 || currentIndex == listSize) {
			session.flush();
			session.clear();
			return 1;
		}
		return index;
	}

	public static void flushAndClear(Session session, int index, int batchSize) {
		if (index % batchSize == 0) {
			session.flush();
			session.clear();
		}
	}

	public static String validaCampoByIndex(ResultSet resultSet, int indice) {
		try {
			String valor = resultSet.getString(indice);
			return valor != null ? valor : "";
		} catch (SQLException e) {
			return "";
		}
	}

	




}
