package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by mayilong on 2017/6/12.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加种类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> addCategory(HttpSession session,String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return ServiceResponse.createByErrorMessage("无权限用户");
        }
    }

    /**
     * 修改种类
     * @param session
     * @param categoryName
     * @param id
     * @return
     */
    @RequestMapping(value = "update_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse<String> updateCategory(HttpSession session,String categoryName, int id){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iCategoryService.updateCategory(categoryName,id);
        }else{
            return ServiceResponse.createByErrorMessage("无权限用户");
        }
    }

    /**
     * 获得种类
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do",method = RequestMethod.GET)
    @ResponseBody
    public ServiceResponse<List<Category>> getChildParalleCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") int categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iCategoryService.getChildParalleCategory(categoryId);
        }else{
            return ServiceResponse.createByErrorMessage("无权限用户");
        }
    }

    /**
     * 子节点及其子节点id
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_deep_category.do",method = RequestMethod.POST)
    @ResponseBody
    public ServiceResponse getChildDeepCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") int categoryId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iCategoryService.getChildDeepCategory(categoryId);
        }else{
            return ServiceResponse.createByErrorMessage("无权限用户");
        }
    }
}
