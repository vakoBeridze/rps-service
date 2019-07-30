# Rock Paper Scissors api service

### Game Description

Server 'AI' is using Markov Chain to predict human's next move.

#### Game process:
- user starts new game with preselected config:
    - movesToRemember: on how much moves calculate probabilities
    - adaptChangesPercentage: used for changing game levels, value can be between 0 - 1.
- based on previous user selection bot generates new prediction and tries to beat user.
- user selects one from ['Rock', 'Paper', 'Scissors']
- bot saves user selection for feature predictions
