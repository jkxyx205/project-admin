package com.rick.project.admin.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @Description: 角色表
 * @author: Rick.Xu
 * @date: 12/19/19 1:37 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Getter
@Setter
@Entity
@Table(name = "sys_auth_role")
public class Role extends BaseEntity {

    private String name;

    private String value;

    @Column(name = "sort")
    private Integer sort;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sys_auth_role_permission", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id", referencedColumnName = "id") })
    private List<Permission> permissionList;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.REMOVE, mappedBy="roleList")
    private List<User> userList;

}
