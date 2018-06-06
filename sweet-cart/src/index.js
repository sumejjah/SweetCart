import React from "react";
import ReactDOM from 'react-dom';
import {render} from "react-dom";
import {Router, Route, browserHistory, IndexRoute} from "react-router";
import { Link } from 'react-router';

import {Root} from "./components/Root";
import {Home} from "./components/Home";
import {User} from "./components/User";
import registerClient from "./components/RegisterClient";
import RegisterCakeShop from "./components/RegisterCakeShop";
import CreateOffer from './components/CreateOffer'
import ShowOffer from './components/ShowOffer'
import EditOffer from './components/EditOffer'
import OrderOffer from './components/OrderOffer'
import ShowRequirements from './components/ShowRequirements'
import ShowConfirmedRequirements from './components/ShowConfirmedRequirements'
import ShowUser from './components/ShowUser'
import ShowDetails from './components/ShowDetails'
import registerServiceWorker from './registerServiceWorker';
import 'bootstrap/dist/css/bootstrap.min.css';

class App extends React.Component {
    render() {
        return (
            <Router history={browserHistory}>
                <Route path={"/"} component={Root} >
                    <IndexRoute component={Home} />
                    <Route path={"user/:id"} component={User} />
                    <Route path={"home"} component={Home} />
                    <Route path={"registerClient"} component={registerClient} />
                    <Route path={"registerCakeShop"} component={RegisterCakeShop} />
                    <Route exact path="/createOffer" component={CreateOffer} />
                     <Route exact path="/showOffer" component={ShowOffer} />
                     <Route path='/editOffer/:id' component={EditOffer} />
                     <Route path='/orderOffer/:id' component={OrderOffer} />
                     <Route exact path="/showRequirements" component={ShowRequirements} />
                     <Route exact path="/showConfirmedRequirements" component={ShowConfirmedRequirements} />
                     <Route exact path="/ShowUser" component={ShowUser} />
                      <Route exact path="/ShowDetails" component={ShowDetails} />
                </Route>

                <Route path={"home-single"} component={Home}/>
            </Router>
        );
    }
}

render(<App />, window.document.getElementById('root'));
