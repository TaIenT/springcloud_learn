package com.learn.client.controller;



import com.learn.client.DTO.CartDTO;
import com.learn.client.VO.ProductInfoVO;
import com.learn.client.VO.ProductVO;
import com.learn.client.VO.ResultVO;
import com.learn.client.dataobject.ProductCategory;
import com.learn.client.dataobject.ProductInfo;
import com.learn.client.service.CategoryService;
import com.learn.client.service.ProductService;
import com.learn.client.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 商品
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    /**
     * 查询所有在架商品
     * 获取类目type列表
     * 查询类目
     * 构造数据
     */

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ResultVO list(){
        //查询所有在架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        //获取类目type列表
        List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        //从数据库查询类目
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //构造数据
        List<ProductVO> productVOList=new ArrayList<ProductVO>();
        for (ProductCategory productCategory:categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCatgoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<ProductInfoVO>();
            for (ProductInfo productInfo:productInfoList)
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);

                    productInfoVOList.add(productInfoVO);
                }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表(给商品服务使用)
     * @param productidList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfo> listForObject(@RequestBody List<String> productIdList){
        return productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }

}
