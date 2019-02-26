package org.github.bookmarks.links.common.util;

import org.github.bookmarks.links.Categorizable;
import org.github.bookmarks.links.category.domain.Category;
import org.github.bookmarks.links.common.controller.response.DataResponse;
import org.github.bookmarks.links.link.domain.Link;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
public class CategorizableToDataResponseConverter extends ConverterBase<Categorizable, DataResponse> {

    @Override
    protected DataResponse processEntityConversion(Categorizable categorizable) {
        DataResponse response = new DataResponse();
        response.setElement(categorizable);
        response.setType(categorizable instanceof Link ? DataResponse.Type.LINK : DataResponse.Type.CATEGORY);
        return response;
    }

    @Override
    protected Category processDomainConversion(DataResponse dataResponse) {
        throw new UnsupportedOperationException();
    }
}
