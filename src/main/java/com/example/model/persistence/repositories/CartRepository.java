package com.example.model.persistence.repositories;

import com.example.model.persistence.Cart;
import com.example.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
