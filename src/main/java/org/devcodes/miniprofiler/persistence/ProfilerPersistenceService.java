package org.devcodes.miniprofiler.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.devcodes.miniprofiler.ProfilerRequest;
import org.devcodes.miniprofiler.annotations.ProfilerIgnore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A Spring @Service implemented using JPA which can be used to persist and retrieve profiler results.
 * 
 * @author Niall Thomson
 */
@Service
@Transactional
@ProfilerIgnore
public class ProfilerPersistenceService {
	@PersistenceContext
    private EntityManager entityManager;
	
	public void save(ProfilerRequest request) {
		entityManager.persist(request);
	}
	
	public ProfilerRequest getLatestRequest(String sessionId) {
		String query = "SELECT r FROM ProfilerRequest r "
			+"WHERE r.sessionId = ?1 ORDER BY r.date DESC";

		List<ProfilerRequest> result = entityManager.createQuery(query, ProfilerRequest.class)
		.setParameter(1, sessionId)
		.setMaxResults(1)
		.getResultList();
		
		if(result.size() == 1) {
		return result.get(0);
		}
		
		return null;
	}
}
