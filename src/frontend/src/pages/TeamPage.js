// Read about useEffect: https://blog.logrocket.com/guide-to-react-useeffect-hook/

import {React, useEffect, useState} from 'react';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';


export const TeamPage = () => {

  const [team, setTeam] = useState({matches: []}); //set an initial empty default value

  useEffect(
    () => {
      const fetchMatches = async () => {
        const response = await fetch('http://localhost:8080/team/Chennai Super Kings');
        const data = await response.json(); 
        setTeam(data);
        // console.log(team.teamName);
        // console.log(data);
      };
      fetchMatches();
    }, [] //I am saying call this only on first page load
  );


  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>
      <MatchDetailCard match ={team.matches[0]}/>
      {/* slice because we want three entries in small card. so it'll be mathes[1:4]*/}
      {team.matches.slice(1).map(match => <MatchSmallCard match = {match} />)} 
      
    </div>
  );
}

