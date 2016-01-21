'use strict';

angular.module('ligaApp')
    .factory('Equipo', function ($resource, DateUtils) {
        return $resource('api/equipos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.fechaCreacion = DateUtils.convertDateTimeFromServer(data.fechaCreacion);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
