Reyode.GameManager = function(xmlrpc) {
	this.gameSessions = {};
	this.xmlrpc = xmlrpc;
	
	this.getGameSession = function(direction) {
		if (typeof this.gameSessions[direction] == 'undefined') {
			this.gameSessions[direction] = new Reyode.GameSession(direction, xmlrpc);
		};
		return this.gameSessions[direction];
	};
};