package com.rick.project.admin.common;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By www.xhope.top
 *
 * @version V1.0
 * @Description: (用一句话描述该文件做什么)
 * @author: Rick.Xu
 * @date: 12/19/19 4:39 PM
 * @Copyright: 2019 www.yodean.com. All rights reserved.
 */
@Service
public class DbService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * 维护关联表关系 全量
     * @param tableName 关联表名称
     * @param masterName 关联的对象列名
     * @param refName 被关联的对象列名
     * @param masterId 关联的对象列值
     * @param refIds 被关联的对象列名值
     */
    public void updateRelationShip(String tableName, String masterName, String refName, long masterId, Set<?> refIds) {
        // 删除
        if (CollectionUtils.isEmpty(refIds)) {
            jdbcTemplate.update(String.format("delete from %s where %s = ?", tableName, masterName), masterId);
            return;
        }

        Map deleteParams = Maps.newHashMapWithExpectedSize(1);
        deleteParams.put("refIds", refIds);
        deleteParams.put("masterId", masterId);

        namedParameterJdbcTemplate.update(String.format("delete from %s where %s = :masterId and %s not in (:refIds)", tableName, masterName, refName), deleteParams);

        String queryALLRefIdSQL = String.format("select %s from %s where %s = ?", refName, tableName, masterName);

        // 库中
        List<?> dbRefIds = jdbcTemplate.queryForList(queryALLRefIdSQL, refIds.iterator().next().getClass(), masterId);

        // 新增
        List<?> newRefIds = refIds.stream().filter(newRefId -> !dbRefIds.contains(newRefId)).collect(Collectors.toList());

        if (newRefIds.size() == 0) {
            return;
        }

        String insertSQL = String.format("insert into %s(%s, %s) values(?, ?)", tableName, masterName, refName);

        List<Object[]> addParams = newRefIds.stream().map(refId -> new Object[] {masterId, refId}).collect(Collectors.toList());

        jdbcTemplate.batchUpdate(insertSQL, addParams);
    }
}
