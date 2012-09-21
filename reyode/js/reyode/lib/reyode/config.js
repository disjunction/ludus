var c = Reyode.Config = {};
c.port = 1337;
c.socketUrl = 'http://ludus.reyode:1337/';
c.reyadXmlRpc = { host: 'ludus.js', port: 80, path: '/reyad/xmlrpc'};

c.autoload = ['models/EventDispatcher',
              
              'models/User',
              'models/QuizSpinner',
              'models/Word',
              
              'models/GameSession',
              'models/UserSession',
              
              'reyode/SocketIoController',
              ];