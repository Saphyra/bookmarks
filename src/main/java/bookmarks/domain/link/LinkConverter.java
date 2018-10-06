package bookmarks.domain.link;

import org.springframework.stereotype.Component;

import bookmarks.common.converter.ConverterBase;
import bookmarks.common.encryption.StringEncryptor;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LinkConverter extends ConverterBase<LinkEntity, Link> {
    private final StringEncryptor stringEncryptor;

    @Override
    protected LinkEntity convertDomainInternal(Link domain) {
        return LinkEntity.builder()
            .linkId(domain.getLinkId())
            .root(domain.getRoot())
            .userId(domain.getUserId())
            .label(stringEncryptor.encryptEntity(domain.getLabel(), domain.getUserId()))
            .url(stringEncryptor.encryptEntity(domain.getUrl(), domain.getUserId()))
            .archived(domain.getArchived())
            .build();
    }

    @Override
    protected Link convertEntityInternal(LinkEntity entity) {
        return Link.builder()
            .linkId(entity.getLinkId())
            .root(entity.getRoot())
            .userId(entity.getUserId())
            .label(stringEncryptor.decryptEntity(entity.getLabel(), entity.getUserId()))
            .url(stringEncryptor.decryptEntity(entity.getUrl(), entity.getUserId()))
            .archived(entity.getArchived())
            .build();
    }
}
