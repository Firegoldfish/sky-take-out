package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
        public void save(CategoryDTO categoryDTO) {
            Category category = new Category();
            BeanUtils.copyProperties(categoryDTO, category);
            category.setStatus(StatusConstant.DISABLE);
            category.setCreateTime(LocalDateTime.now());
            category.setUpdateTime(LocalDateTime.now());
            category.setCreateUser(BaseContext.getCurrentId());
            category.setUpdateUser(BaseContext.getCurrentId());
            categoryMapper.insert(category);
        }
}
