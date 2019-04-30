package com.openhack.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

}
