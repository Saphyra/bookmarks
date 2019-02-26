package org.github.bookmarks.links.category.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.github.bookmarks.links.Categorizable;
import org.github.bookmarks.links.category.dataaccess.CategoryDao;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.common.controller.response.DataTreeResponse;
import org.github.bookmarks.links.common.util.CategorizableAccessValidator;
import org.github.bookmarks.links.common.util.CategorizableToDataResponseConverter;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryQueryService {
    public static final String ROOT_ID = "";
    private static final String ROOT_LABEL = "Root";

    private final CategoryDao categoryDao;
    private final CategorizableAccessValidator categorizableAccessValidator;
    private final CategorizableToDataResponseConverter converter;

    Category findByIdAuthorized(String userId, String categoryId) {
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found with id " + categoryId));
        if (!category.getUserId().equals(userId)) {
            throw new ForbiddenException(userId + " has no access for category " + categoryId);
        }
        return category;
    }

    DataResponse findCategory(String userId, String categoryId) {
        return converter.convertEntity(findByIdAuthorized(userId, categoryId));
    }

    public List<Category> getByUserId(String userId) {
        return categoryDao.getByUserId(userId);
    }

    List<DataResponse> getCategoriesOfParent(String userId, String parent) {
        List<Category> categories = getCategoriesByRootAndUserId(parent, userId);

        List<Categorizable> categorizables = new ArrayList<>(categories);
        categorizableAccessValidator.validateAccess(categorizables, userId);

        return converter.convertEntity(categorizables);
    }

    private List<Category> getCategoriesByRootAndUserId(String root, String userId) {
        return categoryDao.getByRootAndUserId(root, userId);
    }

    List<DataTreeResponse> getCategoryTree(String userId) {
        DataTreeResponse root = new DataTreeResponse();
        root.setCategory(createRootCategory());
        root.setChildren(getCategoryTree(ROOT_ID, userId));
        return Arrays.asList(root);
    }

    private Category createRootCategory() {
        return Category.builder()
            .categoryId(ROOT_ID)
            .label(ROOT_LABEL)
            .build();
    }

    private List<DataTreeResponse> getCategoryTree(String root, String userId) {
        return categoryDao.getByRootAndUserId(root, userId).stream()
            .map(category -> {
                DataTreeResponse response = new DataTreeResponse();
                response.setCategory(category);
                response.setChildren(getCategoryTree(category.getCategoryId(), userId));
                return response;
            })
            .collect(Collectors.toList());
    }
}
