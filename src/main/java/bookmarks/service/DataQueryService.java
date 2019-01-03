package bookmarks.service;

import bookmarks.controller.request.data.FilteredRequest;
import bookmarks.controller.response.DataResponse;
import bookmarks.controller.response.DataTreeResponse;
import bookmarks.domain.Categorizable;
import bookmarks.domain.category.Category;
import bookmarks.domain.link.Link;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataQueryService {
    private static final String ALL = "all";
    private static final String LINK = "link";
    private static final String CATEGORY = "category";

    private final CategoryService categoryService;
    private final LinkService linkService;

    public List<DataResponse> getCategories(String userId, String parentId) {
        List<Category> categories = categoryService.getCategoriesByRootAndUserId(parentId, userId);

        List<Categorizable> categorizables = new ArrayList<>(categories);
        validateAccess(categorizables, userId);

        return convertToResponse(categorizables);
    }

    public List<DataResponse> getDataFiltered(FilteredRequest request, String userId) {
        List<Categorizable> result = new ArrayList<>();

        if (ALL.equals(request.getType()) || CATEGORY.equals(request.getType())) {
            result.addAll(categoryService.getByUserId(userId));
        }
        if (ALL.equals(request.getType()) || LINK.equals(request.getType())) {
            result.addAll(linkService.getByUserId(userId));
        }

        List<Categorizable> filtered = result.stream()
            .filter(item -> "".equals(request.getLabel()) || item.getLabel().toLowerCase().contains(request.getLabel().toLowerCase()))
            .filter(item -> "".equals(request.getSecondary()) || item.getSecondary().toLowerCase().contains(request.getSecondary().toLowerCase()))
            .collect(Collectors.toList());

        return convertToResponse(filtered);
    }

    public List<DataResponse> getDataOfRoot(String userId, String categoryId) {
        Category category = categoryService.findByIdAuthorized(userId, categoryId);
        return getDataOfCategory(userId, category.getRoot());
    }

    public List<DataResponse> getDataOfCategory(String userId, String parentId) {
        List<Category> categories = categoryService.getCategoriesByRootAndUserId(parentId, userId);
        List<Link> links = linkService.getLinksByRootAndUserId(parentId, userId);

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

    private DataResponse convertToResponse(Categorizable categorizable) {
        DataResponse response = new DataResponse();
        response.setElement(categorizable);
        response.setType(categorizable instanceof Link ? DataResponse.Type.LINK : DataResponse.Type.CATEGORY);
        return response;
    }

    public List<DataTreeResponse> getCategoryTree(String userId) {
        return categoryService.getCategoryTree(userId);
    }
}
