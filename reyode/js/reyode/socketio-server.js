// http://ejohn.org/blog/ecmascript-5-strict-mode-json-and-more/
"use strict";

// Optional. You will see this name in eg. 'ps' or 'top' command
process.title = 'reyode';

// bootstrap and load additional server libs
var requirejs = require('requirejs');
requirejs.config({
	baseUrl: './lib/',
    nodeRequire: require
});
requirejs(
	['reyode/bootstrap',
	 'server/ServerController',
	 'server/GameManager',
	 'server/NameGenerator'], 
	function() {
	   Reyode.Bootstrap.run(function(){ main(); });
    }
);

/**
 * just a wrapper around node http stuff, to avoid anon function in require
 */
function main() {
	
	// create one common xmlrpc client for all clients and inject into Controller
	var xmlrpc = require('xmlrpc');
	var xmlrpcClient = xmlrpc.createClient(Reyode.Config.reyadXmlRpc);
	
	var gameManager = new Reyode.GameManager(xmlrpcClient);
	
	// Port where we'll run the socket.io server
	var io = require('socket.io').listen(Reyode.Config.port);
	
	/**
	 * guarantees that every user is unique in a given node process
	 */
	var clientCounter = 0; 
	
	/**
	 *  "assoc array" of client
	 */
	Reyode.Server.ServerController.clients = {};

	var nameGenerator = new Reyode.Server.NameGenerator();
	
	io.sockets.on('connection', function (socket) {
		 //console.log(socket.handshake.headers);
		 var controller = new Reyode.Server.ServerController();
		 controller.gameManager = gameManager;
		 console.log((new Date()) + ' Connection !');
		 
		 // inject connection into server to isolate message processing there
		 controller.socket = socket;
		 
		 var user = new Reyode.User();
		 user.nickname = controller.getCookie('login');
		 if (null == user.nickname) {
			 user.nickname = nameGenerator.generate();
		 }
		 controller.user = user;
		 socket.user = user;
		 
		 // let's collect connected people just for fun
		 // we use string indexes to avoid using array, but an object instead (easier delete)
		 var index = '' + clientCounter++;
		 Reyode.Server.ServerController.clients[index] = controller;
		 console.log((new Date()) + ' index ' + index);
		 console.log((new Date()) + " new size of clients: " + Object.keys(Reyode.Server.ServerController.clients).length);
		
		 // user sent some message
		 socket.on('message', function (data) {
		     console.log((new Date()) + ' Received Message');
		     console.log(data);
		     controller.dispatch(data);
		 });
		 
		 socket.on('disconnect', function() {
				console.log('disconnect!!! ooooo');
				io.sockets.emit('user_disconnected', user);
		 });
	
	});
	/*
	io.sockets.on('disconnect', function (socket) {
		console.log('disconnect!!!');
		io.sockets.emit('user_disconnected', socket.user);
	});
	*/
}