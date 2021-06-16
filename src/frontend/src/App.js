import './App.css';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import { TeamPage } from './pages/TeamPage';

function App() {
  return (
    <div className="App">
      <Router>
        {/* we'll not see anything for localhost:3000. Because that's a part of Route tag now. So, we'll see the same output for localhost:3000/teams */}
        <Route path = "/teams/:teamName"> 
          <TeamPage/>
        </Route>
      </Router>
      
    </div>
  );
}

export default App;
