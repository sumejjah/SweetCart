import React, {Component} from "react";
import { browserHistory } from 'react-router';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import axios from 'axios';

var apiBaseUrl = "http://localhost:8079/api/identify/users/2";


export class Home extends Component {

constructor(props){
  super(props);
  this.state={
  username:'',
  password:''
  }

  this.handleClick = this.handleClick.bind(this);
  this.registerCakeShop = this.registerCakeShop.bind(this);
  this.registerClient = this.registerClient.bind(this);
 }

 registerCakeShop(event){
    browserHistory.replace({
        pathname: '/registerCakeShop',
        state: {}
      });
  }

  registerClient(event){
    browserHistory.replace({
        pathname: '/registerClient',
        state: {}
      });
  }

 handleClick(event){
    var self = this;
    var payload={
        "userid":this.state.username
    }


    axios.get("http://localhost:8079/api/identify/users/name/" + this.state.username + "/" + this.state.password).then(res => {
      if(res.status == 200){
       console.log("Login successfull");

       localStorage.setItem("token", res.data.roleId.type);
       var newUser = {
        "userRole": res.data.roleId.type,
        "username": res.data.username,
        "userId": res.data.id
       }
       
       browserHistory.replace({
        pathname: '/user/' + res.data.id,
        state: {}
      });
     }
     else{
       console.log("Username does not exists");
       alert("Username or password doesn't exist");
     }
    }, err => {
      alert("Server rejected response with: " + err);
    });

        
  }

render() {
    return (
      <div>
        <MuiThemeProvider>

        <div className="row">
        <div className="col-md-4 col-xs-12"></div>
        <div className="col-md-5 col-xs-12">
         <div className="card">

        <div className="card-body">
         <div className="panel-body">
        
          <div>
          <TextField
             hintText="Enter your Username"
             floatingLabelText="Username"
             onChange = {(event,newValue) => this.setState({username:newValue})}
             />
           <br/>
           <TextField
               type="password"
               hintText="Enter your Password"
               floatingLabelText="Password"
               onChange = {(event,newValue) => this.setState({password:newValue})}
               />
             <br/>
          
            <RaisedButton label="Submit" primary={true} style={style} onClick={(event) => this.handleClick(event)}/>
              <br/>

            <div className="container">
              <div className="row">
                <RaisedButton label="Cake Shop" primary={true} style={style} onClick={(event) => this.registerCakeShop(event)}/>
                <br/>
                
                <RaisedButton label="Client" primary={true} style={style} onClick={(event) => this.registerClient(event)}/>

              </div>
            </div>
            
          </div>
          </div>

          
          </div>
         </div>
         </div>
         </div>
         </MuiThemeProvider>

      </div>
    );
  }
}
const style = {
 margin: 15
};
export default Home;