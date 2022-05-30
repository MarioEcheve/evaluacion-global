package com.globallogic.ejercicio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.globallogic.ejercicio.controller.UserController;
import com.globallogic.ejercicio.entity.Phone;
import com.globallogic.ejercicio.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EjercicioApplicationTests {

	@Autowired
	private UserController userController;

	@LocalServerPort
	int randomServerPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() throws Exception {
		assertThat(userController).isNotNull();
	}

	@Test
	public void signUpUserShouldReturnStatus200() throws Exception {

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/user/sign-up";
		URI uri = new URI(baseUrl);

		// phones
		Set<Phone> list = new HashSet<Phone>();
		Phone phone1 = new Phone("123456", "1", "57");
		list.add(phone1);
		Phone phone2 = new Phone("987654", "1", "57");
		list.add(phone2);

		// user
		User user = new User("mario", "Pruebabab12", "", "marioandreseche@gmail.com", new Date(), null, null, true,
				list);

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<User> request = new HttpEntity<>(user, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		assertEquals(200, result.getStatusCodeValue());
	}
}
