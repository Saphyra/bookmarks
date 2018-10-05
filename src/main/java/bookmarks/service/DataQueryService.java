package bookmarks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import bookmarks.common.exception.ForbiddenException;
import bookmarks.controller.response.DataResponse;
import bookmarks.dataaccess.CategoryDao;
import bookmarks.dataaccess.LinkDao;
import bookmarks.domain.Categorizable;
import bookmarks.domain.category.Category;
import bookmarks.domain.link.Link;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataQueryService {
    private final CategoryDao categoryDao;
    private final LinkDao linkDao;

    public List<DataResponse> getDataOfUser(String userId, String parentId) {
        List<Category> categories = categoryDao.getByParentId(parentId);
        List<Link> links = linkDao.getByCategoryId(parentId);

        List<Categorizable> categorizables = new ArrayList<>();
        categorizables.addAll(categories);
        categorizables.addAll(links);
        validateAccess(categorizables, userId);

        return convertToResponse(categorizables);
    }

    private void validateAccess(List<Categorizable> list, String userId) {
        list.forEach(i -> {
            if (!i.getUserId().equals(userId)) {
                throw new ForbiddenException(userId + " has no access to categorizable " + i.getId());
            }
        });
    }

    private List<DataResponse> convertToResponse(List<Categorizable> categorizables) {
        return categorizables.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    private DataResponse convertToResponse(Categorizable categorizable){
        DataResponse response = new DataResponse();
        response.setElement(categorizable);
        response.setType(categorizable instanceof Link ? DataResponse.Type.LINK : DataResponse.Type.CATEGORY);
        return response;
    }
}
