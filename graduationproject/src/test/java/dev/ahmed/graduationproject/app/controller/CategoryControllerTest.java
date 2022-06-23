package dev.ahmed.graduationproject.app.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Ahmed Bughra
 * @Created 3/21/2022 - 10:53 PM
 * @Project bitirmeprojesi-UyCoder
 */
class CategoryControllerTest {
    
    @Autowired
    private CategoryController controller;
    
    @Test
    public void contextLoads() throws Exception{
        assertThat(controller).isNotNull();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCategory() {
    }

    @Test
    void updateKdv() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void findAllCategories() {
    }
}
