package io.harsh.ipldashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.harsh.ipldashboard.model.Team;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final EntityManager em;

  @Autowired
  public JobCompletionNotificationListener(EntityManager em) {
    this.em = em;
  }

//   run this method after the job is complete and give me the job execution to figure out what the status is
  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      Map<String, Team> teamData = new HashMap<>();
      
      // hey JPA, create this query and looking at each individual team in team 1 column and total number of matches they have played as team1 and I want each row in that result needs to be an object array. the first element of that object array will be a string and second element will be count
      em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
        .getResultList() //list of object array <string, int>
        .stream()
        .map(e -> new Team((String) e[0], (long) e[1])) //map it to team instance
        .forEach(team -> teamData.put(team.getTeamName(), team)); //for each of those team instances created, put it into teamData. Basically it does a lookup

      em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
        .getResultList()
        .stream()
        .forEach(e ->{
            Team team = teamData.get((String) e[0]);
            team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
        });

      em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
        .getResultList()
        .stream()
        .forEach( e->{
            Team team = teamData.get((String) e[0]);
            if(team!= null) team.setTotalWins((long) e[1]);
        });
      
      teamData.values().forEach(team -> em.persist(team));

      teamData.values().forEach(team -> System.out.println(team));

    }
  }
}
