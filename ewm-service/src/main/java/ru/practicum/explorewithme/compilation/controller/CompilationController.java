package ru.practicum.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.compilation.service.CompilationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationController {

    private final CompilationService compService;

    //Public endpoints
    @GetMapping("/compilations")
    public List<CompilationDto> findCompilations(@RequestParam(required = false, defaultValue = "false") String pinned,
                                                 @RequestParam(required = false, defaultValue = "0") Integer from,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("Получен запрос GET /compilations c параметрами: pinned = {}, from = {}, size = {}", pinned, from, size);
        List<CompilationDto> compilations = compService.findCompilations(Boolean.valueOf(pinned), from, size);
        log.info("Получены компиляции: {}", compilations);
        return compilations;
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto findCompilationById(@PathVariable Long compId) {
        log.info("Получен запрос GET /compilations/{compId} c параметрами: compId = {}", compId);
        CompilationDto compilation = compService.findCompilationById(compId);
        log.info("Получена компиляция: {}", compilation);
        return compilation;
    }

    //Admin endpoints
    @PostMapping(value = "/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto compilationDto) {
        log.info("Получен запрос POST /admin/compilations c новой компиляцией: {}", compilationDto);
        CompilationDto compilation = compService.addCompilation(compilationDto);
        log.info("Добавлена компиляция: {}", compilation);
        return compilation;
    }

    @PatchMapping(value = "/admin/compilations/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                            @Valid @RequestBody UpdateCompilationRequest updateRequest) {
        log.info("Получен запрос PATCH /admin/compilations/{} c новыми значениями компиляции: {}", compId, updateRequest);
        CompilationDto compilation = compService.updateCompilation(compId, updateRequest);
        log.info("Обновлена компиляция: {}", compilation);
        return compilation;
    }

    @DeleteMapping(value = "/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Получен запрос DELETE /admin/compilations/{}", compId);
        log.info("Удалена компиляция с ID: {}", compId);
        compService.deleteCompilation(compId);
    }
}
