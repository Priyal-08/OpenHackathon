package com.openhack.dao;

import java.math.BigInteger;
import java.util.List;

import com.openhack.domain.Participant;
import com.openhack.domain.Payment;
import com.openhack.domain.Team;

public interface ParticipantDao {

	public Team store(Team team);
	
	public Participant store(Participant participant);
	/*
	 * Get list of hackathon ids by user id.
	 *
	 * @param id: the participant/user id
	 * @return the list of hackathon ids
	 */
	public List<BigInteger> findHackathonByUserId(long id);
	
	/*
	 * Get team by user id and hackathon id.
	 *
	 * @param id: the participant/user id
	 * @return the list of hackathon ids
	 */
	public Team findTeamByUserIdAndHackathonId(long userId, long hackathonId);
	
	public List <Team> findTeamsByHackathonId(long hackathonId);
	
	public Team findTeamByNameAndHackathon(String teamName, long hackathonId);
	
	public Participant findParticipantByToken(String token);
	
	public List <Participant> findParticipantsByTeam(long team_id);

	
	public Payment store(Payment payment);

	public Team findById(long id);

	
	
	
}
