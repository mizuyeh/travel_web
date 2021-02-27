package com.cyx.service.impl;

import com.cyx.dao.ProductDao;
import com.cyx.entity.Product;
import com.cyx.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description
 * @date 2021/2/22
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() throws Exception {
        return productDao.findAll();
    }

    @Override
    public int saveProduct(Product product) throws Exception {
        return productDao.saveProduct(product);
    }

    @Override
    public int deleteProductByNum(String productNum) throws Exception {
        return productDao.deleteProductByNum(productNum);
    }

    @Override
    public int updateProductStatus(String productNum, int status) throws Exception {
        return productDao.updateProductStatus(productNum, status);
    }
}
