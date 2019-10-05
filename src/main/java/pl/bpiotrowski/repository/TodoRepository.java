package pl.bpiotrowski.repository;

import pl.bpiotrowski.config.PersistenceUtil;
import pl.bpiotrowski.entity.Todo;

import javax.persistence.EntityManager;
import java.util.List;

public class TodoRepository {

    public List<Todo> findAll() {
        EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        List todos = em.createQuery("select t from Todo t").getResultList();

        em.getTransaction().commit();
        em.close();
        return todos;
    }

    public Todo findById(Long id) {
        EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        Todo todo = em.find(Todo.class, id);

        em.getTransaction().commit();
        em.close();
        return todo;
    }

    public void save(Todo todo) {
        EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        em.persist(todo);

        em.getTransaction().commit();
        em.close();
    }

    public void deleteById(Long id) {
        EntityManager em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        Todo todo = em.find(Todo.class, id);
        em.remove(todo);

        em.getTransaction().commit();
        em.close();
    }
}
