/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package demos.model;

import demos.db.Product;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;


@Stateless
@LocalBean
public class ProductFacade {

    @PersistenceContext(unitName = "ProductWebPU")
    private EntityManager em;

    public void create(@Valid Product product) {
        em.persist(product);
    }

    public void update(@Valid Product product) {
        em.merge(product);
    }

    public void delete(Product product) {
        em.remove(product);
    }

    public Product findProduct(Integer id) {
        return em.find(Product.class, id);
    }

    public List<Product> findProductByName(String name) {
        Query productNameQuery = em.createNamedQuery("Product.findByName");
        productNameQuery.setParameter("name", name);
        return productNameQuery.getResultList();
    }

    public List<Product> findProductByDate(LocalDate date) {
        Query productDateQuery = em.createNamedQuery("Product.findByBestBefore");
        productDateQuery.setParameter("bestBefore", date);
        return productDateQuery.getResultList();
    }

    public Object[] findTotal(List<Integer> ids) {
        Query productTotalQuery = em.createNamedQuery("Product.findTotal");
        productTotalQuery.setParameter("ids", ids);
        return (Object[]) productTotalQuery.getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void validateProduct(@Valid Product product) {
    }

}
