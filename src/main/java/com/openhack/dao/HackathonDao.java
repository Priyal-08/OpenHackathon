package com.openhack.dao;

import java.util.List;

import com.openhack.domain.Hackathon;

/**
 * The Interface HackathonDao.
 */
public interface HackathonDao {

	 /*
	 * Create/ Update hackathon entity.
	 *
	 * @param hackathon: the hackathon
	 * @return the employer
	 */
	public Hackathon store(Hackathon hackathon);
	
	/*
	 * Get all hackathons.
	 *
	 @return list of Hackathon
	 */
	public List<Hackathon> findAll();
	
	/*
	 * Get hackathon entity by hackathon id.
	 *
	 * @param id: the hackathon id
	 * @return the Hackathon
	 */
	public Hackathon findById(long id);
	
	/*
	 * Get hackathon entity by event name.
	 *
	 * @param eventName: the eventName
	 * @return the Hackathon
	 */
	public Hackathon findByEventName(String eventName);
	
	/*
	 * Get hackathon entity by event name.
	 *
	 * @param hackathon: the hackathon
	 */
	public void delete(Hackathon hackathon);
}
