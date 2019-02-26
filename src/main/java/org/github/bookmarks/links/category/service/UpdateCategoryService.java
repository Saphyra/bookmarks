package org.github.bookmarks.links.category.service;

import static java.util.Objects.isNull;
import static org.github.bookmarks.common.util.Util.replaceIfNotNull;

import java.util.List;

import org.github.bookmarks.links.category.controller.request.UpdateCategoryRequest;
import org.github.bookmarks.links.category.dataaccess.CategoryDao;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.common.util.CategoryUtil;
import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateCategoryService {
    private final CategoryDao categoryDao;
    private final CategoryQueryService categoryQueryService;
    private final CategoryUtil categoryUtil;

    public void update(List<UpdateCategoryRequest> requests, String userId) {
        requests.forEach(request -> updateCategory(userId, request));
    }

    private void updateCategory(String userId, UpdateCategoryRequest request) {
        try {
            Category category = categoryQueryService.findByIdAuthorized(userId, request.getCategoryId());

            categoryUtil.validateRoot(request.getRoot(), userId);

            category.setLabel(replaceIfNotNull(category.getLabel(), request.getLabel()));
            category.setDescription(replaceIfNotNull(category.getDescription(), request.getDescription()));

            validateRoot(request.getRoot(), category.getRoot(), category.getCategoryId());

            category.setRoot(replaceIfNotNull(category.getRoot(), request.getRoot()));

            categoryDao.save(category);
        } catch (RuntimeException e) {
            log.error("Error occurred during category update: {}", e);
        }
    }

    private void validateRoot(String newRoot, String oldRoot, String categoryId) {
        if (isNull(newRoot)) {
            return;
        }

        if (newRoot.equals(oldRoot)) {
            return;
        }

        if (newRoot.equals(categoryId)) {
            throw new BadRequestException("New root must not equal categoryId");
        }

        validateNotChildren(categoryId, newRoot);
    }

    private void validateNotChildren(String categoryId, String newRoot) {
        categoryDao.getByRoot(categoryId).stream()
            .peek(category -> validateNotChildren(category.getCategoryId(), newRoot))
            .filter(category -> category.getCategoryId().equals(newRoot))
            .findAny()
            .ifPresent(c -> {
                throw new BadRequestException("New root must not be any of the category's children.");
            });
    }
}
