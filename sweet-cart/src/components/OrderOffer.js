import React, { Component } from 'react';
import './ShowOffer.css';
import axios from 'axios';
import { Link } from 'react-router';
import {Router, Route, browserHistory, IndexRoute} from "react-router";

class EditOffer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      offer: {}
    };
  }

  componentDidMount() {
    axios.get('http://localhost:8079/api/catalog/offers/'+this.props.params.id)
      .then(res => {
        this.setState({ offer: res.data });
        console.log(this.state.offer);
      });
  }

  onChange = (e) => {
    
  }

  onSubmit = (e) => {
   
  }

  render() {
    return (







      <div class="card">
        <div class="card-body">
           <h4><Link to={`/showOffer`}><span class="glyphicon glyphicon-arrow-left"aria-hidden="true"></span>Back</Link></h4>
            <img class="img-fluid center" src={this.state.offer.picture} alt="Chania"/>
          </div>
          <div class="panel-body">
          
            
            <form onSubmit={this.onSubmit}>
              <div class="form-group">

<h4 class="card-title">{this.state.offer.name}</h4>
                       <p class="card-text">Price: {this.state.offer.price}km</p>
                       

                <label for="address">Address:</label>
                <input type="text" class="form-control" address="address" onChange = {(event,newValue) => this.setState({adress:newValue})} placeholder="Address" />
                 
                <label for="telephone">Phone:</label>
                <input type="text" class="form-control" telephone="telephone" onChange = {(event,newValue) => this.setState({telephone:newValue})} placeholder="Phone" />
                
               
              </div>
             
              <button type="submit" class="btn btn-success">Order</button>
            </form>
          
       </div>
      </div>
    
    );
  }
}

export default EditOffer;