package io.harsh.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;

import io.harsh.ipldashboard.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {
    
    Team findByTeamName(String teamName);

}
