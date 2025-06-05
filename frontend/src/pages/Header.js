import React from 'react'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'

const Header = () => {
    const login = useSelector(state=> state.board.login);
  return (
    <div>
        <h1>Board</h1>
        <Link to='/'>Board</Link>
        {!login && <>&nbsp;|&nbsp;<Link to='/login'>Login</Link>&nbsp;|&nbsp;<Link to='/sign-up'>SignUp</Link></>}
        {login && <>&nbsp;|&nbsp;<Link to='/logout'>Logout</Link></>}
    </div>
  )
}

export default Header