import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import axios from 'axios';
import { Link } from 'react-router';
import {Router, Route, browserHistory, IndexRoute} from "react-router";
import './ShowOffer.css';

class OrderOffer extends Component {
  constructor(props){
    super(props);
    this.state={
      order:{adress:'',
      telephone:''},
      offer:{name:''}
   
      
    }
  }
 componentDidMount() {
  var offer_id=this.props.params.id;
    axios.get('http://localhost:8079/api/ordering/offer/'+offer_id)
      .then(res => {
        this.setState({ offer: res.data });
        console.log(this.state.offer);
      });
  }
  componentWillReceiveProps(nextProps){
    console.log("nextProps",nextProps);
  }
  handleClick(event,role){
    var apiBaseUrl = "http://localhost:8079/api/ordering/orders/add";
    var self = this;
    var offer_id=this.props.params.id;
    var client_id=localStorage.getItem('userId')
    if(this.state.adress.length>0 && this.state.telephone.length>0 ){
      var payload={
      "id":'',
      "adress": this.state.adress,
      "telephone":this.state.telephone,
      "client":{"id": 6},
      "offer":{"id": offer_id}
      
      }
     var payload2={
 "id":'',
      "confirmed":0,
      "ordersId":{"id":''}
     }


       axios.post(apiBaseUrl, payload).then(function (response) {
       if(response.status == 201){
        console.log("Your order was successfull");


        axios.get("http://localhost:8079/api/ordering/orders/adress/" + payload.adress).then(res => {
        if(res.status == 200){
          payload2.ordersId.id = res.data.id;
          axios.post("http://localhost:8079/api/ordering/requirement/add", payload2)
               .then(function (response) {
                 if(response.status == 201){
                  console.log("You requirement was successfull");
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
           console.log("Adress doesn't exit does not exists");
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
          <div class="row">
           <div class="card bg-light">
           <div class="card-body">
            <h4><Link to={`/showOffer`}><span class="glyphicon glyphicon-arrow-left"aria-hidden="true"></span>Back</Link></h4>
           
             <div class="panel-body">

              <div class="col-md-6 col-xs-12 ">
               
                  <img class="img-fluid center" src={this.state.offer.picture} onError={(e)=>{e.target.src="https://www.logocowboy.com/wp-content/uploads/2016/06/sweet-cart.png"}} alt="Chania"/>
            
                  
                </div>
         
              
             <div class="col-md-6 col-xs-12">
               <h3 class="card-title">You ordered:</h3>
                     
                       <h4 class="card-title">{this.state.offer.name}</h4>
                       <p class="card-text">Price: {this.state.offer.price}km</p>
                        <p class="card-text">Category: {this.state.offer.category}</p>
                         <p class="card-text">Description: {this.state.offer.description}</p>
                        
                      
                 
               <span id="adres" class="glyphicon glyphicon-map-marker" ></span>
               <TextField
               hintText="Enter address"
              floatingLabelText="Address"
              onChange = {(event,newValue) => this.setState({adress:newValue})}
              />
             <br/>
             <span id="adres" class="glyphicon glyphicon-phone" ></span>
             <TextField
             hintText="Enter phone"
             floatingLabelText="Phone"
             onChange = {(event,newValue) => this.setState({telephone:newValue})}
             />
            <br/>
 
          <div id="buton">
              <RaisedButton label="Order" primary={true} style={style} onClick={(event) => this.handleClick(event,this.props.role)}/>
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