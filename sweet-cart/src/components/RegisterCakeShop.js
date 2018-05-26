import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import axios from 'axios';

class RegisterCakeShop extends Component {
  constructor(props){
    super(props);
    this.state={
      name:'',
      address:'',
      description: '',
      username: '',
      password:''
    }
  }
  componentWillReceiveProps(nextProps){
    console.log("nextProps",nextProps);
  }
  handleClick(event,role){
    var apiBaseUrl = "http://localhost:8079/api/identify/users/add";
    var self = this;
    if(this.state.name.length>0 && this.state.adress.length>0 && this.state.username.length>0 && this.state.password.length>0){
      var payload={
      "username": this.state.first_name,
      "password":this.state.password,
      "roleId":{"id": 2}
      }

      var payload2 = {
        "name": this.state.name,
        "address": this.state.address,
        "description": this.state.description,
        "user_id": ''
      }

      axios.post(apiBaseUrl, payload)
     .then(function (response) {
       console.log(response);
       if(response.status === 200){
        console.log("registration successfull");
        payload2.user_id = response.data.id;
       }
       else{
         console.log("some error ocurred",response.data.code);
       }
     })
     .catch(function (error) {
       console.log(error);
     });
    }
    else{
      alert("Input field value is missing");
    }

  }
  render() {
    var userhintText,userLabel;
    if(this.props.role === "student"){
      userhintText="Enter your Student Id",
      userLabel="Student Id"
    }
    else{
      userhintText="Enter your Teacher Id",
      userLabel="Teacher Id"
    }
    return (
      <div>
        <MuiThemeProvider>
          <div>
           <TextField
             hintText="Enter Cake Shop Name"
             floatingLabelText="Name"
             onChange = {(event,newValue) => this.setState({name:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter Your Adress"
             floatingLabelText="Address"
             onChange = {(event,newValue) => this.setState({address:newValue})}
             />
           <br/>
           <TextField
             hintText="Describe your shop"
             floatingLabelText="Description"
             onChange = {(event,newValue) => this.setState({description:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter Your Username"
             floatingLabelText="Username"
             onChange = {(event,newValue) => this.setState({username:newValue})}
             />
           <br/>
           <TextField
             type = "password"
             hintText="Enter your Password"
             floatingLabelText="Password"
             onChange = {(event,newValue) => this.setState({password:newValue})}
             />
           <br/>
           <RaisedButton label="Submit" primary={true} style={style} onClick={(event) => this.handleClick(event,this.props.role)}/>
          </div>
         </MuiThemeProvider>
      </div>
    );
  }
}

const style = {
  margin: 15,
};

export default RegisterCakeShop;