package tn.esprit.pi.services;

import java.util.List;

public interface InterfaceServices <T> {
        T add(T entity);
        T update(T entity);
        void delete(Long id);
        T getById(Long id);
        List<T> getAll();
        T assignToEvent(Long idevent,Long identity);

}
