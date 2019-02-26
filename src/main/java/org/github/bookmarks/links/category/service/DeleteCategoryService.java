package org.github.bookmarks.links.category.service;

import java.util.List;

import javax.transaction.Transactional;

import org.github.bookmarks.links.category.dataaccess.CategoryDao;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.link.LinkFacade;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteCategoryService {
    private final CategoryDao categoryDao;
    private final CategoryQueryService categoryQueryService;
    private final LinkFacade linkFacade;

    @Transactional
    public void delete(List<String> categoryIds, String userId) {
        categoryIds.forEach(categoryId -> deleteCategory(userId, categoryId));
    }

    private void deleteCategory(String userId, String categoryId) {
        try {
            Category category = categoryQueryService.findByIdAuthorized(userId, categoryId);
            updateChildren(categoryId, userId);
            categoryDao.delete(category);
        } catch (RuntimeException e) {
            log.error("Exception occurred during category deletion: {}", e);
        }
    }

    private void updateChildren(String categoryId, String userId) {
        List<Category> categories = categoryDao.getByRootAndUserId(categoryId, userId);

        categories.forEach(category -> {
            category.setRoot("");
            categoryDao.save(category);
        });

        linkFacade.updateParent(categoryId, userId, CategoryQueryService.ROOT_ID);
    }
}
