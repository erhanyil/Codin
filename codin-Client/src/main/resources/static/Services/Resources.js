'use strict';

codeApp.factory('ProfilesResources', ['$resource', function ($resource) {
    return $resource('services/:dest/:id/:subDest',
        {
            dest: "profiles"
        },
        {
            post: {method: 'POST', isArray: false, cache: false},
            get: {method: "GET", isArray: false, cache: false, params: {id: "@id"}},
            get: {method: "GET", isArray: false, cache: false},
            delete: {method: "DELETE", cache: false, params: {id: "@id"}},
            delete: {method: "DELETE", cache: false},
            getCourses: {method: "GET", isArray: false, cache: false, params: {id: "@id", subDest: "courses"}},
            getProfile: {method: "GET", isArray: false, cache: false, params: {id: "@id"}},
            firstLoginProcess: {
                method: "POST", isArray: false, cache: false, params: {id: "@id", subDest: "firstLoginProcess"}
            }
        }
    );
}]);

codeApp.factory('ExamsResources', ['$resource', function ($resource) {
    return $resource('services/:dest/:id',
        {
            dest: "exams"
        },
        {
            post: {method: 'POST', isArray: false, cache: false},
            get: {method: "GET", isArray: false, cache: false, params: {id: "@id"}},
            get: {method: "GET", isArray: false, cache: false},
            delete: {method: "DELETE", cache: false, params: {id: "@id"}},
            delete: {method: "DELETE", cache: false}
        }
    );
}]);