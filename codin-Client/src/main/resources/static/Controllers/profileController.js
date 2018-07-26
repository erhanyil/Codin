'use strict';

codeApp.controller('profileController', function ($scope, $http, ProfilesResources, $sessionStorage, $rootScope) {

    $scope.firstLoginProcess = function () {
        ProfilesResources.firstLoginProcess({id: $rootScope.userDetails.id}, $rootScope.isLinkedInLogin, function (response) {
            $sessionStorage.isFirstLogin = false;
            $rootScope.isFirstLogin = false;
        }, function (error) {
            alert(error);
        });
    };

    $scope.initProfileEditModal = function () {
        console.log("this function will work before the modal");
    }

    /*document.getElementById('education').scrollIntoView();
     document.getElementById('education').style.border = 'solid 10px red;'*/

});