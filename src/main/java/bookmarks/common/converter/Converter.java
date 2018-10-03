package bookmarks.common.converter;

import java.util.List;

public interface Converter<E, D> {
    D convertEntity(E entity);
    List<D> convertEntity(List<E> entity);

    E convertDomain(D domain);
    List<E> convertDomain(List<D> domain);

}
