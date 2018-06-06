import React from "react";
import {Link} from "react-router";

class Header extends React.Component{

  render(){
    return (
       <nav className="navbar navbar-expand-lg navbar-light bg-light">
        
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
            {localStorage.getItem('token') === null && 
              <a className="nav-link" href="#"><Link to={"/home"} activeClassName={"active"}>Login</Link> <span className="sr-only">(current)</span></a>}
            </li>

            <li className="nav-item">
            {localStorage.getItem('token') !== null && 
              <a className="nav-link" href="#"><Link to={"/user/" + localStorage.getItem('userId')} activeClassName={"active"}>My profile</Link></a>}
            </li>
            
            <li className="nav-item">
              {localStorage.getItem('token') === 'cake_shop' && 
              <a className="nav-link" href="#"><Link to={"/createOffer"} activeClassName={"active"}>Create Offer</Link></a>}
            </li>
            
            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/showOffer"} activeClassName={"active"}>Show Offer</Link></a>
            </li>
            
            <li className="nav-item">
              {localStorage.getItem('token') === 'cake_shop' && 
              <a className="nav-link" href="#"><Link to={"/showRequirements"} activeClassName={"active"}>Show Requirements</Link></a>}
            </li>

            <li className="nav-item">
             {localStorage.getItem('token') === 'client' && 
              <a className="nav-link" href="#"><Link to={"/showConfirmedRequirements"} activeClassName={"active"}>Show Confirmed Requirements</Link></a>}
            </li>

            <li className="nav-item">
            {localStorage.getItem('token') === 'admin' && 
              <a className="nav-link" href="#"><Link to={"/showUser"} activeClassName={"active"}>Show User</Link></a>}
            </li>

          </ul>

          <form className="form-inline my-2 my-lg-0">
              <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
              <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
          
        </div>
      </nav>
    );
  }
}

export default Header;