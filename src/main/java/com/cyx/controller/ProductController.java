package com.cyx.controller;

import com.cyx.entity.Product;
import com.cyx.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @date 2021/2/22
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequiresPermissions("product:view")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("productList", products);
        modelAndView.setViewName("product-list");
        return modelAndView;
    }

    @RequiresPermissions("product:create")
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        int result = productService.saveProduct(product);
        return "redirect:findAll.do";
    }

    @RequiresPermissions("product:delete")
    @RequestMapping("/delete.do")
    public String delete(String check_val) throws Exception {
        List<String> productNums = Arrays.asList(check_val.split(","));
        for (String productNum : productNums) {
            productService.deleteProductByNum(productNum);
        }
        System.out.println("-------------------" + productNums);
        return "redirect:findAll.do";
    }

    @RequiresPermissions("product:update")
    @RequestMapping("/updateStatus.do")
    public String updateStatus(String check_val, String status) throws Exception {
        List<String> productNums = Arrays.asList(check_val.split(","));
        for (String productNum : productNums) {
            productService.updateProductStatus(productNum, new Integer(status));
        }
        return "redirect:findAll.do";
    }
}
