define(['reyode/bootstrap', 'client/ClientController'], function(){
	
Reyode.Client.View = function(me, container) {
	me.initialized = false;
	me.topContainer = container;
	me.hide = function() {
		 this.mySelector.hide(); 
	};
	me.show = function() {
		if (!this.initialized) {
			this.init();
		}
		if (typeof this.onShow != 'undefined') {
			this.onShow();
		}
		this.mySelector.show(); 
	};
	me.init = function() {}; // dummy abstract init
};

});