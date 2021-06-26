// Read about useEffect: https://blog.logrocket.com/guide-to-react-useeffect-hook/

import {React, useEffect, useState} from 'react';
import {useParams, Link} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';
import {PieChart} from 'react-minimal-pie-chart';

import './TeamPage.scss';

export const TeamPage = () => {

  const [team, setTeam] = useState({matches: []}); //set an initial empty default value
  const {teamName} = useParams();

  useEffect(
    () => {
      const fetchTeam = async () => {
        const response = await fetch(`${process.env.REACT_APP_API_ROOT_URL}/team/${teamName}`);
        const data = await response.json(); 
        setTeam(data);
        // console.log(team.teamName);
        // console.log(data);
      };
      fetchTeam();
    }, [teamName] //I am saying call this when teamName changes
  );

  if(!team || !team.teamName){
    return <h1>Team Not Found</h1>;
  }

  return (
    <div className="TeamPage">
      <div className="team-name-section">
        <h1 className="team-name">{team.teamName}</h1>
      </div>
      <div className="win-loss-section">
        Wins / Losses
        <PieChart
          data = {[
            { title: 'Losses', value: team.totalMatches - team.totalWins, color: '#a84f5f' },
            { title: 'Wins', value: team.totalWins, color: '#4da375' }
          ]}
        />
      </div>
      <div className="match-detail-section">
        <h3>Latest Matches</h3>
        <MatchDetailCard teamName={team.teamName} match ={team.matches[0]}/>
      </div>
      
      {/* slice because we want three entries in small card. so it'll be mathes[1:4]*/}
      {team.matches.slice(1).map(match => <MatchSmallCard key={match.id} teamName = {team.teamName} match = {match} />)} 
      
      <div className="more-link">
        <Link to={`/teams/${teamName}/matches/${process.env.REACT_APP_DATA_END_YEAR}`}>More ></Link>
      </div>


    </div>
  );
}

