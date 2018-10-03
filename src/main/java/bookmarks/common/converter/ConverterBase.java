package bookmarks.common.converter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ConverterBase<E, D> implements Converter<E, D> {

    @Override
    public List<D> convertEntity(List<E> entity) {
        if(entity == null){
            return  null;
        }
        return entity.stream().map(this::convertEntity).collect(Collectors.toList());
    }

    @Override
    public List<E> convertDomain(List<D> domain) {
        if(domain == null){
            throw new IllegalArgumentException("domain must not be null.");
        }
        return domain.stream().map(this::convertDomain).collect(Collectors.toList());
    }
}
