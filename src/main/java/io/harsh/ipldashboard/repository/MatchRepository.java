package io.harsh.ipldashboard.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


import io.harsh.ipldashboard.model.Match;

public interface MatchRepository extends CrudRepository<Match, Long> {
    
    //telling JPA to get all the matches where team1 = teamName1 OR team2 = teamName2
    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    default List<Match> findLatestMatchByTeam(String teamName, int count){
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
