package com.rick.project.admin.plugin.ztree.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TreeNode implements Serializable {

	private Long id;
	
	private String name;

    @JsonProperty("pId")
	private Long pId;

	private boolean open;

	private String icon;
	
	private String iconSkin;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TreeNode)) return false;

		TreeNode treeNode = (TreeNode) o;

		return getId().equals(treeNode.getId());
	}

	@Override
	public int hashCode() {
		return id.intValue();
	}
}
