import './App.css';
import { Route, Routes } from 'react-router-dom';
import Mainlayout from './pages/Mainlayout';
import Login from './pages/Login';
import SignUp from './pages/SignUp';
import Logout from './pages/Logout';
import Board from './pages/Board';
import NewPost from './pages/NewPost';
import DetailPost from './pages/DetailPost';
import UpdatePost from './pages/UpdatePost';

function App() {
  return (
    <Routes>
      <Route path='/' element={<Mainlayout></Mainlayout>}>
        <Route path='/login' element={<Login></Login>}></Route>
        <Route path='/sign-up' element={<SignUp></SignUp>}></Route>
        <Route path='/logout' element={<Logout></Logout>}></Route>

        <Route path='/' element={<Board></Board>}></Route>
        <Route path='/board/newpost' element={<NewPost></NewPost>}></Route>
        <Route path='/board/detail/:postid' element={<DetailPost></DetailPost>}></Route>
        <Route path='/board/update/:postid' element={<UpdatePost></UpdatePost>}></Route>
      
      </Route>
    </Routes>
  );
}

export default App;
