/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demos.model;

import demos.db.Product;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

public class ProductManager {

    private static final Logger LOG = Logger.getLogger(ProductManager.class.getName());

    // Instancia a la fachada 
    private ProductFacadeRemote productFacade;

    public ProductManager() {
        //EJB
        try {
            // sin .properties
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.enterprise.naming.SerialInitContextFactory");
            //props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
             //glassfish default port value will be 3700,
            //props.setProperty("org.omg.CORBA.ORBInitialPort", "4848");
            Context ctx = new InitialContext(props);
            productFacade = (ProductFacadeRemote) ctx.lookup("java:global/ProductApp/ProductApp-ejb/"
                    + "ProductFacade!demos.model.ProductFacadeRemote");

            //CON .PROPERTIES. 
//            Context ctx = new InitialContext();
//            productFacade = (ProductFacadeRemote) ctx.lookup("java:global/ProductApp/ProductApp-ejb/"
//                    + "ProductFacade!demos.model.ProductFacadeRemote");
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error initialising EJB reference", ex);
        }
    }

    // Practica 4
    public void create(Product product) {
        productFacade.create(product);
    }

    public void update(Product product) {
        productFacade.update(product);
    }

    public void delete(Product product) {
        productFacade.delete(product);
    }

    public Product findProduct(Integer id) {
        return productFacade.findProduct(id);
    }

    public List<Product> findProductByName(String name) {
        return productFacade.findProductByName(name);
    }

}
