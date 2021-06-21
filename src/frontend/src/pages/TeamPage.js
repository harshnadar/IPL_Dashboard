// Read about useEffect: https://blog.logrocket.com/guide-to-react-useeffect-hook/

import {React, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';


export const TeamPage = () => {

  const [team, setTeam] = useState({matches: []}); //set an initial empty default value
  const {teamName} = useParams();

  useEffect(
    () => {
      const fetchMatches = async () => {
        const response = await fetch(`http://localhost:8080/team/${teamName}`);
        const data = await response.json(); 
        setTeam(data);
        // console.log(team.teamName);
        // console.log(data);
      };
      fetchMatches();
    }, [teamName] //I am saying call this when teamName changes
  );

  if(!team || !team.teamName){
    return <h1>Team Not Found</h1>;
  }

  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>
      <MatchDetailCard match ={team.matches[0]}/>
      {/* slice because we want three entries in small card. so it'll be mathes[1:4]*/}
      {team.matches.slice(1).map(match => <MatchSmallCard teamName = {team.teamName} match = {match} />)} 
      
    </div>
  );
}

