package com.numpyninja.lms.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(IdNotFoundException.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public String printIdNotFoundExceptionHandler(Exception ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(DataNotAvailableException.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public String printDataNotAvailableExceptionHandler(Exception ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public String printBadRequestExceptionHandler(Exception ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(DatabaseOperationFailedException.class)
	@ResponseBody
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String batchBadRequestException(DatabaseOperationFailedException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String invalidRequestHandler(MissingServletRequestParameterException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ResponseBody
	public String processValidationError(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = processFieldErrors(ex.getBindingResult().getFieldErrors());
		if(fieldErrors.size()>1) {
			//combine all error messages and display
			String allErrors="";
			for (FieldError fieldError : fieldErrors)
				allErrors = allErrors + fieldError.getDefaultMessage() + "\n";
			return allErrors;
		}
		return fieldErrors.get(0).getDefaultMessage();
	}

	private List<FieldError> processFieldErrors(List<FieldError> fieldErrors) {
		List<FieldError> errors = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) 
			errors.add(new FieldError(fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()));
		return errors;
	}

}
