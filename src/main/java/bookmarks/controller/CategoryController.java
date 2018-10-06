package bookmarks.controller;

import bookmarks.controller.request.CreateCategoryRequest;
import bookmarks.filter.FilterHelper;
import bookmarks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private static final String CREATE_CATEGORY_MAPPING = "category";

    private final CategoryService categoryService;

    @PutMapping(CREATE_CATEGORY_MAPPING)
    public void createCategory(
        @RequestBody @Valid CreateCategoryRequest request,
        @CookieValue(FilterHelper.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to create a new category.", userId);
        categoryService.create(request, userId);
    }
}
