define(['reyode/bootstrap'], function(){
Reyode.Client = {};
Reyode.Client.ClientController = function() {
	// "extend" Reyode.Controller
	Reyode.SocketIoController(this);
	
	this.messageRemovalTimeout = false;
	
	/**
	 * Connect to the server and setup callbacks
	 */
	this.connect = function() {
		this.layout = new Reyode.Client.Layout();
		this.layout.switchTo('Connecting');

	    var client = this;
	    
	    this.socket = io.connect(Reyode.Config.socketUrl);

	    this.query('Ping');
	    this.socket.on('message', function (data) {
	    	client.dispatch(data);
	    });
	};
	
	this.setCookie =  function(c_name,value,exdays)	{
		var exdate=new Date();
		exdate.setDate(exdate.getDate() + exdays);
		var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
		c_value += '; path=/';
		document.cookie=c_name + "=" + c_value;
	};
	
	this.actionUpdateUser = function(data) {
		this.user = this.restore('Reyode.User', data.user);
		this.setCookie('login', this.user.nickname, 7);
		this.layout.switchTo('Welcome');
	};
	
	this.actionProcessQuiz = function(data) {
		this.spinner = this.restore('Reyode.QuizSpinner', data);
		this.layout.switchTo('Quiz');
        $('#quizContent').html(this.spinner.draw());
        $('#topMessage').html('');
        $('#message').html('');
        
	};
	
	this.actionGuessInfo = function(event) {
		var colorClass;
		
		console.log(event);
		
		if (event.code > 0) {
			$('#topMessage').html(event.message);
			return;
		}
		
		if (event.difference > 0) {
			colorClass = 'correct';
			$('input[value=' + event.answer + ']').css('background-color', '#9e5');
			$('#topMessage').html('wait for next quizz...');
		} else {
			var s = $('input[value=' + event.answer + ']');
			console.log(s);
			s.css('background-color', 'red');
			colorClass = 'incorrect';
		}
		
		var message = event.nickname + ' got ' + event.difference + ' (total: ' + event.points + ')';
		$('#message').html('<span class=' + colorClass + '>' + message + '</span>');
		if (this.messageRemovalTimeout != false) {
			clearTimeout(this.messageRemovalTimeout);
		}
		
		var me = this;		
		this.messageRemovalTimeout = setTimeout(
			function(){
				$('#message').html('');
				me.messageRemovalTimeout = false;
			},
			5000);
	};
	
	this.actionUserQuit = function(data) {
		$('#topMessage').html('quit: ' + data.user.nickname);
	};
	
	this.gotoSelectGame = function(data) {
		this.layout.switchTo('SelectGame');
	};
	
	this.otherName = function(direction) {
		this.query('OtherName');
	};
	
	this.startGame = function(direction) {
		this.query('SetDirection', {direction: direction});
	};
	
	this.respond = function(answerText) {
		this.query('ProcessAnswer', {answer: answerText, id: this.spinner.id});
	};
	
	this.requestQuiz = function()
	{
		this.query('Quiz', {});
	};
};

});