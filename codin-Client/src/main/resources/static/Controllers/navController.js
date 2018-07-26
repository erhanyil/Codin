'use strict';

codeApp.controller('navController', function ($scope, $rootScope, $window, $state, Alert) {
    $rootScope.Alert = Alert;
    $rootScope.logout = function (status) {
        if (status) {
            if (confirm("Exam still continue. Are you sure to logoff ?")) {
                $rootScope.Auth.logout();
            }
        } else {
            $rootScope.Auth.logout();
        }
    };
});