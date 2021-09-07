package com.rick.project.admin.auth.repository;

import com.rick.project.admin.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/19/19 2:22 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
