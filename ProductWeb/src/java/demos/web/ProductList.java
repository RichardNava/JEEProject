/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package demos.web;

import demos.db.Product;
import demos.model.ProductManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ProductList", urlPatterns = {"/list"})
public class ProductList extends HttpServlet {

    //CDI Beans: inyección de dependencias
    @Inject
    ProductManager pm;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            StringBuilder content = new StringBuilder();
            String name = request.getParameter("p_name");

            List<Product> products = pm.findProductByName(name);
            if (!products.isEmpty()) {
                products.stream().forEach(p -> content.append("<div class= 'data'>" + p + "</div>"));
            } else {
                content.append("<div class='error'>");
                content.append("Unable to find any products matching name '" + name + "'</div>");
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' " + "content='width=device-width, initial-scale=1.0'>");
            out.println("<link rel='stylesheet'" + "type='text/css' href='css/pm.css'>");
            out.println("<title>Product List</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<header class='header'>Products</header>");
            out.println("<nav class='nav'><a href= '/pm/ProductSearch.html'>Back to product search</a></nav>");
            out.println("<section class='content'");
            out.println(content);
            out.println("</section>");
            out.println("<footer class='footer'>Invoker used method " + request.getMethod() + "</footer>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String error = request.getParameter("error");
        if (error != null) {
            throw new ServletException("Test Servlet Error");
        }
        RequestDispatcher rd = request.getRequestDispatcher("ProductSearch.html");
        rd.forward(request, response);
        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
