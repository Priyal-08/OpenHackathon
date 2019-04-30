package com.openhack.dao;

import java.math.BigInteger;
import java.util.List;

public interface ParticipantDao {

	/*
	 * Get team by user id.
	 *
	 * @param id: the participant/user id
	 * @return the list of hackathon ids
	 */
	public List<BigInteger> findHackathonByUserId(long id);
}
