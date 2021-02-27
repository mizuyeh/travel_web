package com.cyx.service;

import com.cyx.entity.Product;

import java.util.List;

/**
 * @Description
 * @date 2021/2/22
 */
public interface ProductService {
    /**
     * 查询所有产品
     * @Param []
     * @Return java.util.List<com.cyx.entity.Product>
     */
    List<Product> findAll() throws Exception;

    /**
     * 保存产品
     * @Param [product]
     * @Return int
     */
    int saveProduct(Product product) throws Exception;

    int deleteProductByNum(String productNum) throws Exception;

    int updateProductStatus(String productNum, int status) throws Exception;

}
