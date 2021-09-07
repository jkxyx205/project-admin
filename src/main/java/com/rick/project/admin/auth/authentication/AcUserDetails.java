package com.rick.project.admin.auth.authentication;

import com.rick.project.admin.auth.model.UserDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 5:16 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Getter
public class AcUserDetails extends User {

    private UserDTO user;

    public AcUserDetails(UserDTO user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }
}
