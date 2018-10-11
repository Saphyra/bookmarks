package bookmarks.util;

import org.springframework.stereotype.Component;

import bookmarks.common.exception.ForbiddenException;
import bookmarks.common.exception.NotFoundException;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.domain.category.Category;
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
