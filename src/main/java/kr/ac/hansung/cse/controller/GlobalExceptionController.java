package kr.ac.hansung.cse.controller;

import javax.servlet.http.HttpServletRequest;

import kr.ac.hansung.cse.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.ac.hansung.cse.exception.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionController {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest req, NotFoundException ex) {
		String requestURL = req.getRequestURL().toString();
		String errorCode = "id.notfound.exception";
		String errorMsg = "id " + ex.getId() + " not found";

		ErrorResponse errorResponse = new ErrorResponse(requestURL, errorCode, errorMsg);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
}
