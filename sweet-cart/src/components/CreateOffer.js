import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import axios from 'axios';
import {Router, Route, browserHistory, IndexRoute} from "react-router";

class CreateOffer extends Component {
  constructor(props){
    super(props);
    this.state={
      name:'',
      category:'',
      price:'',
      picture:'',
      description:''
      
    }
  }

  componentWillReceiveProps(nextProps){
    console.log("nextProps",nextProps);
  }
  handleClick(event,role){
    var apiBaseUrl = "http://localhost:8079/api/catalog/offers/add";
    var self = this;
    if(this.state.name.length>0 && this.state.description.length>0 &&this.state.category.length>0 && this.state.price.length>0 && this.state.picture.length>0 ){
      var payload={
      
      "name": this.state.name,
      "avg_review":0,
      "category":this.state.category,
      "price": this.state.price,
       "description": this.state.description,
      "picture": this.state.picture,
      "cakeShopId":{"id": 1}
      
      }

axios.post(apiBaseUrl, payload).then(function (response) {
       if(response.status == 201){
        console.log("You created offer successfully");
      

        //browserHistory.push("/showOffer/")

         }
         
        
       
       else{
         console.log("some error ocurred",response.data.code);
         //browserHistory.push("/showOffer/")
       }
     })
     .catch(function (error) {
       console.log(payload);
        alert("You created offer successfully");
         
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
<div class="col-md-6 col-xs-12">
          <div>
           <TextField
             hintText="Enter name"
             floatingLabelText="Name"
             onChange = {(event,newValue) => this.setState({name:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter category"
             floatingLabelText="Category"
             onChange = {(event,newValue) => this.setState({category:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter price"
             floatingLabelText="Price"
             onChange = {(event,newValue) => this.setState({price:newValue})}
             />
           <br/>
            <TextField
             hintText="Enter description"
             floatingLabelText="Description"
             onChange = {(event,newValue) => this.setState({description:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter url of picture"
             floatingLabelText="Url picture"
             onChange = {(event,newValue) => this.setState({picture:newValue})}
             />
           <br/>
           
          
           <RaisedButton label="Add" primary={true} style={style} onClick={(event) => this.handleClick(event,this.props.role)}/>
          </div>
          </div>

          <div class="col-md-6 col-xs-12">
           <img id="foo" class="img-fluid center" src={this.state.picture} onError={(e)=>{e.target.src="https://www.logocowboy.com/wp-content/uploads/2016/06/sweet-cart.png"}}/>
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

export default CreateOffer;