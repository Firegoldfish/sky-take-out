package com.sky.service;

import com.sky.dto.DishDTO;

public interface DishService {
    /**
     * 保存菜品以及口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
