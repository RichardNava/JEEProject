/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demos.web;

import demos.db.Product;
import demos.model.ProductManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;


@WebFilter(filterName = "EditProductFilter", urlPatterns = {"/ProductEdit.jsp"})
public class EditProductFilter implements Filter {
    
    @Inject
    private ProductManager pm;
    
    private FilterConfig config;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String pid = req.getParameter("p_id");
        Integer id = Integer.parseInt(pid);
        
        Product product = pm.findProduct(id);
        if (product == null) {
            pm.setErrors(true);
            pm.setStatus("Product with id '" + pid + "' not found");
        } else {
            pm.setProduct(product);
            if (req.getMethod().equalsIgnoreCase("POST")) {
                String pname = req.getParameter("p_name");
                String pprice = req.getParameter("p_price");
                String pdate = req.getParameter("p_date");
                try {
                    product.setName(pname);
                    product.setPrice(new BigDecimal(pprice));
                    if (pdate.length() != 0) {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        product.setBestBefore(LocalDate.parse(pdate, fmt));
                        pm.update(product);
                        pm.setStatus("Product update successfully");
                    } else {
                        product.setBestBefore(null);
                    }
                    
                } catch (NumberFormatException ex) {
                    pm.setErrors(true);
                    pm.setStatus("Price '" + pprice + "' is not a valid number");
                } catch (DateTimeParseException ex) {
                    pm.setErrors(true);
                    pm.setStatus("Best before date '" + pdate + "' format should be: yyy-MM-dd");
                } catch (Exception ex) {
                    Throwable cause = ex.getCause();
                    pm.setErrors(true);
                    if (cause instanceof ConstraintViolationException) {
                        StringBuilder status = new StringBuilder("Error updating product: ");
                        ConstraintViolationException e = (ConstraintViolationException) cause;
                        e.getConstraintViolations().stream().forEach(v -> status.append(v.getMessage() + " "));
                        pm.setStatus(status.toString());
                    } else if (cause instanceof OptimisticLockException) {
                        pm.setStatus("Product has been changed by another user.");
                    } else {
                        pm.setStatus("Excepcion general (Rollback)");
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
    }
    
}
