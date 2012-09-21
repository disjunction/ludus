define(['reyode/bootstrap'], function(){
/**
 * "abstract class", accepts the child object instance as param
 * @param me
 */
Reyode.Controller = function(child) {
	/**
	 * resolve action name by message and redirect to actionXXX method
	 */
	child.dispatch = function(message) {
		// abstraction of data for node.js websockets and browser WebSocket
		var data = typeof message.data == 'string'? message.data : message.utf8Data; 
        try {
            var json = JSON.parse(data);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message.utf8Data);
            return;
        }
        name = json.actionName;
		if (typeof this['action' + name] == 'function') {
			console.log('Controller redirects to action' + name);
			this['action' + name](json.data);
		} else {
			console.log('ERROR: cannot resolve action "' + name + '"');
		}
	};
	
	/**
	 * send message with action and data via web socket
	 * @param actionName
	 * @param data
	 */
	child.query = function (actionName, data) {
		var c = this.connection; // dumb alias
		var message = JSON.stringify( {actionName: actionName, data: data} );
	    typeof c.sendUTF == 'function'? c.sendUTF(message) : c.send(message)  
	};
	
	/**
	 * take raw json object data and convert it to className
	 * @param className
	 * @param data
	 * @returns Object
	 */
	child.restore = function (className, data) {
		obj = eval('new ' + className);
		for (var key in data) {
			eval('obj.' + key + '=data["' + key + '"]');
		}
		return obj;
	}
};

});