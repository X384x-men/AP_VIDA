package com.sytecso.component.exceptions;

import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.sytecso.component.utility.UtileriaErrorDetails;
import com.sytecso.config.logger.SytecsoLogger;
import com.sytecso.dto.errors.ErrorDetails;
import com.sytecso.component.exceptions.MainException.DataAsociateExistsException;
import com.sytecso.component.exceptions.RolAccesoException.RolCannotNotAsocciateViewException;

@RestController
@ControllerAdvice(basePackages = "com.sytecso.controller")
public class SytecsoController {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class, EmptyResultDataAccessException.class })
	public ResponseEntity<ErrorDetails> handleError(HttpServletRequest httpServletRequest, Exception e) {
		return UtileriaErrorDetails.sendError("The method was not supported", e, HttpStatus.BAD_REQUEST,
				"Metodo no soportado");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CuadrillaHasTipoOrdenException.NotDeletedException.class)
	public ResponseEntity<ErrorDetails> cuadrillaHasTipoOrdenException(Exception e) {
		return UtileriaErrorDetails.sendError("Data not found", e, HttpStatus.BAD_REQUEST, "El dato no fue encontrado");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(DataAsociateExistsException.class)
	public ResponseEntity<ErrorDetails> sqlIntegrityConstraintViolationException(Exception e) {
		return UtileriaErrorDetails.sendError("Data not found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ RolAccesoException.NotRolesFoundException.class, RolCannotNotAsocciateViewException.class,
			RolAccesoException.RolNotUpdatedException.class, RolAccesoException.RolNotCreatedException.class,
			RolAccesoException.RolExistsException.class, MenuException.SeccionNotExistsException.class,
			RolAccesoException.RolNotExistsException.class, MenuException.SeccionNotCreatedException.class,
			MenuException.SeccionNotRemovedException.class })
	public ResponseEntity<ErrorDetails> rolAccesoException(Exception e) {
		return UtileriaErrorDetails.sendError("Data not found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> accessDeniedException(Exception e) {
		return UtileriaErrorDetails.sendError("Session not started", e, HttpStatus.UNAUTHORIZED,
				"Debes iniciar sesion");

	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> badCredentialsException(Exception e) {
		return UtileriaErrorDetails.sendError("Usuario o password incorrectos", e, HttpStatus.BAD_REQUEST,
				"El usuario o password son incorrectos");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EvidenciaOrdenException.EvidenciaNotFoundException.class)
	public ResponseEntity<ErrorDetails> evidenciaOrdenException(Exception e) {
		return UtileriaErrorDetails.sendError("Data not found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ InstaladorException.RolNotValidException.class,
			InstaladorException.RolInstaladorAlreadyExistsException.class,
			InstaladorException.NumeroEmpleadoNotValidException.class,
			InstaladorException.NumeroEmpleadoAlreadyExistsException.class,
			InstaladorException.NotInstaladorUpdatedException.class, InstaladorException.RolNotUpdatedException.class })
	public ResponseEntity<ErrorDetails> numeroInstaladorInstaladorAlreadyExistsException(Exception e) {
		return UtileriaErrorDetails.sendError("data already exists", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ InstaladorException.EmptyRolException.class,
			InstaladorException.EmptyNumeroEmpleadoException.class })
	public ResponseEntity<ErrorDetails> emptyInstaladorData(Exception e) {
		return UtileriaErrorDetails.sendError("rol cannot be empty", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(InstaladorException.NotInstaladorDeletedException.class)
	public ResponseEntity<ErrorDetails> notInstaladorDeletedException(Exception e) {
		return UtileriaErrorDetails.sendError("Data not found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(InstaladorException.NotInstaladoresFoudException.class)
	public ResponseEntity<ErrorDetails> notInstaladoresFoudException(Exception e) {
		return UtileriaErrorDetails.sendError("Data not found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GeoCercaException.GeoCercaNotHaveCuadrilla.class)
	public ResponseEntity<ErrorDetails> geoCercaNotHaveCuadrilla(Exception e) {
		return UtileriaErrorDetails.sendError("GeoCerca not have cuadrilla", e, HttpStatus.BAD_REQUEST,
				"La geocerca no tiene una cuadrilla asignada");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ UsuarioAccesoException.UsuarioExistsException.class,
			UsuarioAccesoException.UsuarioNotExistsException.class,
			UsuarioAccesoException.PasswordNotUpdatedException.class })
	public ResponseEntity<ErrorDetails> usuarioExistsException(Exception e) {
		return UtileriaErrorDetails.sendError("Invalid user", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GeoCercaException.DataNotExits.class)
	public ResponseEntity<ErrorDetails> cuadrillaDataNotExists(Exception e) {
		return UtileriaErrorDetails.sendError("Cuadrilla  or  geocerca not exists", e, HttpStatus.BAD_REQUEST,
				"La cuadrilla o la  geocerca no existen");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(GeoCercaException.CuadrillaNotHaveGeoCerca.class)
	public ResponseEntity<ErrorDetails> cuadrillaNotHaveGeocerca(Exception e) {
		return UtileriaErrorDetails.sendError("Cuadrilla not have geocerca", e, HttpStatus.BAD_REQUEST,
				"La cuadrilla no tiene una geocerca asignada");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(MainException.GeneralException.class)
	public ResponseEntity<ErrorDetails> generalException(Exception e) {
		return UtileriaErrorDetails.sendError("An error has ocurred", e, HttpStatus.BAD_REQUEST,
				"Ocurrio un error al procesar la solicitud");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(OrdenesException.NotLocationFoud.class)
	public ResponseEntity<ErrorDetails> notLocationFound(Exception e) {
		return UtileriaErrorDetails.sendError("Location not found", e, HttpStatus.BAD_REQUEST,
				"La ubicacion de la orden no pudo ser encontrada");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> NoHandlerFoundException(HttpServletRequest httpServletRequest, Exception e) {
		return UtileriaErrorDetails.sendError("The URL was not found", e, HttpStatus.BAD_REQUEST,
				"La URL no fue encontrada");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ TipoOrdenesException.NotTipoOrdenesFoundException.class,
			OrdenesException.InsufficientItemsException.class, OrdenesException.ItemsNotCreatedException.class })
	public ResponseEntity<ErrorDetails> tipoOrdenesException(Exception e) {
		return UtileriaErrorDetails.sendError("Orden not created", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(CuentaClienteException.NotClientesFoundException.class)
	public ResponseEntity<ErrorDetails> cuentaClienteException(Exception e) {
		return UtileriaErrorDetails.sendError("Not clientes found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(OrdenesException.NotOrdenCreatedException.class)
	public ResponseEntity<ErrorDetails> notOrdenCreatedException(Exception e) {
		return UtileriaErrorDetails.sendError("Orden not created", e, HttpStatus.BAD_REQUEST,
				"Ocurrio un error al crear la orden");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ BatchException.NotResultsException.class, BatchException.HeaderException.class,
			BatchException.FileEmptyException.class, BatchException.NotRowsInsertedException.class })
	public ResponseEntity<ErrorDetails> batchException(Exception e) {
		return UtileriaErrorDetails.sendError("Error on proccesing file", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(BatchException.BlankSpaceException.class)
	public ResponseEntity<ErrorDetails> blankSpaceException(Exception e) {
		return UtileriaErrorDetails.sendError("The file contaies blank spaces", e, HttpStatus.BAD_REQUEST,
				"El archivo no puede tener saltos de linea en blaco");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ CuadrillasException.NotCuadrillaRegistrerForOrdenException.class,
			CuadrillasException.NotCuadrillaFoundForZone.class, CuadrillasException.FueraRangoException.class,
			CuadrillasException.NotTipoOrdenesFoundException.class, CuadrillasException.NotUserFoundException.class,
			CuadrillasException.PlacasOrNumeroAlreadyExistsException.class, })
	public ResponseEntity<ErrorDetails> notCuadrillaRegisterForOrden(Exception e) {
		return UtileriaErrorDetails.sendError("Cuadrilla not foud", e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ NumberFormatException.class, NullPointerException.class })
	public void weakExceptionsHandler(Exception e) {
		logClassAndMethodWithException(e);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ RuntimeException.class, ClassNotFoundException.class, SQLException.class,
			IllegalArgumentException.class })
	public ResponseEntity<ErrorDetails> severeExceptionsHandler(Exception e) {
		return UtileriaErrorDetails.sendError("An Error has been ocurred", e, HttpStatus.BAD_REQUEST,
				"Ocurrio un error al procesar la solicitud");
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler
	public ResponseEntity<ErrorDetails> exceptionsHandler(Exception e) {
		return UtileriaErrorDetails.sendError("An Error has been ocurred", e, HttpStatus.BAD_REQUEST,
				"Ocurrio un error desconocido");
	}

	public static void logClassAndMethodWithException(Exception e) {
		Integer indice = 0;
		for (StackTraceElement ste : e.getStackTrace()) {
			String basePackage = ste.getClassName();
			if (basePackage.contains("com.sytecso")) {
				break;
			}
			indice += 1;
		}

		StackTraceElement ste = e.getStackTrace()[indice];
		Class<?> c = null;
		try {
			c = Class.forName(ste.getClassName());
		} catch (Exception e2) {
			SytecsoLogger.error("An exception ocurred in SytecsoException type", e2);
		}

		String mname = ste.getMethodName();

		if ("<init>".equals(mname)) {
			c.getConstructors();
		} else if ("<cinit>".equals(mname)) {
			SytecsoLogger.error("An exception ocurred in a satic block: ", e);
		} else {
			for (Method m : c.getMethods()) {
				if (m.getName().equals(mname)) {
					if (m.getDeclaringClass().getName().contains("Impl")) {
						StringBuilder buffer = new StringBuilder();
						Integer index1 = m.getDeclaringClass().getName().indexOf("imp");
						Integer index2 = m.getDeclaringClass().getName().indexOf("Impl");
						buffer.append(m.getDeclaringClass().getName().substring(0, index1));
						buffer.append(m.getDeclaringClass().getName().substring(index1 + 5, index2));
						SytecsoLogger.error("ERROR: An exception ocurred in method: " + m.getName() + " in class: "
								+ buffer + " exception type: ", e);
						System.err.println(
								"ERROR: An exception ocurred in method: " + m.getName() + " in class " + buffer + e);
					} else {
						SytecsoLogger.error("ERROR: An exception ocurred in method: " + m.getName() + " in class: "
								+ m.getDeclaringClass() + " type exception ", e);
						System.err.println("ERROR: An exception ocurred in method: " + m.getName() + " in class: "
								+ m.getDeclaringClass() + " type exception: " + e);
					}
					break;
				} else {
					SytecsoLogger.error("ERROR: An exception ocurred in method: " + m.getName() + " And sub method: "
							+ mname + " in class: " + m.getDeclaringClass() + " exception type: ", e);
					System.err.println("ERROR: An exception ocurred in method: " + m.getName() + " And sub method: "
							+ mname + " in class: " + m.getDeclaringClass() + " exception type: " + e);
					break;
				}
			}
		}

	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(OrganizacionException.OrganizationEmptyException.class)
	public ResponseEntity<ErrorDetails> OrganizationEmpty(Exception e) {
		return UtileriaErrorDetails.sendError("Not Organizacion found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ProyectoException.ProyectoEmptyException.class)
	public ResponseEntity<ErrorDetails> ProyectoEmpty(Exception e) {
		return UtileriaErrorDetails.sendError("Not Proyecto found", e, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(AlmacenesException.AlmacenEmptyException.class)
	public ResponseEntity<ErrorDetails> AlmacenEmptyException(Exception e) {
		return UtileriaErrorDetails.sendError("Not Almacen found", e, HttpStatus.BAD_REQUEST);
	}

}
