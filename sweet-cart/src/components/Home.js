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
  this.register = this.register.bind(this);
 }

 register(event){
    browserHistory.replace({
        pathname: '/register',
        state: {}
      });
  }

 handleClick(event){
    var self = this;
    var payload={
      	"userid":this.state.username
    }


    axios.get("http://localhost:8079/api/identify/users/name/" + this.state.username).then(res => {
      console.log(res);
      if(res.status == 200){
       console.log("Login successfull");
       localStorage.setItem("token", res.data.roleId.type);
        localStorage.setItem("username", res.data.username);
        localStorage.setItem("userId", res.data.id);
       browserHistory.replace({
        pathname: '/user/:' + res.data.id,
        state: {}
    	});
     }
     else{
       console.log("Username does not exists");
       alert("Username does not exist");
     }
    }, err => {
      alert("Server rejected response with: " + err);
    });

        
  }

render() {
    return (
      <div>
        <MuiThemeProvider>
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
             <RaisedButton label="Register" primary={true} style={style} onClick={(event) => this.register(event)}/>
         
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