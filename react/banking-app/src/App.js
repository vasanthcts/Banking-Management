import React from 'react'
import '../node_modules/bootstrap/dist/css/bootstrap.min.css'
import './App.css'
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom'

import NewBankAccount from './components/NewBankAccount'

function App() {
  return (
    <Router>
      <div className="App">
        <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link className="navbar-brand" to={'/sign-up-bank-account'}>
              Banking Management
            </Link>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link className="nav-link" to={'/transfer'}>
                    Transfer
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={'/sign-up-bank-account'}>
                    Register New Bank Account
                  </Link>
                </li>
              </ul>
            </div>
          </div>
        </nav>

        <div className="auth-wrapper">
          <div className="auth-inner">
            <Routes>
              <Route exact path="/" element={<NewBankAccount />} />
              {/* <Route path="/transfer" element={<Transfer />} /> */}
              <Route path="/sign-up-bank-account" element={<NewBankAccount />} />
            </Routes>
          </div>
        </div>
      </div>
    </Router>
  )
}

export default App