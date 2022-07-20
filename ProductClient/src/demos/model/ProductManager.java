/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demos.model;

import demos.db.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.Valid;


public class ProductManager {
    private EntityManager em;
    private Query productNameQuery;
    
    public ProductManager(String persistenceUnit){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnit);
        em = emf.createEntityManager();
        
        productNameQuery = em.createNamedQuery("Product.findByName");
    }
    
    public void closeEntityManager(){
        em.close();
    }
    
    public void create(@Valid Product product){
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
    }
    
    public void update(@Valid Product product){
        em.getTransaction().begin();
        product = em.merge(product);
        em.getTransaction().commit();
    }
    
    public void delete(Product product){
        em.getTransaction().begin();
        em.remove(product);
        em.getTransaction().commit();
    }
    
    public Product findProduct(Integer id){
        return em.find(Product.class, id);
    }
    
    public List<Product> findProductByName(String name){
        productNameQuery.setParameter("name", name);
        return productNameQuery.getResultList();
    }
    
}
