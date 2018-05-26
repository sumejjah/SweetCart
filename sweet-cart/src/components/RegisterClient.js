import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import axios from 'axios';

class RegisterClient extends Component {
  constructor(props){
    super(props);
    this.state={
      first_name:'',
      last_name:'',
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
    if(this.state.first_name.length>0 && this.state.last_name.length>0 && this.state.username.length>0 && this.state.password.length>0){
      var payload={
      "id": '',
      "username": this.state.username,
      "password":this.state.password,
      "roleId":{"id": 3}
      }

      var payload2 = {
        "id": '',
        "bonus": 0,
        "firstName": this.state.first_name,
        "lastName": this.state.last_name,        
        "userId": {"id": ''}
      }

      axios.post(apiBaseUrl, payload).then(function (response) {
       if(response.status == 201){
        console.log("user registration successfull");

        axios.get("http://localhost:8079/api/identify/users/name/" + payload.username).then(res => {
        if(res.status == 200){
          payload2.userId.id = res.data.id;
          axios.post("http://localhost:8079/api/identify/clients/add", payload2)
               .then(function (response) {
                 if(response.status == 201){
                  console.log("cake shop registration successfull");
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
           console.log("Username does not exists");
         }
        }, err => {
          alert("Server rejected response with: " + err);
        });
        
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
    return (
      <div>
        <MuiThemeProvider>
          <div>
           <TextField
             hintText="Enter Your First Name"
             floatingLabelText="First Name"
             onChange = {(event,newValue) => this.setState({first_name:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter Your Last Name"
             floatingLabelText="Last Name"
             onChange = {(event,newValue) => this.setState({last_name:newValue})}
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

export default RegisterClient;