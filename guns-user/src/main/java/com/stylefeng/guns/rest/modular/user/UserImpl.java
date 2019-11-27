package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserImpl  implements UserAPI {
    @Override
    public boolean login(String username, String password) {
        System.out.println("this is user services");
        return true;
    }
}
