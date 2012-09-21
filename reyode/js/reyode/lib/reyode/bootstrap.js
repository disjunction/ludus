if (typeof console == 'undefined') {
	console = {};
}

Reyode = {};
Reyode.Bootstrap = {};
Reyode.Bootstrap.run = function(callback) {
	requirejs(['reyode/config'], function(){
		requirejs(Reyode.Config.autoload, callback);
	});		
};