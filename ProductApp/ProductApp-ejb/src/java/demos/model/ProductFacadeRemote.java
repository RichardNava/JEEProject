/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package demos.model;

import demos.db.Product;
import javax.ejb.Remote;
import javax.validation.Valid;
import java.util.List;

/**
 *
 * @author cristina
 */
@Remote
public interface ProductFacadeRemote{

    void create(@Valid Product product);

    void update(@Valid Product product);

    void delete(Product product);

    Product findProduct(Integer id);

    List<Product> findProductByName(String name);

}
