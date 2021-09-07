package com.rick.project.admin;

import com.rick.meta.dict.dao.dataobject.DictDO;
import com.rick.meta.dict.service.DictService;
import com.rick.meta.props.service.PropertyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-09-07 05:22:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MetaTest {

    @Autowired
    private DictService dictService;

    @Autowired
    private PropertyService propertyService;

    @Test
    public void testList() {
        List<DictDO> sexList = dictService.getDictByType("sex");
        Assert.assertEquals(2, sexList.size());
    }

    @Test
    public void testGetOne() {
        DictDO dictDO = dictService.getDictByTypeAndName("sex", "F").get();
        Assert.assertEquals("女", dictDO.getLabel());
    }

    @Test
    public void testDictYml() {
        Assert.assertEquals(2, dictService.getDictByType("grade").size());
        Assert.assertEquals("一年级",dictService.getDictByTypeAndName("grade", "g1").get().getLabel());
        Assert.assertEquals("Rick",dictService.getDictByTypeAndName("user", "jkxyx205").get().getLabel());
        Assert.assertEquals("男",dictService.getDictByTypeAndName("sex", "M").get().getLabel());
    }

    @Test
    public void testGetProperty() {
        String property = propertyService.getProperty("hello");
        Assert.assertEquals("world", property);
    }

    @Test
    public void testSetProperty() {
        propertyService.setProperty("gg" + System.currentTimeMillis(), "dd");
    }

}
