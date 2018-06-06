import React, { Component } from 'react';
import './ShowOffer.css';
import axios from 'axios';
import { Link } from 'react-router';
import {Router, Route, browserHistory, IndexRoute} from "react-router";

class ShowDetails extends Component {

  constructor(props) {
    super(props);
    this.state = {
      user: {id:"", roleId:""},
      cakeShops: [],
      clients: []
    };
  }

  componentDidMount() {
    axios.get('http://localhost:8079/api/identify/users/'+this.props.params.id)
      .then(res => {
        this.setState({ user: res.data });
        console.log(this.state.user.roleId.id);
      });

    fetch('http://localhost:8079/api/identify/cakeshops/all')
    .then(res=> res.json())
    .then(json=>{
      this.setState({
        isLoaded:true,
        cakeShops:json,
      })
    });
     fetch('http://localhost:8079/api/identify/clients/all')
    .then(res=> res.json())
    .then(json=>{
      this.setState({
        isLoaded:true,
        clients:json,
      })
    });
    

  }

  

 render(){

    var{isLoaded,user}=this.state;
    var{isLoaded,cakeShops}=this.state;
    var{isLoaded,clients}=this.state;
    
    if(!isLoaded){
      return <div>Loading...</div>
    }
    else{
    return (
      <div className="App">
      <ul class="list">
      {this.state.user.roleId.id == 2 ? (
        cakeShops.map(cakeShop=>(
          
          cakeShop.userId.id == user.id ? (

          <li key={cakeShop.id}>
              <div class="card">
                <div class="card-body">
                
                        
                   
                   <div class="row">
                     <div class="col-md-3">
                        <img class="img-fluid" src="https://radov39.ru/wp-content/uploads/2015/05/blyudo-steklyannoe-the-cake-shop-32sm_3.jpg" alt="image"/>  
                     </div>
                     <div class="col-md-2">
                     </div>
                     <div class="col-md-7 ">
                        <div class="pt-5">
                        <h1 class="card-title">{cakeShop.name}</h1>
                        <h3 class="card-text">Cake shop</h3>
                        </div>
                     </div>
                   </div>


                   <div class="row">
                      <div class="col-md-7 center">
                      <h3 class="card-text center">{cakeShop.description}</h3>
                      </div>
                   </div>
                   <div class="row pt-5">
                      <h4 class="card-text center">Our address: {cakeShop.address}</h4>
                   </div>

                   
                  </div>
              </div>
          </li> ) : (<p></p>)
          )) ) : (

            clients.map(client=>(

            user.id == client.userId.id ? (
            <li key={client.id}>
                <div class="card">
                  <div class="card-body">
                     
                      <div class="row">
                         <div class="col-md-3">
                            <img class="img-fluid" src="http://abundance.org.au/wp-content/uploads/2015/09/Client.jpg" alt="image"/>  
                         </div>
                         <div class="col-md-2">
                         </div>
                         <div class="col-md-7 ">
                          <div class="pt-5">
                          
                           <h2 class="card-text">Name of client: {client.firstName} {client.lastName}</h2>
                       
                           <h3 class="card-text">With bonus: {client.bonus}</h3>
                          </div>
                        </div>
                      </div>
                      
                      
                    </div>
                </div>
            </li> ) : (<p></p>)
            )) ) }

      </ul>
      </div>
      );
    }
  }
}

export default ShowDetails;