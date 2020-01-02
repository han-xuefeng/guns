package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Reference(interfaceClass = UserAPI.class,check = false)
    private UserAPI userAPI;

    @RequestMapping("register")
    public ResponseVO register(UserModel userModel){
        if(userModel.getUsername() == null || userModel.getUsername().trim().length() ==0){
            return ResponseVO.serviceFail("用户名不能为空");
        }
        if(userModel.getPassword() == null || userModel.getPassword().trim().length() == 0){
            return ResponseVO.serviceFail("密码不能为空");
        }
        boolean isSuccess = userAPI.register(userModel);
        if(isSuccess){
            return ResponseVO.success("注册成功");
        }else{
            return ResponseVO.serviceFail("注册失败");
        }
    }

    @RequestMapping(value = "check",method = RequestMethod.POST)
    public ResponseVO check(String username){
        if(username != null && username.trim().length()>0){
            boolean notExist = userAPI.checkUsername(username);
            if(notExist){
                return ResponseVO.success("用户用不存在");
            }else{
                return ResponseVO.serviceFail("用户名已存在");
            }
        }else{
            return ResponseVO.serviceFail("用户名不能为空");
        }
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public ResponseVO logout(){
        /**
         * 应用：
         *  1.前端存储jwt[七天] ： jwt刷新问题
         *  2.服务器会存储活动信息[30分钟]
         *  3.jwt里的userId为key 查找用户
         * 退出
         *  1.前端清楚jwt
         *  2.后端服务器删除用户缓存
         * 现状
         *  1.前端删除掉jwt
         */
        return ResponseVO.success("用户退出成功");
    }

    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
    public ResponseVO getUserInfo(){
        //获取当前用户信息
        String userId = CurrentUser.getCurrentUser();
        if(userId != null && userId.trim().length() >0){
            //将用户id传入后端
            int uuid = Integer.parseInt(userId);
            UserInfoModel userInfo = userAPI.getUserInfo(uuid);
            if(userInfo != null) {
                return ResponseVO.success(userInfo);
            }else{
                return ResponseVO.serviceFail("用户信息查询失败");
            }
        }
        return ResponseVO.serviceFail("当前用户没有登录");
    }

    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel){
        //获取当前用户信息
        String userId = CurrentUser.getCurrentUser();
        if(userId != null && userId.trim().length() >0){
            int uuid = Integer.parseInt(userId);
            //判断当前登陆人的id和修改人的id是否相等
            if(uuid != userInfoModel.getUuid()){
                return ResponseVO.serviceFail("非法操作");
            }
            System.out.println(uuid);
            UserInfoModel userInfo = userAPI.updateUserInfo(userInfoModel);
            if(userInfo != null){
                return ResponseVO.success(userInfo);
            }else{
                return ResponseVO.serviceFail("用户信息修改失败");
            }
        }
        return ResponseVO.serviceFail("当前用户没有登录");
    }
}
