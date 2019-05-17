package com.openhack.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.Participant;
import com.openhack.domain.Payment;
import com.openhack.domain.Team;
import com.openhack.domain.UserAccount;

@Repository
public class JpaParticipantDao implements ParticipantDao{
	
	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.lab2.dao.ParticipantDao#findById(long)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
    @Override
	public List<BigInteger> findHackathonByUserId(long id) {
		Query query = entityManager.createNativeQuery("SELECT t.hackathon_id FROM team t WHERE t.id in (SELECT p.team_id FROM participant p WHERE p.user_id = :id)");
		query.setParameter("id", id);
		if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}

	@Transactional
	@Override
	public Team findTeamByUserIdAndHackathonId(long userId, long hackathonId) {
		Query query = entityManager.createNativeQuery("SELECT t.* FROM team t, participant p where t.id = p.team_id AND t.hackathon_id = :hackathonId AND p.user_id = :userId", Team.class);
		query.setParameter("userId", userId);
		query.setParameter("hackathonId", hackathonId);
		if (query.getResultList().isEmpty()) return null;
		return (Team)query.getResultList().get(0);
	}

	@Transactional
	@Override
	public Team store(Team team) {
		return entityManager.merge(team);
	}

	@Transactional
	@Override
	public Participant store(Participant participant) {
		return entityManager.merge(participant);
	}

	@Transactional
	@Override
	public Team findTeamByNameAndHackathon(String teamName, long hackathonId) {
		Query query = entityManager.createNativeQuery("SELECT t.* FROM team t WHERE t.hackathon_id = :hackathonId AND t.name LIKE :teamName", Team.class);
		query.setParameter("hackathonId", hackathonId);
		query.setParameter("teamName", teamName);
		if (query.getResultList().isEmpty()) return null;
		return (Team)query.getResultList().get(0);
	}

	@Override
	public Participant findParticipantByToken(String token) {
		Query query = entityManager.createNativeQuery("SELECT * FROM participant p WHERE p.payment_url LIKE :token", Participant.class);
		query.setParameter("token", token);
		if (query.getResultList().isEmpty()) return null;
		return (Participant)query.getResultList().get(0);
	}
	

	@Transactional
	@Override
	public Payment store(Payment payment) {
		return entityManager.merge(payment);
	}


}
