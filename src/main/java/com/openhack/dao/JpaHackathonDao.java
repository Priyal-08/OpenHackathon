package com.openhack.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.openhack.domain.Hackathon;

/**
 * The Class JpaHackathonDao.
 */
@Repository
public class JpaHackathonDao implements HackathonDao {

	/** The entity manager. */
	@PersistenceContext
    private EntityManager entityManager;
	
	/* (non-Javadoc)
	 * @see com.lab2.dao.HackathonDao#store(com.openhacka.domain.Hackathon)
	 */
	@Transactional
    @Override
	public Hackathon store(Hackathon hackathon) {
		return entityManager.merge(hackathon);
	}
	
	/* (non-Javadoc)
	 * @see com.lab2.dao.HackathonDao#findAll()
	 */
	@Transactional
	@Override
	public List<Hackathon> findAll() {
		 TypedQuery<Hackathon> query =
				 entityManager.createQuery("SELECT h FROM hackathon h", Hackathon.class);
		//Query query = entityManager.createNativeQuery("SELECT * FROM hackathon", Hackathon.class);
		//if (query.getResultList().isEmpty()) return null;
		return query.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see com.lab2.dao.HackathonDao#findById(long)
	 */
	@Transactional
	@Override
	public Hackathon findById(long id) {
		return entityManager.find(Hackathon.class, id);
	}
	
	/* (non-Javadoc)
	 * @see com.lab2.dao.HackathonDao#findByEventName(java.lang.String)
	 */
	@Transactional
    @Override
	public Hackathon findByEventName(String eventName) {
		Query query = entityManager.createNativeQuery("SELECT * FROM hackathon e WHERE e.eventName LIKE :eventName", Hackathon.class);
		query.setParameter("eventName", eventName);
		if (query.getResultList().isEmpty()) return null;
		return (Hackathon)query.getResultList().get(0);
	}

	/* (non-Javadoc)
	 * @see com.lab2.dao.HackathonDao#delete(com.openhacka.domain.Hackathon)
	 */
	@Transactional
	@Override
	public void delete(Hackathon hackathon) {
		entityManager.remove(hackathon);
	}
}
