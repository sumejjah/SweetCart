import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import NewUser from './NewUser';
import axios from 'axios'

class Main extends Component {

  constructor(props) {
    super(props);
    this.state = {
      persons: []}

    this.ping = this.ping.bind(this);
  }

  ping() {
    axios.get("http://localhost:8079/api/identify/users/all").then(res => {
      alert("Received Successful response from server!");
      const persons = res.data;
      this.setState({ persons })
    }, err => {
      alert("Server rejected response with: " + err);
    });
  }

  render() {
    return (
      <div className="Main">
        <header className="App-header">
          <h1 className="App-title">Sweet cart</h1>
        </header>
        
        <p className="App-intro">
          <div>
            <button onClick={this.ping}>Get users!</button>
          </div>
        </p>
      <ul className="container">
      
        { this.state.persons.map(person => <table className="table table-striped"><tr>
                                                    <td>{person.username}</td>
                                                    <td>{person.password}</td>
                                                    <td>{person.roleId.type}</td>
                                                  </tr></table>)}
      </ul>
      </div>
    );
  }
}

export default Main;