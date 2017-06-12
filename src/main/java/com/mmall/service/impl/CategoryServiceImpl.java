package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServiceResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by mayilong on 2017/6/12.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public ServiceResponse<String> addCategory(String categoryName, Integer parentId){
        if(categoryName == null || parentId == null){
            return ServiceResponse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setId(parentId);
        category.setName(categoryName);
        category.setStatus(true);
        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServiceResponse.createBySuccessMessage("添加种类成功");
        }
        return ServiceResponse.createByErrorMessage("添加错误");
    }

    public ServiceResponse<String> updateCategory(String categoryName,Integer id){
        if(categoryName == null || id == null){
            return ServiceResponse.createByErrorMessage("参数错误");
        }
        Category category = new Category();
        category.setId(id);
        category.setName(categoryName);
        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(updateCount > 0){
            return ServiceResponse.createBySuccessMessage("修改种类成功");
        }
        return ServiceResponse.createByErrorMessage("修改种类失败");
    }

    public ServiceResponse<List<Category>> getChildParalleCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectChlidParalleCatoryByParent(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到种类");
        }
        return ServiceResponse.createBySuccess(categoryList);
    }

    /**
     * 查找子节点及其子节点id
     * @param categoryId
     * @return
     */
    public ServiceResponse getChildDeepCategory(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category category : categorySet){
                categoryIdList.add(category.getId());
            }
        }
        return ServiceResponse.createBySuccess(categoryIdList);
    }

    //递归算法，找出子节点
    public Set<Category> findChildCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectChlidParalleCatoryByParent(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }
}
