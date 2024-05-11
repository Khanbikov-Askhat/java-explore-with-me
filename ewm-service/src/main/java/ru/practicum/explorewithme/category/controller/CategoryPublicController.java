package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public List<CategoryDto> findCategories(@RequestParam(required = false, defaultValue = "0") Integer from,
                                            @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос GET /categories c параметрами: from = {}, size = {}", from, size);
        List<CategoryDto> categoryDtos = categoryService.findCategories(from, size);
        log.info("Получены категории: {}", categoryDtos);
        return categoryDtos;
    }

    @GetMapping("categories/{catId}")
    public CategoryDto findCategoryById(@PathVariable Long catId) {
        log.info("Получен запрос GET /categories/{catId} c параметрами: catId = {}", catId);
        CategoryDto category = categoryService.findCategoryById(catId);
        log.info("Получена категория: {}", category);
        return category;
    }
}
