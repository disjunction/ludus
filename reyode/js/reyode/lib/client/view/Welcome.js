define(['client/View'], function(){
	
Reyode.Client.View.Welcome = function(container) {
	Reyode.Client.View(this, container);
	this.mySelector = container.find('#welcome');
	
	this.init = function() {
		this.mySelector.find('#next').click(function(){
			clientController.gotoSelectGame();
		});
		this.mySelector.find('#otherName').click(function(){
			clientController.otherName();
		});
		this.initialized = true;
	};
	
	this.onShow = function() {
		this.mySelector.find('#nickname').text(clientController.user.nickname);
	};
};

});