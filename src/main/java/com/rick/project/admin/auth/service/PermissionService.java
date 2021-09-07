package com.rick.project.admin.auth.service;

import com.rick.project.admin.auth.entity.Permission;
import com.rick.project.admin.auth.repository.PermissionRepository;
import com.rick.project.admin.common.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/19/19 2:24 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Service
public class PermissionService {
    @Autowired
    private DbService dbService;

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public void addRoles(long permissionId, Set<Long> roleIds) {
        dbService.updateRelationShip("sys_auth_role_permission", "permission_id", "role_id", permissionId, roleIds);
    }

    public List<Permission> findAll() {
        return  permissionRepository.findAll();
    }

    public Permission findById(long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public List<Permission> findByIds(Collection<Long> ids) {
        return permissionRepository.findAllById(ids);
    }
}
