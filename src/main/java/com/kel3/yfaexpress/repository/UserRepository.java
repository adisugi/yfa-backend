package com.kel3.yfaexpress.repository;

import com.kel3.yfaexpress.model.entity.Useraa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Useraa, Long>{
	Useraa findByEmail(String email);
}
