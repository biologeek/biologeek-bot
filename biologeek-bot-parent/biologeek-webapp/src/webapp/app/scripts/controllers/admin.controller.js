
(function() {
	'use strict';

	angular.module('wikibot').controller('AdminController', AdminController);

	AdminController.$inject = [ '$scope', 'PluginService' ];

	function AdminController($scope, PluginService) {

		var vm = this;

	
		var init = function(){
			initParameters();
		};
			
		function initParameters(){
		
			PluginService.getParametersForBatch(batchID, function(response){
				vm.parameters = response;
			}, function(response){
				Notification.error(response.message);
			});
			
		}
		
		init();
	}
	
})();