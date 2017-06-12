package com.mmall.service;

import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * Created by mayilong on 2017/6/12.
 */
public interface ICategoryService {
    ServiceResponse<String> addCategory(String categoryName, Integer parentId);

    ServiceResponse<String> updateCategory(String categoryName,Integer id);

    ServiceResponse<List<Category>> getChildParalleCategory(Integer categoryId);

    ServiceResponse getChildDeepCategory(Integer categoryId);
}
