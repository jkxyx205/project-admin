package com.rick.project.admin.auth.controller;

import com.rick.db.dto.Grid;
import com.rick.db.dto.PageModel;
import com.rick.db.plugin.GridHttpServletRequestUtils;
import com.rick.db.plugin.GridUtils;
import com.rick.project.admin.auth.service.RoleService;
import com.rick.project.admin.auth.service.UserService;
import com.rick.project.admin.common.Result;
import com.rick.project.admin.common.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 9/10/19 1:55 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * login页面
     *
     * @return
     */
    @GetMapping("page")
    public String gotoUserPage() {
        return "sys/user";
    }

    /**
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('auth')")
    public Result<Grid<Map<String, Object>>> list(HttpServletRequest request) {
        String sql = "SELECT id, username, name, status FROM sys_user WHERE username = :username";
        return ResultUtils.success(GridHttpServletRequestUtils.list(sql, request));
    }
}
