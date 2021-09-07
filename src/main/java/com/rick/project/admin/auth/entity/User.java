package com.rick.project.admin.auth.entity;

import com.rick.project.admin.common.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/19/19 1:38 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {

    private String username;

    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @ManyToMany
    @JoinTable(name = "sys_auth_user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private List<Role> roleList;

    public boolean getLocked() {
        return status !=  UserStatusEnum.NORMAL;
    }
}
