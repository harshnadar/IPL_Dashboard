package io.harsh.ipldashboard.data;

import java.time.LocalDate;
// import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import io.harsh.ipldashboard.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);
  
    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        
        Match match = new Match();

        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        String str=matchInput.getDate();
	    // String newstr=str.substring(6)+"-"+str.substring(3, 5)+"-"+str.substring(0,2);
	    match.setDate(LocalDate.parse(str));
        // match.setDate(LocalDate.parse(matchInput.getDate()));

        match.setPlayerOfMatch(matchInput.getPlayer_of_match());
        match.setVenue(matchInput.getVenue());

        String firstBatTeam, secondBatTeam;
        
        // set team batting first and team batting second
        if("bat".equals(matchInput.getToss_decision())){
            
            String tossWin = matchInput.getToss_winner();
            String team1 = matchInput.getTeam1();
            String team2 = matchInput.getTeam2();

            firstBatTeam = tossWin;
            secondBatTeam = (tossWin.equals(team1))?team2:team1;

        } else{
            String tossWin = matchInput.getToss_winner();
            String team1 = matchInput.getTeam1();
            String team2 = matchInput.getTeam2();

            secondBatTeam = matchInput.getToss_winner();
            firstBatTeam = (tossWin.equals(team1))?team2:team1;
        }

        // set team1 to be first batting team and team 2 to be team batting second
        match.setTeam1(firstBatTeam);
        match.setTeam2(secondBatTeam);

        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setMatchWinner(matchInput.getWinner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());

        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        return match;
    }
}