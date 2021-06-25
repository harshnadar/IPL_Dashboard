import './App.scss';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import { TeamPage } from './pages/TeamPage';
import { MatchPage } from './pages/MatchPage';
import { HomePage } from './pages/HomePage';

function App() {
  return (
    <div className="App">
      <Router>
        <Switch> 
          {/* Switch matches the first thing that matches... So, put the substring at the last. */}
          <Route path = "/teams/:teamName/matches/:year"> 
            <MatchPage/>
          </Route>
          {/* we'll not see anything for localhost:3000. Because that's a part of Route tag now. So, we'll see the same output for localhost:3000/teams/ */}
          <Route path = "/teams/:teamName"> 
            <TeamPage/>
          </Route>
          <Route path="/">
            <HomePage />
          </Route>
        </Switch>
      </Router>
      
    </div>
  );
}

export default App;
