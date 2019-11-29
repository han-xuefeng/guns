package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {

    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String username, String password) {
        //根据登陆账号获取数据库信息
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(username);
        MoocUserT result = moocUserTMapper.selectOne(moocUserT);

        //验证
        if (result != null && result.getUuid() > 0){
            String md5Password = MD5Util.encrypt(password);
            if(result.getUserPwd().equals(md5Password)){
                return result.getUuid();
            }
        }
        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {

        //获取注册信息

        //将注册信息实体转化为数据实体
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setEmail(userModel.getEmail());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setUserPhone(userModel.getPhone());

        //密码  数据加密
        String md5Password = MD5Util.encrypt(userModel.getPassword());
        moocUserT.setUserPwd(md5Password);
        //将数据实体存入数据库
        Integer insert = moocUserTMapper.insert(moocUserT);
        if(insert > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkUsername(String username) {
        System.out.println(username+"###");
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",username);
        Integer result = moocUserTMapper.selectCount(entityWrapper);
        if(result != null && result >0){
            return false;
        }else {
            return true;
        }
    }

    private UserInfoModel do2UserInfo(MoocUserT moocUserT){
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setUpdateTime(moocUserT.getUpdateTime().getTime());
        userInfoModel.setSex(moocUserT.getUserSex());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setLifeState(""+moocUserT.getLifeState());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBeginTime(moocUserT.getBeginTime().getTime());
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setUsername(moocUserT.getUserName());

        return userInfoModel;
    }

    private Date time2Date(Long time){
        Date date = new Date(time);
        return date;
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        //根据主键查询用户信息
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);

        //将MoocUserT转为UserInfoModel
        UserInfoModel userInfoModel = do2UserInfo(moocUserT);
        //返回数据
        return userInfoModel;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {
        //将传入的数据转换为MoocUserT
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUuid(userInfoModel.getUuid());
        moocUserT.setUserName(userInfoModel.getNickname());
        moocUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBeginTime(time2Date(userInfoModel.getBeginTime()));
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setUpdateTime(time2Date(System.currentTimeMillis()));

        //将数据存入到数据库
        Integer isSuccess = moocUserTMapper.updateById(moocUserT);
        if(isSuccess >0 ){
            //按照id把用户查处来
            UserInfoModel userInfo = getUserInfo(moocUserT.getUuid());
            return userInfo;
        }
        return null;
    }
}
