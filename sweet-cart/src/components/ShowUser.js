import React, { Component } from 'react';
import axios from 'axios';
import {Router, Route, browserHistory, IndexRoute} from "react-router";
import {Link} from "react-router";

<h2> HA </h2>

class ShowUser extends Component{

constructor(props){
  super(props);
  this.state={
    users:[],
    isLoaded:false,
  }
}

componentDidMount(){
  fetch('http://localhost:8079/api/identify/users/all')
  .then(res=> res.json())
  .then(json=>{
    this.setState({
      isLoaded:true,
      users:json,
    })
  });
}
delete(id){
    console.log(id);
    axios.delete('http://localhost:8079/api/identify/users/'+id)
      .then((result) => {
        alert("Poruka: Deleted!");
        browserHistory.push('/createOffer');

      });

  }

  render(){
    var{isLoaded,users}=this.state;
    if(!isLoaded){
      return <div>Loading...</div>
    }
    else{
    return (
      <div className="App">
      <ul class="list">
      {users.map(user=>(
        <li key={user.id}>
            <div class="card">
              <div class="card-body">
                 <div class="row">


                    <div class="col-md-6">
                       <h4 class="card-text">{user.id}</h4>
                       <p class="card-text">User: {user.username}</p>



                      </div>


                       <div class="col-md-2">


                       <Link to={`/showDetails/${user.id}`} class="btn btn-primary btn-primary"><span class="glyphicon glyphicon-ok"></span>Details</Link>&nbsp;

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
export default ShowUser;
