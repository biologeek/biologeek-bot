(function() {
	'use strict';

	angular.module('wikibot')
			.controller('WelcomeController', WelcomeController);

	WelcomeController.$inject = [ '$scope', '$location', 'PluginService', 'Notification' ];

	function WelcomeController($scope, $location, PluginService, Notification) {
		var vm = this;

		vm.plugins = [];
		vm.pluginsToDisplay = [];
		vm.checked = {
			"installed" : true,
			"notinstalled" : true
		};

		var getAllPluginsAndFilter = function(){
			PluginService.getAll(function(response){
				vm.plugins = response;
				vm.filterPlugins();
				vm.enrichPlugins();
			}, function(response) {
				Notification.error("Could not retrieve plugins");
			});
		}
		
		vm.enrichPlugins = function(){
			_.forEach(vm.pluginsToDisplay, function(o){
				switch(o.batch.status){
				case "RUNNING":
				case "RESUMED":
					o.bootstrapLabelClassStatus = "label-success";
				break;
				case "STOPPED":
					o.bootstrapLabelClassStatus = "label-important";
				break;
				case "PAUSED":
					o.bootstrapLabelClassStatus = "label-warning";
				break;
				}
			});
		}
		
		vm.filterPlugins = function() {
			if (vm.checked.installed && vm.checked.notinstalled) {
				vm.pluginsToDisplay = vm.plugins;
			}
			if (!vm.checked.installed) {
				vm.pluginsToDisplay.append(_.filter(vm.plugins, function(o) {
					return o.id != null && o.installed;
				}));
			}

			if (!vm.checked.notinstalled) {
				vm.pluginsToDisplay.append(_.filter(vm.plugins, function(o) {
					return o.id != null;
				}));
			}
		}
		
		vm.getColorForStatus = function(status){
			switch (status){
			case 'STOPPED' :
				return '#d4d4d4';
			case 'RUNNING' :
				return '#80ff82';
			case 'PAUSED' :
				return '#ffe080';
			case 'RESUMED' :
				return '#deff80';
			}
		}
		var updateBatchStatus = function(){
			var batches = [];
			var installedBatches = _.filter(vm.pluginsToDisplay, function(o){
				return o.installed && o.id != null; 
			})
			
			var arrayOfIds = _.map(installedBatches, 'batch.id');
			console.log(arrayOfIds);
		}
		setInterval(updateBatchStatus(), 3000);
		
		getAllPluginsAndFilter();
	}
})();