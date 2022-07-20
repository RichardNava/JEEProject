/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB31/SingletonEjbClass.java to edit this template
 */
package demos.model;

import demos.db.Product;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;

/**
 *
 * @author cristina
 */
@Singleton
@LocalBean
public class ExpiringProduct {

    private static final Logger LOG = Logger.getLogger(ExpiringProduct.class.getName());
    
    @EJB
    private ProductFacade productFacade;
    @Inject
    private JMSContext context;
    @Resource (lookup="jms/productQueue")
    private Queue productQueue;

    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "10", dayOfMonth = "*", year = "*", minute = "57", second = "0")
    
    public void handleExpiringProducts() {
        
        JMSProducer producer = context.createProducer();
        List<Product> products = productFacade.findProductByDate(LocalDate.now().plusDays(1));
        products.stream().forEach(p -> producer.send(productQueue, context.createObjectMessage(p)));
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
