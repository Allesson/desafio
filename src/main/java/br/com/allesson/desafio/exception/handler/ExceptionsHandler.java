package br.com.allesson.desafio.exception.handler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroPadrao> handlerException(PersistenceException e, HttpServletRequest request){
		String error = "Erro interno no servidor";
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErroPadrao err = new ErroPadrao(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Object> handlerNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
		String error = "Não encontrado.";
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErroPadrao err = new ErroPadrao(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(PersistenceException.class)
	public ResponseEntity<ErroPadrao> handlerDataBase(PersistenceException e, HttpServletRequest request){
		String error = "Erro em requisição.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErroPadrao err = new ErroPadrao(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> handlerArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		String error = "Campo inválido.";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errors = new ArrayList<>();
        fieldErrors.forEach(f -> {
            errors.add(f.getField() + " : " + f.getDefaultMessage());
        });
		ErroPadrao err = new ErroPadrao(Instant.now(), status.value(), error, errors.toString(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}

}