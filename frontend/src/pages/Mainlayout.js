import React from 'react'
import Header from './Header'
import { Outlet } from 'react-router-dom'

const Mainlayout = () => {
  return (
    <div>
        <Header></Header>
        <hr></hr>
        <Outlet></Outlet>
    </div>
  )
}

export default Mainlayout