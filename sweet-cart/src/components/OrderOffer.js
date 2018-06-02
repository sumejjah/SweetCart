import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import axios from 'axios';
import {Router, Route, browserHistory, IndexRoute} from "react-router";

class OrderOffer extends Component {
  constructor(props){
    super(props);
    this.state={
      adress:'',
      telephone:''
    
      
    }
  }

  componentWillReceiveProps(nextProps){
    console.log("nextProps",nextProps);
  }
  handleClick(event,role){
    var apiBaseUrl = "http://localhost:8079/api/ordering/orders/add";
    var self = this;
    var offer_id=this.props.params.id;
    if(this.state.adress.length>0 && this.state.telephone.length>0 ){
      var payload={
      
      "adress": this.state.adress,
      "telephone":this.state.telephone,
      "client":{"id": 2},
      "offer":{"id": offer_id}
      
      }



      axios.post(apiBaseUrl, payload).then(function (response) {
       if(response.status == 201){
        console.log("You order was successfull");
      

        //browserHistory.push("/showOffer/")

         }
         
        
       
       else{
         console.log("some error ocurred",response.data.code);
         //browserHistory.push("/showOffer/")
       }
     })
     .catch(function (error) {
       console.log(payload);
        alert("You order was successfull");
         
              // browserHistory.push("/showOffer/")
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
        <div class="row">
         <div class="card">
        <div class="card-body">
         <div class="panel-body">
<div class="col-md-12 col-xs-12">
          <div>
           <TextField
             hintText="Enter address"
             floatingLabelText="Address"
             onChange = {(event,newValue) => this.setState({adress:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter phone"
             floatingLabelText="Phone"
             onChange = {(event,newValue) => this.setState({telephone:newValue})}
             />
           <br/>
          
           <RaisedButton label="Add" primary={true} style={style} onClick={(event) => this.handleClick(event,this.props.role)}/>
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
  margin: 15,
};

export default OrderOffer;