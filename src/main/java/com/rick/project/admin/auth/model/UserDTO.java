package com.rick.project.admin.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rick.project.admin.auth.entity.UserStatusEnum;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;
import java.util.Set;

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
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    private Set<String> roles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String imgName;

    private UserStatusEnum status;

    @Tolerate
    UserDTO() {}

}
