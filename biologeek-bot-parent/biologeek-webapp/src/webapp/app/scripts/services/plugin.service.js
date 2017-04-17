
(function() {
	'use strict';

	angular.module('wikibot').service('PluginService', PluginService);

	PluginService.$inject = [ '$http', 'Constants' ];

	function PluginService($http, Constants){
		
		var vm = this;

		vm.getOne = function (id, callbackOK, callbackKO){
			$http.get(Constants.API_ADDRESS+'/management/plugin/'+id).then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};

		vm.getAllNotInstalled = function (id, callbackOK, callbackKO){
			$http.get(Constants.API_ADDRESS+'/management/plugins/notinstalled').then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};

		vm.getAll = function (callbackOK, callbackKO){			
			$http.get(Constants.API_ADDRESS+'/management/list/all')
			.then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};

		vm.getAllInstalled = function (id, callbackOK, callbackKO){
			$http.get(Constants.API_ADDRESS+'/management/plugin/installed').then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};
		
		/**
		 * Install a plugin. Sends a POST request with data of type PluginBean
		 */
		vm.install = function (plugin){
			$http.post(Constants.API_ADDRESS+'/management/install', plugin).then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};
		
		/**
		 * Uninstall a plugin. Sends a POST request with data of type PluginBean
		 */
		vm.uninstall = function (plugin){
			$http.delete(Constants.API_ADDRESS+'/management/uninstall', plugin).then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};
		
		
		vm.getParametersForBatch = function(batchId, callbackOK, callbackKO){
			$http.get(Constants.API_ADDRESS+'/management/parameters/'+batchId).then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};
		
		
		vm.saveParametersForBatch = function(batchId, body, callbackOK, callbackKO){
			$http.put(Constants.API_ADDRESS+'/management/parameters/'+batchId, body).then(function(response){
				callbackOK(response.data);
			}, function(response){
				callbackKO(response.data);
			});
		};
	}
})();