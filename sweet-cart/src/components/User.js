import React from "react";
import { browserHistory } from "react-router";
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import axios from 'axios';


export class User extends React.Component {

    constructor(props){
      super(props);
      this.state={
      first_name:'',
      last_name:'',
      username: '',
      password:'',
      isLoaded: false
      }

      this.updateCakeShop = this.updateCakeShop.bind(this);
      this.updateClient = this.updateClient.bind(this);
      this.logout = this.logout.bind(this);
     }

    componentDidMount(){
        fetch('http://localhost:8079/api/identify/users/' + this.props.params.id)
          .then(res=> res.json())
          .then(json=>{
            this.setState({
                first_name: json.firstName,
                last_name: json.lastName,
                username: json.username,
                password: json.password, 
                isLoaded: true
            })
          });
    }
    onNavigateHome() {
        browserHistory.push("/home");
    }

    logout(event){
        localStorage.clear();
        browserHistory.push("/home");
    }

    updateCakeShop(event){
        var payload = {
            firstName: this.state.first_name,
            lastName: this.state.last_name,
            username: this.state.username,
            password: this.state.password,
            bonus: 0,
            userId: this.props.params.id
        }
    
    axios.put("http://localhost:8079/api/identify/users/" + this.props.params.id, payload).then(res => {
      if(res.status == 200){
       console.log("Update successfull");
       
     }
     else{
       alert("Error");
     }
    }, err => {
      alert("Server rejected response with: " + err);
    });

        
    }

    updateClient(event){
    
    axios.put("http://localhost:8079/api/identify/users/" + this.props.params.id, this.state).then(res => {
      if(res.status == 200){
       console.log("Update successfull");
       
     }
     else{
       alert("Error");
     }
    }, err => {
      alert("Server rejected response with: " + err);
    });

        
    }

    render() {
        var{isLoaded}=this.state;
    if(!isLoaded){
      return <div>Loading...</div>
    }
    else{
        return (
        <div>
        <MuiThemeProvider>
        <div class="row">
         <div class="card">
        <div class="card-body">
         <div class="panel-body">
        <div class="col-md-6 col-xs-12">
          <div>
           
           <TextField
             hintText={this.state.username}
             floatingLabelText={this.state.last_name}
             onChange = {(event,newValue) => this.setState({username:newValue})}
             />
           <br/>
            <TextField
             hintText={this.state.password}
             floatingLabelText={this.state.last_name}
             onChange = {(event,newValue) => this.setState({password:newValue})}
             />
           <br/>           
          
          
            <RaisedButton label="Update" primary={true} onClick={(event) => this.updateClient(event)}/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>
            <br/>   
            <RaisedButton label="LOGOUT" primary={true} onClick={(event) => this.logout(event)}/>
          
          </div>
          </div>

          <div class="col-md-6 col-xs-12">
           <img id="foo" class="img-fluid center" src="https://www.logocowboy.com/wp-content/uploads/2016/06/sweet-cart.png" onError={(e)=>{e.target.src="https://www.logocowboy.com/wp-content/uploads/2016/06/sweet-cart.png"}}/>
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
}