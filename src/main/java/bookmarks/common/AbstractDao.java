package bookmarks.common;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bookmarks.common.converter.Converter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractDao<E, D, ID, R extends JpaRepository<E, ID>> {
    protected final Converter<E, D> converter;
    protected final R repository;

    public void delete(D domain) {
        repository.delete(converter.convertDomain(domain));
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public Optional<D> findById(ID id) {
        return repository.findById(id).map(converter::convertEntity);
    }

    public void save(D domain) {
        repository.save(converter.convertDomain(domain));
    }
}
