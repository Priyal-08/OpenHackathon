package com.openhack.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.Expense;

@Repository
public class JpaExpenseDao implements ExpenseDao{
	
	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;

	@Transactional
	@Override
	public Expense store(Expense expense) {
		return entityManager.merge(expense);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Expense> findByHackathonId(long id) {
		Query query =
				 entityManager.createNativeQuery("SELECT * FROM expense where hackathonid=:id", Expense.class);
		query.setParameter("id", id);
		return query.getResultList();
	}

	@Transactional
	@Override
	public Expense findById(long id) {
		return entityManager.find(Expense.class, id);
	}

}
