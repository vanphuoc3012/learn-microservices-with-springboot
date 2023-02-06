import * as React from "react";
import ApiClient from "../services/ApiClient";
import LastAttemptsComponent from "./LastAttemptsComponent"

class ChallengeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            a: '',
            b: '',
            user: '',
            message:'',
            guess:'',
            lastAttempts:[]
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitResult = this.handleSubmitResult.bind(this);
    }

    //execute logic after the component is rendered for the first time
    componentDidMount() {
        this.refreshChallenge();
    }

    refreshChallenge() {
        ApiClient.challenge().then(
            res => {
                if(res.ok) {
                    res.json().then(json => {
                        this.setState({
                            a: json.factorA,
                            b: json.factorB
                        });
                    });
                } else {
                    this.updateMessage("Error: server error or not available")
                }
            }
        )
    }
    handleChange(event) {
        const name = event.target.name;
        this.setState({
            [name]: event.target.value
        });
    }

    handleSubmitResult(event) {
        event.preventDefault();
        ApiClient.sendGuess(this.state.user, this.state.a, this.state.b, this.state.guess)
            .then(res => {
                if(res.ok) {
                    res.json().then(json => {
                        if(json.correct) {
                            this.updateMessage("Congratulation! Your guess is correct!!!");
                        } else {
                            this.updateMessage("Ops! Your guess " + json.resultAttempt + " is wrong, but keep playing!");
                        }
                    });
                    this.updateLastAttempts(this.state.user);
                    this.refreshChallenge();
                } else {
                    this.updateMessage("Error: server error or not available");
                }
            })
    }

    updateLastAttempts(userAlias: string) {
        ApiClient.getAttempts(userAlias)
            .then(res => {
                if(res.ok) {
                    let attempts: Attempt[] = [];
                    res.json().then(data => {
                        data.forEach(item => {
                            attempts.push(item);
                        })
                    });
                    this.setState({lastAttempts: attempts});
                }
            })
    }

    updateMessage(m: string) {
        this.setState({
            message: m
        });
    }

    render() {
        return (
            <div className="display-column">
                <div className="challenge">
                    <h3>Your new challenge</h3>
                    <h1>
                        {this.state.a} x {this.state.b}
                    </h1>
                </div>
                <form onSubmit={this.handleSubmitResult}>
                    <label>
                        Your Alias:
                        <input type="text" maxLength="12"
                               name="user"
                               value={this.state.user}
                                onChange={this.handleChange}/>
                    </label>
                    <br/>
                    <label>
                        Your guess:
                        <input type="number" min={0}
                        name="guess" value={this.state.guess}
                        onChange={this.handleChange}/>
                    </label>
                    <br/>
                    <input type="submit" value="Submit"/>
                </form>
                <h4>{this.state.message}</h4>
                {this.state.lastAttempts.length > 0 &&
                <LastAttemptsComponent lastAttempts={this.state.lastAttempts}/>}
            </div>
        );
    }
}

export default ChallengeComponent;