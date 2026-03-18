package com.example.demo.repository;

import com.example.demo.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByProductId(Long productId);

    @Query("SELECT a.imageUrl FROM Asset a WHERE a.productId = :productId")
    List<String> findImageUrlProduct(@Param("productId") Long productId);
}