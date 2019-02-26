package org.github.bookmarks.links.common.util;

import org.github.bookmarks.links.category.dataaccess.CategoryDao;
import org.github.bookmarks.links.category.domain.Category;
import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryUtil {
    private final CategoryDao categoryDao;

    public void validateRoot(String categoryId, String userId){
        if(categoryId != null && !categoryId.isEmpty()){
            Category category = categoryDao.findById(categoryId).orElseThrow(() -> new NotFoundException("category not found with id " + categoryId));
            if(!category.getUserId().equals(userId)){
                throw new ForbiddenException(userId + " has no access to category " + categoryId);
            }
        }
    }
}
