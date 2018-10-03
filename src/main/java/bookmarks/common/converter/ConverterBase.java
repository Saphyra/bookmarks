package bookmarks.common.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ConverterBase<E, D> implements Converter<E, D> {
    @Override
    public D convertEntity(E entity) {
        if(entity == null){
            return null;
        }

        return convertEntityInternal(entity);
    }

    @Override
    public Optional<D> convertEntity(Optional<E> entity) {
        return convertEntityToOptional(entity.orElse(null));
    }

    @Override
    public Optional<D> convertEntityToOptional(E entity) {
        return Optional.ofNullable(convertEntity(entity));
    }

    @Override
    public List<D> convertEntity(List<E> entity) {
        if (entity == null) {
            return null;
        }
        return entity.stream().map(this::convertEntity).collect(Collectors.toList());
    }

    @Override
    public E convertDomain(D domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null");
        }

        return convertDomainInternal(domain);
    }

    @Override
    public Optional<E> convertDomain(Optional<D> domain) {
        return convertDomainToOptional(domain.orElseThrow(() -> new IllegalArgumentException("domain must not be null.")));
    }

    @Override
    public Optional<E> convertDomainToOptional(D domain) {
        return Optional.of(convertDomain(domain));
    }

    @Override
    public List<E> convertDomain(List<D> domain) {
        if (domain == null) {
            throw new IllegalArgumentException("domain must not be null.");
        }
        return domain.stream().map(this::convertDomain).collect(Collectors.toList());
    }

    abstract protected E convertDomainInternal(D domain);
    abstract protected D convertEntityInternal(E entity);
}
