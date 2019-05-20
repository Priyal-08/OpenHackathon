package com.openhack.dao;

import java.util.List;

import com.openhack.domain.Expense;

/**
 * The Interface ExpenseDao.
 */
public interface ExpenseDao {

	 /*
	 * Create expense.
	 *
	 * @param expense: the expense
	 * @return the expense
	 */
	public Expense store(Expense expense);
	
	/*
	 * Get all expenses for hackathon.
	 *
	 @return list of expenses for hackathon
	 */
	public List<Expense> findByHackathonId(long id);
	
	/*
	 * Get expense entity by  id.
	 *
	 * @param id: the expense id
	 * @return the Expense details
	 */
	public Expense findById(long id);
	
}
