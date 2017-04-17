(function() {
	'use strict';

	angular.module('wikibot').controller('AdminController', AdminController);

	AdminController.$inject = [ '$scope', '$routeParams', '$location',
			'$filter', 'PluginService', 'Notification' ];

	function AdminController($scope, $routeParams, $location, $filter,
			PluginService, Notification) {

		var vm = this;

		function initParameters() {

			if (typeof ($routeParams.batchId) !== "undefined"
					&& !isNaN($routeParams.batchId)) {
				var batchID = $routeParams.batchId;
				PluginService.getParametersForBatch(batchID,
						function(response) {
							vm.parameters = convertRequestOrResponse(response, false);
						}, function(response) {
							Notification.error(response.message);
						});
			} else {
				$location.path("/");
			}
		}

		vm.installPlugin = function(toInstallPlugin) {
			if (typeof (toInstallPlugin.id) != "undefined") {
				Notification.error("Plugin already installed !");
			}
		};

		vm.saveParams = function() {
			PluginService.saveParametersForBatch($routeParams.batchId,
					vm.parameters, function(response) {
						Notification.success("batch.saved.ok");
					}, function(response) {
						Notification.error("batch.saved.ko");
					});
		}

		initParameters();

		var convertRequestOrResponse = function(requestOrResponse, isRequest) {
			for ( var grp in requestOrResponse.groups) {
				var group = requestOrResponse.groups[grp];
				for ( var param in group.parameters) {
					var prm = group.parameters[param];
					switch (prm.type) {
					case 'DATE':
						prm.value = convertDate(prm.value, isRequest);
						break;
					}

				}
			}
			return requestOrResponse;
		}

		var convertDate = function(dateToConvert, toTimestamp) {
			if (toTimestamp) {
				return new Date(dateToConvert).getTime();
			} else {
				var filter = $filter('date');
				return filter(dateToConvert, 'dd/MM/yyyy HH:mm:ss')
			}
		}
	}

})();