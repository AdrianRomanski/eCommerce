package com.example.controllers;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.persistence.Cart;
import com.example.model.persistence.User;
import com.example.model.persistence.repositories.CartRepository;
import com.example.model.persistence.repositories.UserRepository;
import com.example.model.requests.CreateUserRequest;
import org.apache.commons.lang3.RandomStringUtils;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		log.info("INFO: Username set with: createUserRequest.getUsername()");
		user.setSalt(RandomStringUtils.randomAlphanumeric(16));
		log.info("INFO: Salt set with: RandomStringUtils.randomAlphanumeric(16)");
		user.setPassword(createUserRequest.getPassword() + user.getSalt());
		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		log.info("INFO: Cart created and saved to repository");
		if (!createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			log.warn("WARN: CreateUser = Failed | Reason = Not_Equals");
			return ResponseEntity.badRequest().build();
		} else if (createUserRequest.getPassword().length() < 7) {
			log.warn("WARN: CreateUser = Failed | Reason = Too_Short");
			return ResponseEntity.badRequest().build();
		} else if (!createUserRequest.getPassword().matches(("[a-zA-Z0-9]+"))) {
			log.warn("WARN: CreateUser = Failed | Reason = Illegal_Characters");
			return ResponseEntity.badRequest().build();
		}
		String encryptedPassword = bCryptPasswordEncoder.encode(createUserRequest.getPassword());
		log.info("INFO: Password encrypted with: bCryptPasswordEncoder.encode(createUserRequest.getPassword()");
		user.setPassword(encryptedPassword);
		userRepository.save(user);
		log.info("INFO: User " + user.getUsername() + " successfully created and saved to repository");
		return ResponseEntity.ok(user);
		}
	}