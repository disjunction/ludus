Reyode.Server = {};
Reyode.Server.ServerController = function() {
	// "extend" Reyode.Controller
	Reyode.SocketIoController(this);
	
	this.state = 'init';
	this.gameManager = null;
	this.userSession = null;
	
	this.getCookie = function(name)	{
		if (typeof(this.socket.handshake.headers.cookie) == 'undefined') {
			return null;
		}
		
		var i, x, y;
		var ARRcookies = this.socket.handshake.headers.cookie.split(";");
		
		for (i = 0; i < ARRcookies.length; i++)
		{
			x = ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
			y = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
			x = x.replace(/^\s+|\s+$/g,"");
			if (x == name) {
			    return unescape(y);
			}
		}
	};
	
	this.requestQuiz = function() {
		if (this.userSession.gameSession.currentQuiz == null) {
			this.userSession.gameSession.requestQuiz();
		} else {
			this.query('ProcessQuiz', this.userSession.gameSession.currentQuiz);
		}
	};
	
	this.actionQuiz = function(message) {
		this.requestQuiz();
	};
	
	this.onNewQuiz = function(event) {
		this.query('ProcessQuiz', event.quiz);
	};
	
	this.actionSetDirection = function(message) {
		var gameSession = this.gameManager.getGameSession(message.direction);
		this.userSession = new Reyode.UserSession(gameSession, this.user);
		
		var dispatcher = gameSession.eventDispatcher;
		dispatcher.addListener('onNewQuiz', this.onNewQuiz, this);
		dispatcher.addListener('onGuess', this.onGuess, this);
		
		this.requestQuiz();
	};
	
	this.onGuess = function(event) {
		var quiz = this.userSession.gameSession.currentQuiz;
		if (event.quizId = quiz.id) {
			this.query('GuessInfo', event);
		}
	};
	
	this.actionPing = function(message) {
		this.query('UpdateUser', {user: this.user});
	};
	
	this.actionProcessAnswer = function(message) {
		var quiz = this.userSession.gameSession.currentQuiz;
		
		if (this.userSession.gameSession.state != 'active') {
			this.query('GuessInfo',
				{type: 'onGuess',
				 code: 1,
				 quizId: quiz.id,
				 message: 'quiz is already solved'
				});
			return;
		}
		
		var difference = 1;
		if (quiz.id == message.id) {
			if (quiz.correctAnswer == message.answer) {
				this.userSession.points += 1;
				this.userSession.gameSession.state = 'wait';
				var me = this;
				setTimeout(function() {
						me.userSession.gameSession.requestQuiz();
					},
					5000);
			} else {
				var oldPoints = this.userSession.points;
				this.userSession.points = Math.floor(this.userSession.points / 2);
				difference = this.userSession.points - oldPoints;
			}
		
			this.userSession.gameSession.eventDispatcher.fire(
					{type: 'onGuess',
					 code: 0,
					 difference: difference,
					 nickname: this.userSession.user.nickname,
					 points: this.userSession.points,
					 quizId: quiz.id,
					 answer: message.answer
					});
		}
	};
	
	this.actionOtherName = function(message) {
		var nameGenerator = new Reyode.Server.NameGenerator();
		this.user.nickname = nameGenerator.generate();
		this.query('UpdateUser', {user: this.user});
	};
	
	this.userQuit = function(user) {
		this.query('UserQuit', {user: user});
	};
};
