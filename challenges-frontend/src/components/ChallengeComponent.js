import * as React from "react";
import ApiClient from "../services/ApiClient";

class ChallengeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            a: '',
            b: '',
            user: '',
            message:'',
            guess:''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmitResult = this.handleSubmitResult.bind(this);
    }

    //execute logic after the component is rendered for the first time
    componentDidMount() {
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
        ApiClient.sendGuess(
            this.state.user, this.state.a, this.state.b, this.state.guess)
            .then(res => {
                if(res.ok) {
                    res.json().then(json => {
                        if(json.correct) {
                            this.updateMessage("Congratulation! Your guess is correct!!!");
                        } else {
                            this.updateMessage("Ops! Your guess " + json.resultAttempt + " is wrong, but keep playing!");
                        }
                    });
                } else {
                    this.updateMessage("Error: server error or not available")
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
            <div>
                <div>
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
            </div>
        );
    }
}

export default ChallengeComponent;