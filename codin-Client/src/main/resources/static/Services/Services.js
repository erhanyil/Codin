'use strict';

codeApp.factory('Auth', function ($resource, $rootScope, $sessionStorage, $q, $location, $window, $http, $state, ProfilesResources, Modal, Alert) {
    var Profile = $resource('user', {}, {
        login: {
            method: "GET",
            isArray: false
        }
    });

    var auth = {};

    auth.init = function () {
        if (auth.isLoggedIn()) {
            $rootScope.user = auth.currentUser();
            $rootScope.userDetails = auth.currentUserProfile();
        } else {
            auth.login().then(function () {
                if (auth.userHasPermission(['USER'])) {
                    auth.isFirstLogin();
                    $state.go("app.profile");
                } else if (auth.userHasPermission(['ADMIN'])) {
                    $state.go("app.dashboard");
                } else {
                    $window.location.href = 'login';
                }
            }, function () {
                $window.location.href = 'login';
            });
        }
    };

    auth.login = function () {
        return $q(function (resolve, reject) {
            Profile.login({}).$promise
                .then(function (data) {
                    $sessionStorage.user = data.response.userAuthentication;
                    $sessionStorage.userProfile = data.response.userProfile;
                    $sessionStorage.isFirstLogin = data.response.userAuthentication.details.isFirstLogin;
                    $sessionStorage.isLinkedInLogin = data.response.userAuthentication.details.linkedInProfile == null ? false : true;

                    $rootScope.user = $sessionStorage.user;
                    $rootScope.userProfile = $sessionStorage.userProfile;
                    $rootScope.isFirstLogin = $sessionStorage.isFirstLogin;
                    $rootScope.isLinkedInLogin = $sessionStorage.isLinkedInLogin;

                    $sessionStorage.examsStartStatus = false;
                    $rootScope.examsStartStatus = $sessionStorage.examsStartStatus;
                    resolve();
                }, function () {
                    reject();
                });
        });
    };

    auth.logout = function () {
        delete $sessionStorage.user;
        delete $rootScope.user;
        delete $sessionStorage.userProfile;
        delete $rootScope.userProfile;
        delete $sessionStorage.isLinkedInLogin;
        delete $rootScope.isLinkedInLogin;
        delete $sessionStorage.isFirstLogin;
        delete $rootScope.isFirstLogin;
        $http({
            method: 'POST',
            url: '/logout',
            data: {}
        }).then(function successCallback(response) {
            $window.location.href = 'http://localhost:8003/auth/logout';
        }, function errorCallback(response) {
            $window.location.href = 'http://localhost:8003/auth/logout';
        });
    };

    auth.checkPermissionForView = function (view) {
        if (!view.requiresAuthentication) {
            return true;
        }
        return userHasPermissionForView(view);
    };

    var userHasPermissionForView = function (view) {
        if (!auth.isLoggedIn()) {
            return false;
        }
        if (!view.permissions || !view.permissions.length) {
            return true;
        }
        return auth.userHasPermission(view.permissions);
    };

    auth.userHasPermission = function (permissions) {
        if (!auth.isLoggedIn()) {
            return false;
        }

        var found = false;
        if (findWithAttr($sessionStorage.user.authorities, "authority", permissions) != -1) {
            found = true;
        }

        return found;
    };

    auth.currentUser = function () {
        return $sessionStorage.user;
    };

    auth.currentUserProfile = function () {
        return $sessionStorage.userProfile;
    };

    auth.isLoggedIn = function () {
        return $sessionStorage.user != null;
    };

    auth.isFirstLogin = function () {
        if ($sessionStorage.isFirstLogin) {
            Alert.info("Do not forget to enter the exams, it is waiting for you.", 'exams');
            Modal.open('isFirstLogin', 'profileController');
        }
    };

    auth.permissionFolder = function () {
        return auth.userHasPermission(['ADMIN']) ? "/Admin/" : "/";
    };

    function findWithAttr(array, attr, value) {
        for (var i = 0; i < array.length; i += 1) {
            if (array[i][attr] == value) {
                return i;
            }
        }
        return -1;
    }

    return auth;
});

codeApp.service('Modal', function ($modal, $state) {

    var currentModal = "";

    this.open = function (template, ctrl) {
        var size = 'lg';
        currentModal = $modal.open({
            backdrop: 'static',
            keyboard: false,
            animation: true,
            templateUrl: 'Views/Modals/' + template + '.modal.html',
            controller: ctrl,
            size: size
        });
        angular.element(document.querySelectorAll('.all')).addClass('setBlur');

    };

    this.open = function (template) {
        var ctrl = $state.router.globals.$current.self.controller;
        var size = 'lg';
        currentModal = $modal.open({
            backdrop: 'static',
            keyboard: false,
            animation: true,
            templateUrl: 'Views/Modals/' + template + '.modal.html',
            controller: ctrl,
            size: size
        });
        angular.element(document.querySelectorAll('.all')).addClass('setBlur');
    };

    this.close = function () {
        currentModal.close();
        angular.element(document.querySelectorAll('.all')).removeClass('setBlur');
    };
});

codeApp.service('Alert', function ($rootScope, $state, $timeout) {

    /**use 'alertAnimation' class on ng-repeat for animation**/

    $rootScope.alertAll = [];

    this.success = function (message, state) {
        this.createAlert(message, 'success', state);
    };
    this.info = function (message, state) {
        this.createAlert(message, 'info', state);
    };
    this.warning = function (message, state) {
        this.createAlert(message, 'warning', state);
    };
    this.danger = function (message, state) {
        this.createAlert(message, 'danger', state);
    };

    this.createAlert = function (message, alertType, state) {
        if ($rootScope.alertAll.length > 0) $timeout($rootScope.alertAll.splice(0, 1), 10000);
        $rootScope.alertMessage = message;
        $rootScope.alertType = alertType;
        $rootScope.alertState = state;
        $rootScope.alertAll.push({
            alertMessage: $rootScope.alertMessage,
            alertType: $rootScope.alertType,
            alertState: $rootScope.alertState
        });
    };

    $rootScope.goAlertState = function () {
        $state.go($rootScope.alertState);
    };

    $rootScope.clearAlert = function () {
        $rootScope.alertAll = [];
        $rootScope.alertMessage = null;
        $rootScope.alertType = null;
        $rootScope.alertState = null;
    };

    this.clear = function () {
        $rootScope.alertAll = [];
        $rootScope.alertMessage = null;
        $rootScope.alertType = null;
        $rootScope.alertState = null;
    };
});