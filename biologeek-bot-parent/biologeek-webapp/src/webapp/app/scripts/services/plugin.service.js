
(function() {
	'use strict';

	angular.module('wikibot').service('PluginService', PluginService);

	PluginService.$inject = [ '$http' ];

	function PluginService($http){
		
		var vm = this;

		vm.getOne = function (id){
			
			$http.get('/plugin/'+id, function(response){
				return response.data
			}, function(response){
				return response.data
			});
		}

		vm.getAllNotInstalled = function (id){
			
			$http.get('/plugin/notinstalled').then(function(response){
				return response.data
			}, function(response){
				return response.data
			});
		}

		vm.getAll = function (id){
			
			$http.get('/plugin/all').then(function(response){
				return response.data
			}, function(response){
				return response.data
			});
		}

		vm.getAllInstalled = function (id){
			
			$http.get('/plugin/installed').then(function(response){
				return response.data
			}, function(response){
				return response.data				
			});
		}
		
		/**
		 * Install a plugin. Sends a POST request with data of type PluginBean
		 */
		vm.install = function (plugin){
			
			$http.post('/management/install', plugin).then(function(response){
				return response.data;
			}, function(response){
				return response.data
			});
		}
	}
})();