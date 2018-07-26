'use strict';

codeApp.controller('examsController', function ($scope, $state, $rootScope, $sessionStorage) {
    $scope.examsContent = './Views/examsContent.html';

    $scope.show = function (language) {
        $scope.languageName = language;
    };

    $scope.tabs = [
        {
            active: true,
            title: '1',
            content: 'The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '2',
            content: 'The Hibernate Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '3',
            content: 'The Java Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '4',
            content: 'The C# Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '5',
            content: 'The PHP Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '6',
            content: 'The For Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '7',
            content: 'The IF ELSE Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '8',
            content: 'The SWITCH CASE Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '9',
            content: 'The WHILE Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '10',
            content: 'The DO WHILE Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '11',
            content: 'The C++ Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '12',
            content: 'The ANGULAR Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '13',
            content: 'The REACT Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '14',
            content: 'The Swift Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '15',
            content: 'The KOTLIN Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '16',
            content: 'The VUEJS Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '17',
            content: 'The GITHUB Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '18',
            content: 'The Microsoft Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '19',
            content: 'The APPLE Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },
        {
            active: false,
            title: '20',
            content: 'The MYSQL Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications'
        },

    ];

    $scope.tabHandler = function (tab) {
        angular.forEach($scope.tabs, function (item) {
            item.active = false;
        });
        tab.active = true;
    };

    $scope.examsStart = function () {
        $rootScope.examsStartStatus = $sessionStorage.examsStartStatus = true;
        $state.go("app.examsStart");
    };

    $scope.answer = function (data) {
        alert(data);
    }
});