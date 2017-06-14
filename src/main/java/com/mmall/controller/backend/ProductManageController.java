package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServiceResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by mayilong on 2017/6/13.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;

    /**
     * 添加或者更新产品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping(value = "save.do")
    @ResponseBody
    public ServiceResponse<String> productSave(HttpSession session, Product product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iProductService.saveAndUpdate(product);
        }else{
            return ServiceResponse.createByErrorMessage("用户没有权限");
        }
    }

    /**
     * 修改产品状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping(value = "set_status.do")
    @ResponseBody
    public ServiceResponse<String> setStatus(HttpSession session,Integer productId,Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iProductService.setStatus(productId,status);
        }else{
            return ServiceResponse.createByErrorMessage("用户没有权限");
        }
    }

    /**
     * 查询产品信息
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping(value = "get_detail.do")
    @ResponseBody
    public ServiceResponse getDetail(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServiceResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"请登录");
        }
        if(iUserService.isAdmin(user).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }else{
            return ServiceResponse.createByErrorMessage("用户没有权限");
        }
    }
}
