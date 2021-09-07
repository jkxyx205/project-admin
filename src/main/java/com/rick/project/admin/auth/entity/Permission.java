package com.rick.project.admin.auth.entity;

import com.rick.project.admin.common.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: 权限表
 * @author: Rick.Xu
 * @date: 12/19/19 1:39 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Getter
@Setter
@Entity
@Table(name = "sys_auth_permission")
public class Permission extends BaseEntity {

    private String name;

    private String permission;

    private Integer pid;

    @Column(name = "sort")
    private Integer sort;
}
