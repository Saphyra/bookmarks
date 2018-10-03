package bookmarks.domain.category;

import org.springframework.stereotype.Component;

import bookmarks.common.converter.ConverterBase;
import bookmarks.common.encryption.StringEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryConverter extends ConverterBase<CategoryEntity, Category> {
    private final StringEncryptor stringEncryptor;

    @Override
    protected CategoryEntity convertDomainInternal(Category domain) {
        return CategoryEntity.builder()
            .categoryId(domain.getCategoryId())
            .parentId(domain.getParentId())
            .userId(domain.getUserId())
            .text(stringEncryptor.encryptEntity(domain.getText(), domain.getUserId()))
            .description(stringEncryptor.encryptEntity(domain.getDescription(), domain.getUserId()))
            .build();
    }

    @Override
    protected Category convertEntityInternal(CategoryEntity entity) {
        return Category.builder()
            .categoryId(entity.getCategoryId())
            .parentId(entity.getParentId())
            .userId(entity.getUserId())
            .text(stringEncryptor.decryptEntity(entity.getText(), entity.getUserId()))
            .description(stringEncryptor.decryptEntity(entity.getDescription(), entity.getUserId()))
            .build();
    }
}
