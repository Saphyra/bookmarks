package bookmarks.domain.category;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryConverter extends ConverterBase<CategoryEntity, Category> {
    private final StringEncryptor stringEncryptor;

    @Override
    protected CategoryEntity processDomainConversion(Category domain) {
        return CategoryEntity.builder()
            .categoryId(domain.getCategoryId())
            .root(domain.getRoot())
            .userId(domain.getUserId())
            .label(stringEncryptor.encryptEntity(domain.getLabel(), domain.getUserId()))
            .description(stringEncryptor.encryptEntity(domain.getDescription(), domain.getUserId()))
            .build();
    }

    @Override
    protected Category processEntityConversion(CategoryEntity entity) {
        return Category.builder()
            .categoryId(entity.getCategoryId())
            .root(entity.getRoot())
            .userId(entity.getUserId())
            .label(stringEncryptor.decryptEntity(entity.getLabel(), entity.getUserId()))
            .description(stringEncryptor.decryptEntity(entity.getDescription(), entity.getUserId()))
            .build();
    }
}
