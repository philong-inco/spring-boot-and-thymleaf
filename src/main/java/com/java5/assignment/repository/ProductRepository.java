package com.java5.assignment.repository;

import com.java5.assignment.entity.Product;
import com.java5.assignment.repository.projection.ProductForHomeProjecttion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findById(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.code = :code")
    Optional<Product> findByCode(@Param("code") String code);

    @Query("SELECT p FROM Product p")
    Page<Product> getAll(Pageable pageable);

    @Query("SELECT p FROM Product p")
    List<Product> getAll();

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    Page<Product> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT p FROM Product p WHERE p.status = :status")
    List<Product> findByStatus(@Param("status") Integer status);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.id <> :id")
    List<Product> existByNameAndDifferentId(@Param("name") String name, @Param("id") Long id);

    @Query(value = """
           SELECT p.id AS product_id,
                          (SELECT pd.image
                           FROM product_detail pd
                           WHERE pd.product_id = p.id
                           LIMIT 1) AS product_image,
                          CASE
                              WHEN EXISTS (SELECT 1
                                           FROM favorite_list f
                                           WHERE f.product_id = p.id
                                             AND f.customer_id = :idCustomer)
                              THEN true
                              ELSE false
                          END AS favorite,
                          p.name AS product_name,
                          p.description AS product_description
                   FROM product p WHERE LOWER(p.name) LIKE %:key%
            """,
            countQuery = """
                    SELECT COUNT(p.id) FROM product p
                    """
            , nativeQuery = true)
    Page<ProductForHomeProjecttion> getProductForClient(@Param("idCustomer") Long idCustomer,@Param("key") String key, Pageable pageable);


    @Query(value = """
           SELECT p.id AS product_id,
                          (SELECT pd.image
                           FROM product_detail pd
                           WHERE pd.product_id = p.id
                           LIMIT 1) AS product_image,
                          CASE
                              WHEN EXISTS (SELECT 1
                                           FROM favorite_list f
                                           WHERE f.product_id = p.id
                                             AND f.customer_id = :idCustomer)
                              THEN true
                              ELSE false
                          END AS favorite,
                          p.name AS product_name,
                          p.description AS product_description
                   FROM product p JOIN favorite_list fav ON p.id = fav.product_id
                   WHERE fav.customer_id = :idCustomer
            """,
            countQuery = """
                    SELECT COUNT(p.id) FROM product p
                    """
            , nativeQuery = true)
    Page<ProductForHomeProjecttion> getProductFavorite(@Param("idCustomer") Long idCustomer, Pageable pageable);

    @Query(value = """
              SELECT p.id AS product_id,
                           (SELECT pd.image
                            FROM product_detail pd
                            WHERE pd.product_id = p.id
                            LIMIT 1) AS product_image,
                           CASE
                               WHEN EXISTS (SELECT 1
                                            FROM favorite_list f
                                            WHERE f.product_id = p.id
                                              AND f.customer_id = :idCustomer)
                               THEN true
                               ELSE false
                           END AS favorite,
                           p.name AS product_name,
                           p.description AS product_description
                    FROM product p
                    WHERE p.id = :idProduct
            """, nativeQuery = true)
    ProductForHomeProjecttion getProductSingleForClient(@Param("idCustomer") Long idCustomer, @Param("idProduct") Long idProduct);

}
