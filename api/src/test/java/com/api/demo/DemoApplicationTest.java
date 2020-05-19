package com.api.demo;

import com.api.demo.grid.controller.GridRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class DemoApplicationTest {
    @Autowired
    private GridRestController controller;

    @Test
    public void contextLoads() throws Exception {
        //assertThat(controller).isNotNull();
    }

}