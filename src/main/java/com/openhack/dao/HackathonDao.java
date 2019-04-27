package com.openhack.dao;

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
	 * Get hackathon entity by hackathon id.
	 *
	 * @param hackathon: the hackathon
	 * @return the Hackathon
	 */
	public Hackathon findById(long id);
	
	/*
	 * Get hackathon entity by event name.
	 *
	 * @param hackathon: the hackathon
	 * @return the Hackathon
	 */
	public Hackathon findByEventName(String eventName);
}
