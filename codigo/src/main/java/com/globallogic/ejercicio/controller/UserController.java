package com.globallogic.ejercicio.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.ejercicio.DTO.ErrorMessage;
import com.globallogic.ejercicio.entity.User;
import com.globallogic.ejercicio.service.IUserService;
import com.globallogic.ejercicio.util.AuthUtils;
import com.globallogic.ejercicio.util.Token;
import com.nimbusds.jose.JOSEException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/sign-up", method = { RequestMethod.POST })
	public ResponseEntity<?> save(@Valid @RequestBody User request, BindingResult result,
			HttpServletRequest requestServelt) throws JOSEException {

		Map<String, Object> response = new HashMap<>();
		User newUser = new User();
		try {
			// creacion de token
			request.setToken(Token.createToken(requestServelt,request));

			// validacion campos obligatorios vacios
			if (request.getPassword() == null) {
				response.put("errors : ", new ErrorMessage(new Date(), 400, "El campo password es obigatorio"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			if (request.getEmail() == null) {
				response.put("errors : ", new ErrorMessage(new Date(), 400, "El campo email es obigatorio"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			// manejo de errores de campos
			if (result.hasErrors()) {
				List<Object> errors = result.getFieldErrors().stream()
						.map(err -> new ErrorMessage(new Date(), 400,
								"En el campo: '" + err.getField() + "' " + err.getDefaultMessage()))
						.collect(Collectors.toList());
				response.put("errors", errors);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			// preguntamos si el correo ya existe
			User user = userService.findByEmail(request.getEmail());
			if (user == null) {
				request.setCreated(new Date());
				request.setIsactive(true);

				// grabamos en BD
				newUser = userService.save(request);

				// creamos la respuesta
				response.put("id", newUser.getIdUser());
				response.put("created", newUser.getCreated());
				response.put("lastLogin", (newUser.getLastLogin() != null) ? newUser.getLastLogin() : "null");
				response.put("token", (newUser.getToken() != null) ? newUser.getToken() : "null");
				response.put("isActive", newUser.getIsactive());
				
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			} else {
				response.put("errors", new ErrorMessage(new Date(), 200, "El correo ya existe"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		} catch (DataAccessException e) {
			response.put("errors",
					new ErrorMessage(new Date(), 500, "Error al realizar el insert en la base de datos"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public ResponseEntity<?> getUsers(HttpServletRequest request) throws Exception {
		Map<String, Object> response = new HashMap<>();
		try {
			User user = userService.findByEmail(AuthUtils.getUser(request).getEmail());
			// volvemos a crear un token			
			user.setToken(Token.createToken(request,user));
			user.setLastLogin(new Date());
			user = userService.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			response.put("errors", new ErrorMessage(new Date(), 500, "Error al buscar el usuario"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
