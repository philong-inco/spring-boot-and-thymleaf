package com.java5.assignment.repository;

import com.java5.assignment.entity.Cart;
import com.java5.assignment.repository.projection.CartProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = """
            SELECT 
            p.name AS product_name,
            s.name AS size_name,
            co.name AS color_name,
            pd.price AS product_detail_price,
            pd.amount AS product_detail_amount,
            c.amount AS cart_amount,
            c.id AS id,
            pd.id AS product_detail_id
            FROM cart c LEFT JOIN product_detail pd ON c.product_detail_id = pd.id
            LEFT JOIN product p ON p.id = pd.product_id
            LEFT JOIN size s ON s.id = pd.size_id
            LEFT JOIN color co ON co.id = pd.color_id
            WHERE c.customer_id = :customerId
            AND p.status = 1 AND pd.status = 1
            """, nativeQuery = true)
    List<CartProjection> getAll(@Param("customerId") Long idCustomer);

    @Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId AND c.productDetail.id = :productDetailId")
    List<Cart> existByProductDetailId(@Param("customerId") Long customerId,@Param("productDetailId") Long productDetailId);

    @Query(value = """
            SELECT 
            p.name AS product_name,
            s.name AS size_name,
            co.name AS color_name,
            pd.price AS product_detail_price,
            pd.amount AS product_detail_amount,
            c.amount AS cart_amount,
            c.id AS id,
            pd.id AS product_detail_id
            FROM cart c LEFT JOIN product_detail pd ON c.product_detail_id = pd.id
            LEFT JOIN product p ON p.id = pd.product_id
            LEFT JOIN size s ON s.id = pd.size_id
            LEFT JOIN color co ON co.id = pd.color_id
            WHERE c.id = :id
            """, nativeQuery = true)
    CartProjection findByIdCart(@Param("id") Long idCart);

    @Query("SELECT c FROM Cart c WHERE c.productDetail.id = :pdId AND c.customer.id = :cId")
    Cart findByCustomerIdAndProductDetailId(@Param("cId") Long customerId,@Param("pdId") Long productDetailId);
}
