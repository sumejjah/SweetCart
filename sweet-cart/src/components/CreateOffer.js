import React, { Component } from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import AppBar from 'material-ui/AppBar';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import axios from 'axios';

class CreateOffer extends Component {
  constructor(props){
    super(props);
    this.state={
      name:'',
      category:'',
      price:'',
      picture:''
      
    }
  }
  componentWillReceiveProps(nextProps){
    console.log("nextProps",nextProps);
  }
  handleClick(event,role){
    var apiBaseUrl = "http://localhost:8079/api/catalog/offers/add";
    var self = this;
    if(this.state.name.length>0 && this.state.category.length>0 && this.state.price.length>0 && this.state.picture.length>0 ){
      var payload={
      "id": '',
      "name": this.state.name,
      "avg_review":0,
      "category":this.state.category,
      "price": this.state.price,
      "picture": this.state.picture,
      "cakeShopId":{"id": 1}
      
      }


      axios.post(apiBaseUrl, payload).then(function (response) {
       if(response.status == 201){
        console.log("offer added successfull");

        

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
             hintText="Enter Cakes category"
             floatingLabelText="Cake Name"
             onChange = {(event,newValue) => this.setState({name:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter Cakes category"
             floatingLabelText="Cake category"
             onChange = {(event,newValue) => this.setState({category:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter Cakes price"
             floatingLabelText="Cake price"
             onChange = {(event,newValue) => this.setState({price:newValue})}
             />
           <br/>
           <TextField
             hintText="Enter Cakes picture"
             floatingLabelText="Cake picture"
             onChange = {(event,newValue) => this.setState({picture:newValue})}
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

export default CreateOffer;