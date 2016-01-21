'use strict';

angular.module('ligaApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


