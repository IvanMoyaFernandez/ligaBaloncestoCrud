'use strict';

angular.module('ligaApp')
    .factory('Jugador', function ($resource, DateUtils) {
        return $resource('api/jugadors/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.nacimiento = DateUtils.convertDateTimeFromServer(data.nacimiento);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
