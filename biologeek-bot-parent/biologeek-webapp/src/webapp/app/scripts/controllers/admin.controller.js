(function() {
	'use strict';

	angular.module('wikibot').controller('AdminController', AdminController);

	AdminController.$inject = [ '$scope', '$routeParams', '$location',
			'PluginService' ];

	function AdminController($scope, $routeParams, $location, PluginService) {

		var vm = this;

		var init = function() {
			console.log('coucou');
			initParameters();
		};

		function initParameters() {

			if (typeof ($routeParams.batchId) !== "undefined"
					&& !isNaN($routeParams.batchId)) {
				var batchID = $routeParams.batchID;
				PluginService.getParametersForBatch(batchID,
						function(response) {
							vm.parameters = response;
						}, function(response) {
							Notification.error(response.message);
						});
			} else {
				$location.path("/");
			}
		}

		init();
	}

})();