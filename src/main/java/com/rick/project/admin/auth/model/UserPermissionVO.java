package com.rick.project.admin.auth.model;

import com.rick.project.admin.auth.entity.Role;
import com.rick.project.admin.plugin.ztree.model.TreeNode;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 12:13 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Data
@Builder
public class UserPermissionVO {

    private List<Role> roleList;

    private List<TreeNode> permissionList;

}
