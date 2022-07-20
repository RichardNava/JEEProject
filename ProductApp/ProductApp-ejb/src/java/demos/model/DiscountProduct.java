/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/MessageDrivenBean.java to edit this template
 */
package demos.model;

import demos.db.Product;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.OptimisticLockException;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author cristina
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/productQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "messsageSelector", propertyValue = "JMSXDeliveryCount <3")
})
public class DiscountProduct implements MessageListener {

    private static final Logger LOG = Logger.getLogger(DiscountProduct.class.getName());
    @EJB
    private ProductFacade productFacade;

    public DiscountProduct() {
    }

    @Override
    public void onMessage(Message message) {

        try {
            Product product = message.getBody(Product.class);
            BigDecimal discount = product.getPrice().multiply(BigDecimal.valueOf(0.1));
            product.setPrice(product.getPrice().subtract(discount));
            productFacade.validateProduct(product);
            productFacade.update(product);
            
            LOG.log(Level.INFO, "Product " + product+ " discounted by " + discount);

        } catch (JMSException ex) {
            LOG.log(java.util.logging.Level.SEVERE, "Error Adquiring Message", ex);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof ConstraintViolationException) {
                ConstraintViolationException e = (ConstraintViolationException) cause;
                e.getConstraintViolations().stream().forEach(v -> LOG.log(java.util.logging.Level.INFO, e.getMessage()));

            } else if (cause instanceof OptimisticLockException) {
                OptimisticLockException e = (OptimisticLockException) cause;
                LOG.log(java.util.logging.Level.INFO, e.getMessage());
            } else {
                LOG.log(java.util.logging.Level.SEVERE, "Product Manager Error", ex);
            }
        }
    }

}
