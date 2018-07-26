'use strict';

codeApp.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);

codeApp.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
    $stateProvider
        .state('app', {
            views: {
                'navbar': {
                    templateUrl: 'Views/MainContents/navbar.html',
                    controller: "navController"
                },
                'alert': {
                    templateUrl: 'Views/MainContents/alert.html'
                },
                'content': {
                    templateUrl: 'Views/MainContents/content.html'
                },
                'footer': {
                    templateUrl: 'Views/MainContents/footer.html'
                }
            }
        })
        .state('app.dashboard', {
            url: "/dashboard",
            cache: false,
            templateUrl: "Views/dashboard.html",
            controller: "dashboardController",
            requiresAuthentication: true,
            permissions: ["ADMIN"],
            title: 'Dashboard'
        })
        .state('app.home', {
            url: "/home",
            cache: false,
            templateUrl: "Views/home.html",
            controller: "homeController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Home'
        })
        .state('app.profile', {
            url: "/profile",
            cache: false,
            templateUrl: "Views/profile.html",
            controller: "profileController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Profile'
        })
        .state('app.discover', {
            url: '/discover',
            templateUrl: "Views/discover.html",
            controller: "discoverController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Discover'
        })
        .state('app.messaging', {
            url: '/messaging',
            templateUrl: "Views/messaging.html",
            controller: "messagingController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Messaging'
        })
        .state('app.notifications', {
            url: '/notifications',
            templateUrl: "Views/notifications.html",
            controller: "notificationsController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Notifications'
        })
        .state('app.exams', {
            url: '/exams',
            templateUrl: "Views/exams.html",
            controller: "examsController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Exams'
        })
        .state('app.examsDetails', {
            url: '/exams/details',
            templateUrl: "Views/examsDetails.html",
            controller: "examsController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Exams Details'
        })
        .state('app.examsStart', {
            url: '/exams/start',
            templateUrl: "Views/examsStart.html",
            controller: "examsController",
            requiresAuthentication: true,
            permissions: ["USER"],
            title: 'Exams Start'
        })
        .state('app.settings', {
            url: '/settings',
            templateUrl: "Views/settings.html",
            controller: "settingsController",
            requiresAuthentication: true,
            permissions: ["USER", "ADMIN"],
            title: 'Settings'
        });
});

codeApp.config(function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
});