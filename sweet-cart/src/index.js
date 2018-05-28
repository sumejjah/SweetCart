import React from "react";
import ReactDOM from 'react-dom';
import {render} from "react-dom";
import {Router, Route, browserHistory, IndexRoute} from "react-router";

import {Root} from "./components/Root";
import {Home} from "./components/Home";
import {User} from "./components/User";
import registerClient from "./components/RegisterClient";
import RegisterCakeShop from "./components/RegisterCakeShop";
import CreateOffer from './components/CreateOffer'

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
                </Route>
                <Route path={"home-single"} component={Home}/>
            </Router>
        );
    }
}

render(<App />, window.document.getElementById('root'));
