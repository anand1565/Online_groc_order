package com.grocery.dao;

import com.grocery.model.Product;
import com.grocery.dao.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
	public boolean addProduct(Product product) {
        String insertSQL = "INSERT INTO products (productName, price, description, productImage) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
             
            pstmt.setString(1, product.getProductName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.setString(3, product.getDescription());
            pstmt.setString(4, product.getProductImage());
            pstmt.executeUpdate();
            return true; // Product added successfully
        } catch (SQLException e) {
            System.out.println("Error adding product: " + e.getMessage());
            return false; // Error adding product
        }
    }

    // Method to retrieve all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectSQL = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setProductImage(rs.getString("productImage"));
                products.add(product); // Add the product to the list
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving products: " + e.getMessage());
        }
        return products; // Return the list of products
    }

    // Method to retrieve a specific product by its ID
    public Product getProductById(int productId) {
        String selectSQL = "SELECT * FROM products WHERE productId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
             
            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productId"));
                product.setProductName(rs.getString("productName"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setProductImage(rs.getString("productImage"));
                return product; // Return the found product
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving product by ID: " + e.getMessage());
        }
        return null; // Return null if product not found
    }
}
