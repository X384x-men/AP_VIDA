package com.sytecso.dao.usuario.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.beans.UsuarioCuenta;
import com.sytecso.dao.usuario.DAOUsuarioCuenta;

@Repository
public class DAOUsuarioCuentaImpl implements DAOUsuarioCuenta{
	@Autowired
	private JdbcTemplate jdbcTemplate;
    private static final String REGISTRAR_USUARIO_CUENTA = "INSERT INTO usuariocuenta (login, password, nivelCuenta, status) VALUES (?, MD5(?), ?, ?)";
    private static final String ACTUALIZAR_USUARIO_CUENTA = "UPDATE usuariocuenta SET login=?, nivelCuenta=?, status=? WHERE idusuarioCuenta=?";
    private static final String ACTUALIZAR_USUARIO_CUENTA_PWD = "UPDATE usuariocuenta SET login=?, password=MD5(?), nivelCuenta=?, status=? WHERE idusuarioCuenta=?";
    private static final String TRAER_USUARIO_CUENTA = "SELECT idusuarioCuenta, login, password, nivelCuenta FROM usuarioCuenta WHERE idusuarioCuenta = ?";
    private static final String VALIDA_USUARIO_PASSWORD = "SELECT idusuarioCuenta, login, password, nivelCuenta FROM usuarioCuenta WHERE login = ? AND status=1";
    private static final String CANCELA_USUARIO_CUENTA = "UPDATE usuariocuenta SET status=11 WHERE idusuarioCuenta=?";
    private static final String SUSPENDE_USUARIO_CUENTA = "UPDATE usuariocuenta SET status=10 WHERE idusuarioCuenta=?";

    @Autowired
    private DataSource dataSource;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registrarUsuarioCuenta(UsuarioCuenta usuarioCuenta) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(REGISTRAR_USUARIO_CUENTA);
            preparedStatement.setString(1, usuarioCuenta.getLogin());
            preparedStatement.setString(2, usuarioCuenta.getPassword());
            preparedStatement.setString(3, usuarioCuenta.getNivelCuenta());
            preparedStatement.setInt(4, 1);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void actualizarUsuarioCuenta(UsuarioCuenta usuarioCuenta) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(ACTUALIZAR_USUARIO_CUENTA);
            preparedStatement.setString(1, usuarioCuenta.getLogin());
            preparedStatement.setString(2, usuarioCuenta.getNivelCuenta());
            preparedStatement.setInt(3, usuarioCuenta.getStatus());
            preparedStatement.setInt(4, usuarioCuenta.getIdUsuarioCuenta());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void actualizarUsuarioCuentaPwd(UsuarioCuenta usuarioCuenta) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(ACTUALIZAR_USUARIO_CUENTA_PWD);
            preparedStatement.setString(1, usuarioCuenta.getLogin());
            preparedStatement.setString(2, usuarioCuenta.getPassword());
            preparedStatement.setString(3, usuarioCuenta.getNivelCuenta());
            preparedStatement.setInt(4, usuarioCuenta.getStatus());
            preparedStatement.setInt(5, usuarioCuenta.getIdUsuarioCuenta());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelarUsuarioCuenta(int idUsuarioCuenta) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(CANCELA_USUARIO_CUENTA);
//            preparedStatement.setInt(1, usuarioCuenta.getStatus());
            preparedStatement.setInt(1, idUsuarioCuenta);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspenderUsuarioCuenta(int idUsuarioCuenta) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SUSPENDE_USUARIO_CUENTA);
            preparedStatement.setInt(1, idUsuarioCuenta);
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
    
    @Override
    @Transactional(readOnly=true)
    public UsuarioCuenta traerCamposUsuarioCuenta(int idUsuarioCuenta) throws Exception {
        UsuarioCuenta usuarioCuenta = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(TRAER_USUARIO_CUENTA);
            preparedStatement.setInt(1, idUsuarioCuenta);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                usuarioCuenta = new UsuarioCuenta();
                usuarioCuenta.setIdUsuarioCuenta(resultSet.getInt("idusuarioCuenta"));
                usuarioCuenta.setLogin(resultSet.getString("login"));
                usuarioCuenta.setPassword(resultSet.getString("password"));
                usuarioCuenta.setNivelCuenta(resultSet.getString("nivelCuenta"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return usuarioCuenta;
    }

    @Override
    @Transactional(readOnly=true)
    public UsuarioCuenta traerUsuarioPorUsername(String username) throws Exception {
        UsuarioCuenta usuarioCuenta = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareCall(VALIDA_USUARIO_PASSWORD);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                usuarioCuenta = new UsuarioCuenta();
                usuarioCuenta.setIdUsuarioCuenta(resultSet.getInt("idusuarioCuenta"));
                usuarioCuenta.setLogin(resultSet.getString("login"));
                usuarioCuenta.setPassword(resultSet.getString("password"));
                usuarioCuenta.setNivelCuenta(resultSet.getString("nivelCuenta"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
        return usuarioCuenta;
    }
    
    
//	public List<UsuarioCuenta> traerTodosUsuarios() {
//		List<UsuarioCuenta> lstUsuarios = new ArrayList<UsuarioCuenta>();
//			List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM usuariocuenta");
//			for (Map<?, ?> row : rows) {
//				UsuarioCuenta usr = new UsuarioCuenta();
//				usr.setIdUsuarioCuenta(((Long)row.get("idusuarioCuenta")).intValue());
//				usr.setLogin((String)row.get("login"));
//				usr.setPassword((String)row.get("password"));
//				usr.setNivelCuenta((String)row.get("nivelCuenta"));
//				lstUsuarios.add(usr);
//			}
//		return lstUsuarios;
//	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @Transactional(readOnly=true)
	public List<UsuarioCuenta> traerTodosUsuarios(){
//		System.out.println("LLAMADO  MK->     List<UsuarioCuenta> traerTodosUsuarios()");
		String sql = "SELECT idusuariocuenta,login,password,case WHEN UPPER(TRIM(UCTA.nivelcuenta))='ADMIN' THEN 'PERFIL - ADMINISTRACION ' WHEN UPPER(TRIM(UCTA.nivelcuenta))='BILLING' THEN 'PERFIL - FACTURACION ' WHEN UPPER(TRIM(UCTA.nivelcuenta))='CONFIG' THEN 'PERFIL - CONFIGURACION ' WHEN UPPER(TRIM(UCTA.nivelcuenta))='USER' THEN 'PERFIL - USUARIO ' ELSE UCTA.nivelcuenta END as nivelcuenta,status FROM usuariocuenta UCTA";
		List<UsuarioCuenta> usuarios=jdbcTemplate.query(sql,new BeanPropertyRowMapper(UsuarioCuenta.class));
		return usuarios;
	}
	@Override
    @Transactional(readOnly=true)
	public UsuarioCuenta traerUsuario(String userID) {
//		UsuarioCuenta rows = jdbcTemplate.queryForObject(sql, UsuarioCuenta new());
		String sql = "SELECT idusuarioCuenta, login, password, nivelCuenta, status FROM usuariocuenta WHERE idusuarioCuenta = ?";
		UsuarioCuenta user=(UsuarioCuenta)jdbcTemplate.queryForObject(sql, new Object[]{userID},new BeanPropertyRowMapper<UsuarioCuenta>(UsuarioCuenta.class));
		return user;
	}
}
