package com.rick.project.admin.plugin.ztree.service;


import com.rick.db.service.SharpService;
import com.rick.project.admin.plugin.ztree.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TreeServiceImpl implements TreeService {

	@Autowired
	private SharpService sharpService;

	@Override
	public List<TreeNode> getTreeNode(String sql, Map<String, Object> params) {
		return sharpService.query(sql, params, TreeNode.class);
	}
	
}
