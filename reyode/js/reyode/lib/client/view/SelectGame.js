define(['client/View'], function(){
	
Reyode.Client.View.SelectGame = function(container) {
	Reyode.Client.View(this, container);
	this.mySelector = container.find('#selectGame');
	
	this.init = function() {
		var me = this;
		this.mySelector.find('#start').click(function(){
			clientController.startGame(me.mySelector.find('#selectDirection').val());
		});
	};
};

});