package com.cyx.dao;

import com.cyx.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @date 2021/2/22
 */
public interface ProductDao {
    /**
     * 查询所有账户
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

    /**
     * 根据产品编号删除产品
     * @Param [product]
     * @Return int
     */
    int deleteProductByNum(String productNum) throws Exception;

    /**
     * 更新指定编号产品的状态
     * @Param [productNum, status]
     * @Return int
     */
    int updateProductStatus(@Param("productNum") String productNum, @Param("status") int status) throws Exception;
}
