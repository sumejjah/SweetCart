import './ShowOffer.css';
import React, { Component } from 'react';
import axios from 'axios';
import {Router, Route, browserHistory, IndexRoute} from "react-router";
import {Link} from "react-router";


class ShowConfirmedRequirements extends Component{

constructor(props){
  super(props);
  this.state={
    requirements:[],
    isLoaded:false,
  }
}

componentDidMount(){
  fetch('http://localhost:8079/api/ordering/requirement/all')
  .then(res=> res.json())
  .then(json=>{
    this.setState({
      isLoaded:true,
      requirements:json,
    })
  });
  console.log(this.state.requirements)
  }

  


  render(){
    var{isLoaded,requirements}=this.state;
    if(!isLoaded){
      return <div>Loading...</div>
    }
    else{
    return (
      <div className="App">
      <ul class="list">
      {requirements.map(requirement=>(
        <li key={requirement.id}>
            {requirement.confirmed==true ? (
            <div class="card">
                
                <div class="card-header">
                  <div class="row">
                        <h4 class="card-title pr-2 pl-4">Client that ordered:</h4> 
                        <h4 class="card-title pr-2">{requirement.ordersId.client.firstName}</h4>
                        <h4 class="card-title pr-2">{requirement.ordersId.client.lastName}</h4>       
                  </div>
                </div>

                <div class="card-body">

                  <div class="row">
                  
                      <div class="col-md-1 ">
                        <img class="img-fluid" src={requirement.ordersId.offer.picture} alt="Chania"/>  
                      </div>
                      <div class="col-md-5">
                        <h3 class="card-title">{requirement.ordersId.offer.name}</h3>
                        <h5>In category: {requirement.ordersId.offer.category}</h5>
                      </div>
                      <div class="col-md-4">  
                        <h4>{requirement.ordersId.offer.price}$</h4>
                      </div>
                      

                  </div>    
                </div> 
            </div>) : (<p></p>)}

         
     </li>
        ))}
      </ul>
     
      </div>
      );
    }
  }
}
export default ShowConfirmedRequirements;