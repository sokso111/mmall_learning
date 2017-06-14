package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetialVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mayilong on 2017/6/13.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    public ServiceResponse<String> saveAndUpdate(Product product){
        if(product != null){
            if(StringUtils.isNotBlank(product.getSubImages())){
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length>0){
                    product.setMainImage(subImageArray[0]);
                }
            }
            if(product.getId() != null){
                int rowCount = productMapper.updateByPrimaryKeySelective(product);
                if(rowCount > 0 ){
                    return ServiceResponse.createBySuccessMessage("更新产品成功");
                }else{
                    return ServiceResponse.createByErrorMessage("更新产品失败");
                }
            }else{
                int rowCount = productMapper.insert(product);
                if(rowCount > 0){
                    return ServiceResponse.createBySuccessMessage("添加产品成功");
                }else {
                    return ServiceResponse.createByErrorMessage("添加产品失败");
                }
            }
        }
        return ServiceResponse.createByErrorMessage("产品错误");
    }

    public ServiceResponse<String> setStatus(Integer productId,Integer status){
        if(productId == null || status == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServiceResponse.createBySuccessMessage("修改状态成功");
        }
        return ServiceResponse.createByErrorMessage("修改状态失败");
    }

    public ServiceResponse<ProductDetialVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServiceResponse.createByErrorMessage("产品不存在");
        }
        ProductDetialVo productDetialVo = assembleProductDetailVo(product);
        return ServiceResponse.createBySuccess("查询成功",productDetialVo);
    }

    public ProductDetialVo assembleProductDetailVo(Product product){
        ProductDetialVo productDetialVo = new ProductDetialVo();
        productDetialVo.setId(product.getId());
        productDetialVo.setCategoryId(product.getCategoryId());
        productDetialVo.setPrice(product.getPrice());
        productDetialVo.setMainImage(product.getMainImage());
        productDetialVo.setSubtitle(product.getSubtitle());
        productDetialVo.setSubImages(product.getSubImages());
        productDetialVo.setDetail(product.getDetail());
        productDetialVo.setStatus(product.getStatus());
        productDetialVo.setStock(product.getStock());
        productDetialVo.setName(product.getName());

        productDetialVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix",""));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetialVo.setParentCategoryId(0);
        }else{
            productDetialVo.setParentCategoryId(category.getParentId());
        }
        productDetialVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetialVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetialVo;
    }
}
