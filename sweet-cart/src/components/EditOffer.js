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
    const state = this.state.offer
    state[e.target.name] = e.target.value;
     state[e.target.category] = e.target.value;
      state[e.target.price] = e.target.value;
       state[e.target.picture] = e.target.value;
    this.setState({offer:state});
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { name,category, price, picture} = this.state.offer;

    axios.put('http://localhost:8079/api/catalog/offers/'+this.props.params.id, { name,category, price, picture}  )
      .then((result) => {
        browserHistory.push("/showOffer/")
      });
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
                <label for="name">Name:</label>
                <input type="text" class="form-control" name="name" value={this.state.offer.name} onChange={this.onChange} placeholder="Name" />
                 
                <label for="category">Category:</label>
                <input type="text" class="form-control" category="category" value={this.state.offer.category} onChange={this.onChange} placeholder="Category" />
                
                 <label for="price">Price:</label>
                <input type="text" class="form-control" price="price" value={this.state.offer.price} onChange={this.onChange} placeholder="Price" />
                
                 <label for="picture">Picture:</label>
                <input type="text" class="form-control" picture="picture" value={this.state.offer.picture} onChange={this.onChange} placeholder="Picture" />
              </div>
             
              <button type="submit" class="btn btn-success">Update</button>
            </form>
          
       </div>
      </div>
    
    );
  }
}

export default EditOffer;