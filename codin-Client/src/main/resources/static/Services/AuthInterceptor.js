'use strict'

codeApp.factory('AuthInterceptor', function ($rootScope, $q, $window) {
  return {
    request: function (config) {
		config.headers = config.headers || {};
		if ($window.sessionStorage.token) {
			config.headers["Content-Type"] = "application/x-www-form-urlencoded";
			config.headers.Authorization = "";
			config.headers.Authorization = 'Basic '+$window.sessionStorage.token;
		}
		return config;
    },
    response: function (response) {
		if (response.status === 401) {
      		delete $window.sessionStorage.token;
            $window.location.href = 'login';
		}
		return response || $q.when(response);
    },
    responseError: function(rejection){
        if (rejection.status == 401)
        {
      		delete $window.sessionStorage.token;
            $window.location.href = 'login';
        }
        return $q.reject(rejection);
    }
  };
});