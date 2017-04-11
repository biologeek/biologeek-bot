/**
 * Created by xcaron on 09/09/2016.
 */

(function() {
	'use strict';

	var app = angular.module('wikibot', [ 'ngMessages', 'ngRoute',
			'ngMaterial', 'ui-notification',
			'angularMoment' ]);

	app.config(function($routeProvider) {

		$routeProvider.when('/admin/:batchId', {
			templateUrl : 'admin/admin.html',
			controller : 'AdminController'
		}).otherwise({
			redirectTo : '/'
		});
	});

	app.config(function(NotificationProvider) {
		NotificationProvider.setOptions({
			delay : 5000,
			startTop : 20,
			startRight : 10,
			verticalSpacing : 20,
			horizontalSpacing : 20,
			positionX : 'right',
			positionY : 'bottom'
		});
	});

	app.config(function($httpProvider) {
		$httpProvider.defaults.useXDomain = true;
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
	});
})();