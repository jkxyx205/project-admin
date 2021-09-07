package com.rick.project.admin.plugin.ztree.service;

import com.rick.project.admin.plugin.ztree.model.TreeNode;

import java.util.List;
import java.util.Map;

public interface TreeService {

	/**
	 * 获取所有节点
	 * @return
	 */
	List<TreeNode> getTreeNode(String sql, Map<String, Object> params);
	
}
