import './ShowOffer.css';
import React, { Component } from 'react';
import axios from 'axios';
import {Router, Route, browserHistory, IndexRoute} from "react-router";


class ShowOffer extends Component{

constructor(props){
  super(props);
  this.state={
    offers:[],
    isLoaded:false,
  }
}

componentDidMount(){
  fetch('http://localhost:8079/api/catalog/offers/all')
  .then(res=> res.json())
  .then(json=>{
    this.setState({
      isLoaded:true,
      offers:json,
    })
  });
}
delete(id){
    console.log(id);
    axios.delete('http://localhost:8079/api/catalog/offers/'+id)
      .then((result) => {
        alert("Poruka: Deleted!");
        browserHistory.push('/createOffer');
        
      });

  }

  render(){
    var{isLoaded,offers}=this.state;
    if(!isLoaded){
      return <div>Loading...</div>;
    }
    else{
    return (
      <div className="App">
      <ul>
      {offers.map(offer=>(
        <li key={offer.id}>
            <div class="card">
              <div class="card-body">
                 <div class="row">
                    <div class="col-md-2">
                       <img class="img-fluid" src={offer.picture} alt="Chania"/>
                      
                   </div>
                 
                     <div class="col-md-6">
                       <h4 class="card-title">{offer.name}</h4>
                       <p class="card-text">Price: {offer.price}km</p>
                       <a href="#" class="card-link">Category: {offer.category}</a>
                       <a href="#" class="card-link">Edit</a>
                      </div>
                     <div class="col-md-2">
                         <div class="progress-circle p10">
                         <span>{offer.avg_review}</span>
                             <div class="left-half-clipper">
                                 <div class="first50-bar"></div>
                                  <div class="value-bar"></div>
                                 
                             </div>
                          </div>
                      </div>
                       <div class="col-md-2">
<button onClick={this.delete.bind(this, offer.id)} className="btn btn-danger">Delete</button>
</div>
                  </div>
                </div>
            </div>
 

      </li>
        ))};
      </ul>
      </div>
      );
    }
  }
}
export default ShowOffer;