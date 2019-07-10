package com.mysite.service.impl;

import com.mysite.model.po.Option;
import com.mysite.service.OptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionServiceImplTest {

    @Autowired
    private OptionService optionService;

    @Test
    public void deleteOptionByName() {
    }

    @Test
    public void updateOptionByName() {
    }

    @Test
    public void saveOptions() {
    }

    @Test
    public void getOptionByName() {
        Option option = optionService.getOptionByName("site_theme");
        System.out.println(option);
    }

    @Test
    public void getOptions() {
    }
}