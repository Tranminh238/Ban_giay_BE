package com.example.demo.repository;

import com.example.demo.entity.Oder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Oder, Long> {

}
