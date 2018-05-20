import React, { Component } from 'react';
import logo from '../assets/logo.svg';
import '../css/App.css';
import axios from 'axios'

class NewUser extends Component {

  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      roleId: '2'}
  }

  handleChangeName = event => {
    this.setState({ username: event.target.value });
  }

  handleChangePassword = event => {
    this.setState({ password: event.target.value });
  }

  handleSubmit = event => {
  const user = {
    username: this.state.name,
    password: this.state.password,
    roleId: this.state.roleId
  };

    axios.post("http://localhost:8079/api/identify/users/add", {user}).then(res => {
      alert("Received Successful response from server!");
      console.log(res);
      console.log(res.data)
    }, err => {
      alert("Server rejected response with: " + err);
    });
  }


render() {
    return (
      <div>
        <form onSubmit={this.handleSubmit}>
          <label>
            Person Name:
            <input type="text" name="name" onChange={this.handleChangeName} />
          </label>
          <label>
            Password:
            <input type="text" name="name" onChange={this.handleChangePassword} />
          </label>
          <button type="submit">Add</button>
        </form>
      </div>
    )
  }
}

export default NewUser;