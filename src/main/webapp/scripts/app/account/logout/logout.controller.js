'use strict';

angular.module('ligaApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
