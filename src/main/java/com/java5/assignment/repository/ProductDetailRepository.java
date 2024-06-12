package com.java5.assignment.repository;

import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.repository.projection.ProductDetailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    @Query("SELECT p FROM ProductDetail p WHERE p.id = :id")
    Optional<ProductDetail> findById(Long id);

    @Query("SELECT p FROM ProductDetail p WHERE p.product.id = :id AND p.color.id = :idColor AND p.size.id = :idSize")
    List<ProductDetail> findByColorAndSizeByIdSP(@Param("id") Long id, @Param("idColor") Long idColor, @Param("idSize") Long idSize);

    @Query(value = """
            SELECT p.name as product_name, 
            c.name as color_name, 
            s.name as size_name, 
            pd.price as price, 
            pd.amount as amount, 
            pd.status as status, 
            pd.id as id
            FROM product p
            LEFT JOIN product_detail pd ON p.id = pd.product_id
            LEFT JOIN size s ON s.id = pd.size_id
            LEFT JOIN color c ON c.id = pd.color_id
            WHERE pd.product_id = :idSP
            """, nativeQuery = true)
    Page<ProductDetailProjection> getAll(Pageable pageable, @Param("idSP") Long idSP);

    @Query(value = """
            SELECT p.name as product_name, 
            c.name as color_name, 
            s.name as size_name, 
            pd.price as price, 
            pd.amount as amount, 
            pd.status as status, 
            pd.id as id
            FROM product p
            LEFT JOIN product_detail pd ON p.id = pd.product_id
            LEFT JOIN size s ON s.id = pd.size_id
            LEFT JOIN color c ON c.id = pd.color_id
            WHERE pd.status=1 AND (LOWER(p.name) LIKE %:key% OR LOWER(p.code) LIKE %:key% OR LOWER(s.name) LIKE %:key% OR LOWER(c.name) LIKE %:key%)
            """, nativeQuery = true)
    Page<ProductDetailProjection> findByNameOrCodeOrColorOrSize(@Param("key") String key, Pageable pageable);

    @Query(value = """
            SELECT p.name as product_name, 
            c.name as color_name, 
            s.name as size_name, 
            pd.price as price, 
            pd.amount as amount, 
            pd.status as status, 
            pd.id as id
            FROM product p
            LEFT JOIN product_detail pd ON p.id = pd.product_id
            LEFT JOIN size s ON s.id = pd.size_id
            LEFT JOIN color c ON c.id = pd.color_id
            WHERE pd.id = :id
            """, nativeQuery = true)
    ProductDetailProjection findProjectionById(@Param("id") Long idProductDetail);
    @Query(value = """
            SELECT p.name as product_name, 
            c.name as color_name, 
            s.name as size_name, 
            pd.price as price, 
            pd.amount as amount, 
            pd.status as status, 
            pd.id as id
            FROM product p
            LEFT JOIN product_detail pd ON p.id = pd.product_id
            LEFT JOIN size s ON s.id = pd.size_id
            LEFT JOIN color c ON c.id = pd.color_id
            WHERE pd.product_id = :idSP
            AND pd.status = 1
            """, nativeQuery = true)
    List<ProductDetailProjection> findByIdProductForClient(@Param("idSP") Long idSP);

    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.id =:idSP AND pd.color.id = :idColor AND pd.size.id = :idSize")
    List<ProductDetail> existByColorIdAndSizeIdAndIdSP(@Param("idColor") Long idColor,
                                                       @Param("idSize") Long idSize,
                                                       @Param("idSP") Long idSP);

    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.id =:idSP AND pd.color.id = :idColor AND pd.size.id = :idSize AND pd.id <> :id")
    List<ProductDetail> existByColorIdAndSizeIdAndIdSPDifferentId(@Param("idColor") Long idColor,
                                                                  @Param("idSize") Long idSize,
                                                                  @Param("idSP") Long idSP,
                                                                  @Param("id") Long id);
}
