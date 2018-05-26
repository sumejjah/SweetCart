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
  handleClick(event){
    var apiBaseUrl = "http://localhost:8079/api/identify/users/add";
    var self = this;
    //if(this.state.name.length>0 && this.state.adress.length>0 && this.state.username.length>0 && this.state.password.length>0){
      var payload={
      "username": this.state.username,
      "password":this.state.password,
      "roleId":{"id": 2}
      }

      var payload2 = {
        "id": '',
        "name": this.state.name,
        "address": this.state.address,
        "description": this.state.description,
        "userId": {"id": ''}
      }

      axios.post(apiBaseUrl, payload)
     .then(function (response) {
       if(response.status == 201){
        console.log("user registration successfull");
        
        axios.get("http://localhost:8079/api/identify/users/name/" + payload.username).then(res => {
        if(res.status == 200){
          payload2.userId.id = res.data.id;

           axios.post("http://localhost:8079/api/identify/cakeshops/add", payload2)
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
    //}
    //else{
      //alert("Input field value is missing");
    //}

  }
  render() {
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
           <RaisedButton label="Submit" primary={true} style={style} onClick={(event) => this.handleClick(event)}/>
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