package com.mysite.service.impl;

import com.github.pagehelper.PageInfo;
import com.mysite.model.dto.AttachDto;
import com.mysite.service.AttachService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachServiceImplTest {

    @Autowired
    private AttachService attachService;

    @Test
    public void addAttach() {
    }

    @Test
    public void batchAddAttach() {
    }

    @Test
    public void deleteAttach() {
    }

    @Test
    public void updateAttach() {
    }

    @Test
    public void getAttachById() {
        AttachDto attachDto = attachService.getAttachById(1);
        System.out.println(attachDto);

    }

    @Test
    public void getAtts() {
        PageInfo<AttachDto> attachDtoPageInfo = attachService.getAtts(10, 10);
        System.out.println(attachDtoPageInfo);
    }
}