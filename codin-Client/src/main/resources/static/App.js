'use strict';

var codeApp = angular.module('codeApp', ['ui.router', 'ngResource', 'ngStorage', 'ui.bootstrap', 'ngAnimate']);

codeApp.run(['$rootScope', '$location', 'Auth', '$window', 'Modal', function ($rootScope, $location, Auth, $window, Modal,Alert) {
    Auth.init();

    /**First definitions**/
    $rootScope.Modal = Modal;
    $rootScope.Alert = Alert;
    $rootScope.Auth = Auth;
    /**end**/

    $rootScope.$on('$stateChangeStart', function (event, next) {
        if (!Auth.checkPermissionForView(next)){
            event.preventDefault();
            $window.location.href = 'login';
        }
        if (next.name === 'app.examsStart' || next.name === 'examsStart') {
            next.templateUrl = 'views/' + toParams.somevalue + '/page.html';
        }
    });
}]);