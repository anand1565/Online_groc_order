package com.grocery.controller;
import com.grocery.dao.ProductDao;
import com.grocery.model.Product;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ProductServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductDao productDao = new ProductDao();
        List<Product> productList = productDao.getAllProducts();

        // Set product list as a request attribute and forward to home.jsp
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("home.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productName = request.getParameter("productName");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        Part productImage = request.getPart("productImage");

        // Generate a unique product ID (You can modify this according to your logic)
        String productId = String.valueOf(System.currentTimeMillis());

        // Create a new product object
        Product product = new Product();
        product.setProductId(productId); // Set the generated product ID
        product.setProductName(productName);
        product.setPrice(Double.parseDouble(price));
        product.setDescription(description);

        // Assuming productImage is stored in a location accessible later, save it to a specific location
        String imagePath = saveImage(productImage); // Method to handle image saving
        product.setProductImage(imagePath);

        // Call ProductDao to add the product
        ProductDao productDao = new ProductDao();
        boolean success = productDao.addProduct(product);

        // Redirect based on success
        if (success) {
            response.sendRedirect("adminhome.jsp?success=true");
        } else {
            response.sendRedirect("addproduct.jsp?error=true");
        }
	}
	
	 private String saveImage(Part productImage) {
	        // Implement logic to save the image to a specific path and return the image URL or path
	        String filePath = "path_to_image_storage/" + productImage.getSubmittedFileName(); // Adjust the path as needed
	        try {
	            productImage.write(filePath); // Save the image to the file system
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return filePath; // Return the path of the saved image
	    }
	

}
