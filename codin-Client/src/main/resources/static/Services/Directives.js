'use strict';

codeApp.directive('loading', ['$http', '$timeout', function ($http, $timeout) {
    return {
        restrict: 'A',
        link: function (rootScope, element, attr, ctrl) {
            rootScope.$on('$viewContentLoaded', function (event) {

                rootScope.isLoading = function () {
                    return $http.pendingRequests.length > 0;
                };

                rootScope.$watch(rootScope.isLoading, function (val) {
                    if (val) {
                        //$(element).show();
                        angular.element(document.querySelectorAll('.panel-body')).addClass('animated-background');
                        $('.panel-body > div').css("visibility", "hidden");
                    } else {
                        //$(element).hide();
                        angular.element(document.querySelectorAll('.panel-body')).removeClass('animated-background');
                        $('.panel-body > div').css("visibility", "visible");
                    }
                });
                /*rootScope.$watch('Auth.isLoggedIn()', function (val) {
                 if (val) {
                 //$(element).show();
                 angular.element(document.querySelectorAll('.panel-body')).addClass('animated-background');
                 $('.panel-body > div').css("visibility", "hidden");
                 } else {
                 //$(element).hide();
                 angular.element(document.querySelectorAll('.panel-body')).removeClass('animated-background');
                 $('.panel-body > div').css("visibility", "visible");
                 }
                 });*/
            });
        }
    }
}]);

codeApp.directive('hoverClass', function () {
    return {
        restrict: 'A',
        scope: {
            hoverClass: '@'
        },
        link: function (scope, element) {
            element.on('mouseenter', function () {
                element.addClass(scope.hoverClass);
            });
            element.on('mouseleave', function () {
                element.removeClass(scope.hoverClass);
            });
        }
    };
});