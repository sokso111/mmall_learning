package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetialVo;

/**
 * Created by mayilong on 2017/6/13.
 */
public interface IProductService {
    ServiceResponse<String> saveAndUpdate(Product product);

    ServiceResponse<String> setStatus(Integer productId,Integer status);

    ServiceResponse<ProductDetialVo> manageProductDetail(Integer productId);
}
