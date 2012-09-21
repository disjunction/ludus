define(['client/ClientController'], function(){
Reyode.Client.Layout = function(){
	this.screens = {};
	this.topContainer = $('#topContainer');
	this.state = '';
	
	this.switchTo = function(screenName) {
		if (this.state == screenName) {
			this.screens[screenName].show();
			return;
		}
		if (typeof this.screens[screenName] == 'undefined') {
			this.screens[screenName] = eval('new Reyode.Client.View.' + screenName + '(this.topContainer)');
		}
		for (var name in this.screens) {
			if (name != screenName) {
				this.screens[name].hide();
			}
		}
		this.screens[screenName].show();
		this.state = screenName;
	};
};

});