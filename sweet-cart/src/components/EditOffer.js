import React, { Component } from 'react';
import './ShowOffer.css';
import axios from 'axios';
import { Link } from 'react-router';
import {Router, Route, browserHistory, IndexRoute} from "react-router";

class EditOffer extends Component {

  constructor(props) {
    super(props);
    this.state = {
      offer: {name:"", price:"",category:""}
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
   
     state[e.target.id] = e.target.value;
 
    this.setState({offer:state});
  }

  onSubmit = (e) => {
    e.preventDefault();

    const { name,category, price, picture,avg_review,cakeShopId} = this.state.offer;

    axios.put('http://localhost:8079/api/catalog/offers/'+this.props.params.id, { name,category, price, picture,avg_review,cakeShopId}  )
      .then((result) => {
        browserHistory.push("/showOffer/")
      });
  }

  render() {
    return (







      <div class="card">
        <div class="card-body">
           <h4><Link to={`/showOffer`}><span class="glyphicon glyphicon-arrow-left"aria-hidden="true"></span>Back</Link></h4>
            <img class="img-top center" src={this.state.offer.picture} onError={(e)=>{e.target.src="https://www.logocowboy.com/wp-content/uploads/2016/06/sweet-cart.png"}} alt="Chania"/>
          </div>
          <div class="panel-body">
          
            
            <form onSubmit={this.onSubmit}>
              <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" class="form-control" name="name" value={this.state.offer.name} onChange={this.onChange} placeholder="Name" ></input>
                 
                <label for="category">Category:</label>
                <input type="text" id="category" class="form-control" category="category" value={this.state.offer.category} onChange={this.onChange.bind(this)} placeholder="Category" ></input>
                
                 <label for="price">Price:</label>
                <input type="text" id="price" class="form-control" price="price" value={this.state.offer.price} onChange={this.onChange} placeholder="Price" ></input>
                
                 <label for="picture">Picture:</label>
                <input type="text" id="picture" class="form-control" picture="picture" value={this.state.offer.picture} onChange={this.onChange} placeholder="Picture" ></input>

               
               
              </div>
             
              <button type="submit" class="btn btn-success">Update</button>
            </form>
          
       </div>
      </div>
    
    );
  }
}

export default EditOffer;