package kr.pe.fourj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.pe.fourj.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
