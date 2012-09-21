/**
 * "abstract class", accepts the child object instance as param
 * @param me
 */
Reyode.SocketIoController = function(child) {
	/**
	 * resolve action name by message and redirect to actionXXX method
	 */
	child.dispatch = function(data) {
        name = data.actionName;
		if (typeof this['action' + name] == 'function') {
			console.log('Controller redirects to action' + name);
			this['action' + name](data.data);
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
		var s = this.socket; // dumb alias
		var data = {actionName: actionName, data: data};
	    s.emit('message', data);
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