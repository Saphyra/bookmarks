package org.github.bookmarks.links.category.service;

import org.github.bookmarks.links.category.controller.request.CreateCategoryRequest;
import org.github.bookmarks.links.category.dataaccess.CategoryDao;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.user.UserFacade;
import org.springframework.stereotype.Service;

import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateCategoryService {
    private final CategoryDao categoryDao;
    private final IdGenerator idGenerator;
    private final UserFacade userFacade;

    public void create(CreateCategoryRequest request, String userId) {
        userFacade.checkUserWithIdExists(userId);

        Category category = Category.builder()
            .categoryId(idGenerator.generateRandomId())
            .root(request.getRoot())
            .userId(userId)
            .label(request.getLabel())
            .description(request.getDescription())
            .build();
        categoryDao.save(category);
    }
}
