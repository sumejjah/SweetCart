import React from "react";
import {Link} from "react-router";

export const Header = (props) => {
    return (
       <nav className="navbar navbar-expand-lg navbar-light bg-light">

        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav mr-auto">
            <li className="nav-item active">
              <a className="nav-link" href="#"><Link to={"/home"} activeStyle={{color: "red"}}>Home</Link> <span className="sr-only">(current)</span></a>
            </li>

            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/user/10"} activeClassName={"active"}>User</Link></a>
            </li>

            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/registerClient"} activeClasssName={"active"}>RegisterClient</Link></a>
            </li>

            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/createOffer"} activeClassName={"active"}>Create Offer</Link></a>
            </li>

            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/showOffer"} activeClassName={"active"}>Show Offer</Link></a>
            </li>

            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/showRequirements"} activeClassName={"active"}>Show Requirements</Link></a>
            </li>

            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/showConfirmedRequirements"} activeClassName={"active"}>Show Confirmed Requirements</Link></a>
            </li>
            <li className="nav-item">
              <a className="nav-link" href="#"><Link to={"/showUser"} activeClassName={"active"}>Show User</Link></a>
            </li>
          </ul>
          <form className="form-inline my-2 my-lg-0">
            <input className="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" />
            <button className="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
          </form>
        </div>
      </nav>
    );
};
