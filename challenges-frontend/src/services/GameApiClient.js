class GameApiClient {
    static SERVER_URL = "http://localhost:8081";
    static GET_LEADERBOARD = "/leaders";

    static leaderBoard(): Promise<Response> {
        return fetch(this.SERVER_URL + this.GET_LEADERBOARD);
    }
}
export default GameApiClient;