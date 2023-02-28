class ChallengeApiClient {
    static SERVER_URL = 'http://localhost:8000';
    static GET_CHALLENGE = '/challenges/random';
    static POST_RESULT = '/attempts';
    static GET_ATTEMPTS_BY_ALIAS = '/attempts?alias='
    static GET_USER_BY_IDS = '/users'

    static challenge(): Promise<Response> {
        return fetch(ChallengeApiClient.SERVER_URL + ChallengeApiClient.GET_CHALLENGE);
    }

    static sendGuess(user: string,
                      a: number,
                      b: number,
                      guess: number): Promise<Response> {
        return fetch(ChallengeApiClient.SERVER_URL + ChallengeApiClient.POST_RESULT,
            {
                method: 'POST',
                headers: {
                'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userAlias: user,
                    factorA: a,
                    factorB: b,
                    guess: guess
                })
        });
    }

    static getAttempts(userAlias: string): Promise<Response> {
        return fetch(ChallengeApiClient.SERVER_URL + ChallengeApiClient.GET_ATTEMPTS_BY_ALIAS + userAlias);
    }

    static getUsers(userIds: number[]) {
        return fetch(ChallengeApiClient.SERVER_URL + ChallengeApiClient.GET_USER_BY_IDS + '/' + userIds.join(','));
    }
}

export default ChallengeApiClient;