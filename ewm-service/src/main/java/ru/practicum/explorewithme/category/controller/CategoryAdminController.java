package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping(value = "admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("Получен запрос POST /admin/categories c новой категорией: {}", categoryDto.getName());
        CategoryDto category = categoryService.addCategory(categoryDto);
        log.info("Добавлена категория: {}", category);
        return category;
    }

    @DeleteMapping(value = "admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCategory(@PathVariable Long catId) {
        log.info("Получен запрос DELETE /admin/categories/{}", catId);
        categoryService.removeCategory(catId);
        log.info("Удалена категория с ID: {}", catId);
    }

    @PatchMapping(value = "admin/categories/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId,
                                      @Valid @RequestBody CategoryDto categoryDto) {
        log.info("Получен запрос PATCH /admin/categories/{} c новыми значениями категории: {}", catId, categoryDto);
        CategoryDto category = categoryService.updateCategory(catId, categoryDto);
        log.info("Обновлена категория: {}", categoryDto);
        return category;
    }

}
