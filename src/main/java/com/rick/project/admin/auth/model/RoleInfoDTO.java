package com.rick.project.admin.auth.model;

import com.rick.project.admin.plugin.ztree.model.TreeNode;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/20/19 11:00 AM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Data
public class RoleInfoDTO implements Serializable {

    private List<UserDTO> userList;

    private List<TreeNode> permissionList;
}
