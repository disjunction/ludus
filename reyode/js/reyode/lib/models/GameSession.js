Reyode.GameSession = function(direction, xmlrpc) {
	this.direction = direction;
	this.totalQuestions = 0;
	this.currentQuiz = null;
	this.state = 'init';
	
	this.xmlrpc = xmlrpc;
	
	this.eventDispatcher = new Reyode.EventDispatcher();
	
	this.requestQuiz = function() {
		var me = this;
		this.xmlrpc.methodCall('Quizzer.getOne', [this.direction], function(error, value) {
			var quiz = Reyode.QuizSpinner.fromArray(value);
			me.currentQuiz = quiz;
			me.state = 'active';
			me.eventDispatcher.fire({type: 'onNewQuiz', quiz: quiz});
		});
	};
};