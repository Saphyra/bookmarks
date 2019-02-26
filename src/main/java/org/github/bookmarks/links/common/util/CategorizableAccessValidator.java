package org.github.bookmarks.links.common.util;

import java.util.List;

import org.github.bookmarks.links.Categorizable;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;

@Component
public class CategorizableAccessValidator {

    public void validateAccessOfDataResponse(List<DataResponse> list, String userId){
        list.forEach(dataResponse -> validateAccess(dataResponse.getElement(), userId));
    }

    public void validateAccess(List<Categorizable> list, String userId) {
        list.forEach(categorizable -> validateAccess(categorizable, userId));
    }

    private void validateAccess(Categorizable categorizable, String userId){
        if(!categorizable.getUserId().equals(userId)){
            throw new ForbiddenException(userId + " has no access to categorizable " + categorizable.getId());
        }
    }
}
