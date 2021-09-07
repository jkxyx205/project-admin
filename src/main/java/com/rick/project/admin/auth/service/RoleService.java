package com.rick.project.admin.auth.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rick.db.service.SharpService;
import com.rick.project.admin.auth.entity.Role;
import com.rick.project.admin.auth.model.RoleInfoDTO;
import com.rick.project.admin.auth.model.UserDTO;
import com.rick.project.admin.auth.repository.RoleRepository;
import com.rick.project.admin.common.DbService;
import com.rick.project.admin.plugin.ztree.model.TreeNode;
import com.rick.project.admin.plugin.ztree.service.TreeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DbService dbService;

    @Autowired
    private TreeService treeService;

    @Autowired
    private SharpService sharpService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void save(Role role) {
        roleRepository.save(role);
    }

    public List<Role> saveAll(List<Role> roleList) {
        return roleRepository.saveAll(roleList);
    }

    public List<Role> findAll() {
        // language=SQL
        return sharpService.query("SELECT id, name, value\n" +
                "FROM sys_auth_role\n" +
                "ORDER BY sort ASC", null, Role.class);
    }

    /**
     * 添加权限， 全量
     * @param roleId
     * @param permissionIds
     */
    public void addPermission(long roleId, Set<Long> permissionIds) {
        dbService.updateRelationShip("sys_auth_role_permission", "role_id", "permission_id", roleId, permissionIds);
    }

    /**
     *  获取角色权限
     * @param roleId
     * @return
     */
    public List<TreeNode> getPermissionsNodesByRoleId(Long roleId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("roleId", roleId);
        // language=SQL
        return treeService.getTreeNode("SELECT p.id, p.name, pid as \"pId\", 1 as open\n" +
                "   FROM sys_auth_permission p\n" +
                "  iNNer JOIN sys_auth_role_permission rp\n" +
                "     ON p.id = rp.permission_id\n" +
                "  iNNer JOIN sys_auth_role r\n" +
                "     ON r.id = rp.role_id\n" +
                "  where r.id = :roleId and p.is_deleted = '0' order by p.sort asc", params);
    }

    /**
     *  获取权限集合
     *  剔除为查看品牌的特殊权限
     * @return
     */
    public List<TreeNode> getPermissionsNodes() {
        // language=SQL
        return treeService.getTreeNode("SELECT\n" +
                "\tp.id,\n" +
                "\tp.name,\n" +
                "\tpid AS \"pId\",\n" +
                "\t1 AS OPEN\n" +
                "FROM\n" +
                "\tsys_auth_permission p\n" +
                "WHERE\n" +
                "\tis_deleted = '0'\n" +
                "ORDER BY\n" +
                "\tp.sort ASC", null);
    }

    /**
     *  获取用户权限
     * @param userId
     * @return
     */
    public List<TreeNode> getPermissionsNodesByUserId(String userId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("userId", userId);
        // language=SQL
        List<TreeNode> treeNodeList = treeService.getTreeNode("SELECT\n" +
                "\tap.id,\n" +
                "\tap.name,\n" +
                "\tap.pid AS \"pId\",\n" +
                "\t1 AS OPEN\n" +
                "FROM\n" +
                "\tsys_auth_permission ap\n" +
                "INNER JOIN sys_auth_role_permission rp ON ap.ID = rp.PERMISSION_ID\n" +
                "INNER JOIN sys_auth_user_role ur ON ur.ROLE_ID = rp.ROLE_ID\n" +
                "WHERE\n" +
                "\tur.user_id = :userId\n" +
                "AND ap.is_deleted = '0'\n" +
                "ORDER BY\n" +
                "\tsort", params);

        // 去重
        return treeNodeList.stream().distinct().collect(Collectors.toList());
    }

    /**
     *  获取用户角色
     * @param userId
     * @return
     */
    public List<Role> getRolesByUserId(String userId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("userId", userId);
        // language=SQL
        return sharpService.query("SELECT\n" +
                "\tid, name, value\n" +
                "FROM\n" +
                "\tsys_auth_role ar\n" +
                "WHERE\n" +
                "\tEXISTS (\n" +
                "\t\tSELECT\n" +
                "\t\t\t1\n" +
                "\t\tFROM\n" +
                "\t\t\tsys_auth_user_role ur\n" +
                "\t\tWHERE\n" +
                "\t\t\tur.user_id = :userId\n" +
                "\t\tAND ar.id = ur.role_id\n" +
                "\t)\n" +
                "ORDER BY\n" +
                "\tsort", params, Role.class);
    }



    public void addUser(long roleId, Set<String> userIds) {
        dbService.updateRelationShip("sys_auth_user_role", "role_id", "user_id", roleId, userIds);
    }

    /**
     *  获取角色用户
     * @param roleId
     * @return
     */
    public List<UserDTO> getUsersByRoleId(Long roleId) {
        Map<String, Object> params = Maps.newHashMapWithExpectedSize(1);
        params.put("roleId", roleId);
        // language=SQL
        return sharpService.query("SELECT\n" +
                        "    id, u.username, u.name\n" +
                        "FROM\n" +
                        "    sys_auth_user_role ur\n" +
                        "        " +
                        "INNER JOIN sys_user u ON ur.user_id = u.id\n" +
                        "WHERE\n" +
                        "        role_id = :roleId\n" +
                        "ORDER BY\n" +
                        "    id"
                , params
                , UserDTO.class);
    }


    public RoleInfoDTO getSettingsInfoByRoleId(long roleId) {
        RoleInfoDTO roleInfoDTO = new RoleInfoDTO();
        roleInfoDTO.setPermissionList(getPermissionsNodesByRoleId(roleId));
        roleInfoDTO.setUserList(getUsersByRoleId(roleId));

        return roleInfoDTO;
    }

    /**
     * 编辑角色
     * @param roleList
     */
    public List<Role> editRoles(List<Role> roleList) {
        int size = roleList.size();

        // 更新
        String updateSQL = "update sys_auth_role set name = ?, sort = ? where id = ?";
        List<Object[]> updateParams = Lists.newArrayListWithExpectedSize(size);
        List<Role> roleAddList = Lists.newArrayListWithExpectedSize(size);
        List<Role> roleDelList = Lists.newArrayListWithExpectedSize(size);

        List<Role> editableList = roleRepository.findAll();

        for (Role role : editableList) {
            if (!roleList.contains(role)) {
                roleDelList.add(role);
            }
        }
        int seqIndex = 1;
        for (Role role : roleList) {
            role.setSort(seqIndex++);

            if (Objects.isNull(role.getId())) {
                role.setValue(RandomStringUtils.randomAlphanumeric(10));
                roleAddList.add(role);
            } else {
                updateParams.add(new Object[] {role.getName(), role.getSort(), role.getId()});
            }
        }
        roleRepository.saveAll(roleAddList); // 新增
        jdbcTemplate.batchUpdate(updateSQL, updateParams); // 修改
        roleRepository.deleteInBatch(roleDelList); // 删除

        return findAll();
    }

    public void removeRoleByUserId(Long roleId, String userId) {
        jdbcTemplate.update("DELETE FROM sys_auth_user_role WHERE role_id = ? and user_id = ?", roleId, userId);
    }

}
