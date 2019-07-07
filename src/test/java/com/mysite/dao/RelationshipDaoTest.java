package com.mysite.dao;

import com.mysite.model.entity.Relationship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMybatis
public class RelationshipDaoTest {

    @Autowired
    private RelationshipDao relationshipDao;

    @Test
    public void testGetRelationByCid() {
        List<Relationship> relationships = relationshipDao.getRelationshipByCid(1);
        System.out.println(relationships);
    }

    @Test
    public void testGetRelationByMid() {
        List<Relationship> relationships = relationshipDao.getRelationshipByMid(1);
        System.out.println(relationships);
    }

}